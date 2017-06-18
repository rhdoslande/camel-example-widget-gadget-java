package org.doslande;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Message;
import org.apache.camel.Predicate;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.xml.XPathBuilder;

public class RestToDivisionRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		Predicate isWidget = xpath("/order/product = 'widget'");
		Predicate isGadget = xpath("/order/product = 'gadget'");
		
//		String incomingOrdersUri = "cxfrs://http://localhost:9090/incomingOrders?resourceClasses=org.doslande.IncomingOrdersServiceResource";
		String incomingCxfrs = "cxfrs://http://localhost:9090/incoming?resourceClasses=org.doslande.IncomingOrdersServiceResource";
		Endpoint widgetDivisionQueue = endpoint("activemq:queue:widgetDivision");
		Endpoint gadgetDivisionHttp = endpoint("http://localhost:9090/gadgetdivision/orderservice/order/");
		
		Endpoint fileStartPoint = endpoint("file:src/test/resources/from-rest?noop=true&fileName=rest_orders.xml");
//        Endpoint widgetFileEndpoint = endpoint("file:src/test/resources/divisions?fileName=rest_widget_out.xml&fileExist=Append");
//        Endpoint gadgetFileEndpoint = endpoint("file:src/test/resources/divisions?fileName=rest_gadget_out.xml&fileExist=Append");
        Endpoint otherFileEndpoint = endpoint("file:src/test/resources/divisions?fileName=rest_other_out.xml&fileExist=Append");
        
        XPathBuilder xPathBuilder = new XPathBuilder("//orders/order"); 
        
		from(incomingCxfrs)
		.setExchangePattern(ExchangePattern.InOnly)
//		.to("log:incomingCxfrs")
		
        .process(new Processor() {
            public void process(Exchange exchange) throws Exception {
                //custom processing here
            	Message inMessage = exchange.getIn();
            	String body = inMessage.getBody(String.class);
            	System.out.println("body is:" + body);            	
            	exchange.getIn().setBody(body);
//            	exchange.getOut().setBody(body);
            }
        })
        
        .split(xPathBuilder)
	        .choice()
		        .when(isWidget)
		            .to("xslt:orders.xsl")
		            .to(widgetDivisionQueue)  // sends the "order" node, not a full xml document
		            .to("log:to-widget-queue")
			      .when(isGadget)
		            .setHeader(Exchange.HTTP_METHOD, constant("POST"))
		            .setHeader(Exchange.CONTENT_TYPE, constant("text/xml"))
		            // do not wrap in the orders element
		//            .to("xslt:orders.xsl")
		            .to("log:to-gadget-http")
		            .to(gadgetDivisionHttp)	              
		        .otherwise()
		            .to("xslt:orders.xsl")
		            .to("log:other")
		            .to(otherFileEndpoint);
		
		
/*		.split(xPathBuilder)
          .choice()
	          .when(isWidget)
	              .to("mock:widget")
	              .to("xslt:orders.xsl")
	              .to(widgetDivisionQueue)  // sends the "order" node, not a full xml document
	              .to("log:to-widget-queue")
		      .when(isGadget)
//	              .to("mock:gadget")
	              .setHeader(Exchange.HTTP_METHOD, constant("POST"))
	              .setHeader(Exchange.CONTENT_TYPE, constant("text/xml"))
//	              .to("xslt:orders.xsl")
	              .to("log:to-gadget-http")
	              .to(gadgetDivisionHttp)	              
	          .otherwise()
	              .to("xslt:orders.xsl")
	              .to("log:other")
	              .to(otherFileEndpoint);*/
	}
}