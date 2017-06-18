package org.doslande;

import org.apache.camel.Endpoint;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.spi.DataFormat;
import org.doslande.model.Order;

public class FtpToDivisionRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		JaxbDataFormat jaxbDataFormat = new JaxbDataFormat();
		jaxbDataFormat.setContextPath(Order.class.getPackage().getName());
		
		// adds namespace to the ouputted xml
//		jaxbDataFormat.setSchemaLocation("schema/order.xsd");
		
		DataFormat bindy = new BindyCsvDataFormat(org.doslande.model.Order.class);
		
		Predicate isWidget = xpath("/order/product = 'widget'");
		Predicate isGadget = xpath("/order/product = 'gadget'");
        
		Endpoint fileStartPoint = endpoint("file:src/test/resources/from-ftp?noop=true&fileName=jaxb_orders.csv");
//		Endpoint widgetFileEndpoint = endpoint("file:src/test/resources/divisions?fileName=ftp_widget_out.xml&fileExist=Append");
//		Endpoint gadgetFileEndpoint = endpoint("file:src/test/resources/divisions?fileName=ftp_gadget_out.xml&fileExist=Append");
        Endpoint other = endpoint("file:src/test/resources/divisions?fileName=ftp_other_out.xml&fileExist=Append");
        
        Endpoint widgetDivisionQueue = endpoint("activemq:queue:widgetDivision");
        Endpoint gadgetDivisionHttp = endpoint("http://localhost:9090/gadgetdivision/orderservice/order/");
        
        // gadget receives on http, not a queue
//        Endpoint gadgetDivisionQueue = endpoint("activemq:queue:gadgetDivision");
		
//		from("file:src/test/resources/from-ftp?noop=true&fileName=jaxb_orders.csv")
        
		from(fileStartPoint)		
		  .unmarshal(bindy)
		  .split(body())
		  .marshal(jaxbDataFormat)		  
          .choice()
	          .when(isWidget)
	              .to("xslt:orders.xsl")
//	              .to(widgetFileEndpoint)
	              .to(widgetDivisionQueue)
		      .when(isGadget)
//	              .to("xslt:orders.xsl")
//	              .to(gadgetFileEndpoint)
	              // must send to a rest endpoint!
//	              .to("http://localhost:9090/gadgetdivision/orderservice/order/")
	              .to(gadgetDivisionHttp)
//	              .to(gadgetDivisionQueue)
	              .to("log:to-gadgetdivision-http")
	          .otherwise()
	              .to("xslt:orders.xsl")
	              .to("log:other") // add a log so we can see this happening in the console
	              .to(other);
		
	}
}
