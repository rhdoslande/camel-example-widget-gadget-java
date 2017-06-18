package org.doslande;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.NoErrorHandlerBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.xml.XPathBuilder;
import org.doslande.model.MyValidationBean;
import org.doslande.model.Order;
import org.apache.camel.spi.DataFormat;
import org.apache.camel.converter.jaxb.JaxbDataFormat;

public class ValidateGadgetRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		// start from "gadgetDivision" web service
		// get the customer, if not known in the system, send to accounting queue
		// get order "amount" from xml input, if less than 50K, send to fulfillment db
		// otherwise send to accounting queue
		
		errorHandler(new NoErrorHandlerBuilder());
		
		// TODO complaint: when defining cxfrs, I can't get it to work with Order jaxb elements nested in Orders,
		// therefore I need to workaround that by passing in order elements (not orders element) to the service.
		String CXF_RS_ENDPOINT_URI = "cxfrs://http://localhost:9090/gadgetdivision?resourceClasses=org.doslande.OrderServiceResource";
		
//		XPathBuilder xPathBuilder = new XPathBuilder("//orders/order");
		
		Endpoint fulfillmentQueue = endpoint("activemq:queue:fulfillment");
		Endpoint accountingQueue = endpoint("activemq:queue:accounting");
		
		DataFormat jaxbformat = new JaxbDataFormat("org.doslande.model");
		
		// now expects one "order" element in the body. not "orders". 
		from(CXF_RS_ENDPOINT_URI)
//		.split(xPathBuilder) // not needed because "order is passed in to the service
		.setExchangePattern(ExchangePattern.InOnly)
		.process(new OrderProcessor())
			.choice()
				.when(method(MyValidationBean.class, "isKnownCustomer"))
					.choice()
						.when(method(MyValidationBean.class, "isOrderUnderLimit"))
							// known customer and order is under the limit, send to fulfillment
	//						.to("xslt:order.xsl")
//							.to("log:fulfillment1")
							.marshal(jaxbformat)
							.to("log:fulfillment2")
							.to(fulfillmentQueue)						
	//						.to("log:fulfillment2")
						.otherwise()
							// order is over the limit, send to accounting
	//						.to("xslt:order.xsl")
							.marshal(jaxbformat)
							.to(accountingQueue)
							.to("log:accounting-overlimit")
					.endChoice()
				.otherwise()
					// not a known customer, send to accounting
	//				.to("xslt:orders.xsl")
					.marshal(jaxbformat)
		            .to(accountingQueue)
					.to("log:accounting-newcustomer")
			.endChoice();
		
		
		
		
		
		
		
		
		
		
		
		
		
/*		getContext().setTracing(true);
		
		from("cxfrs://http://localhost:9090/route?resourceClasses=org.doslande.NewOrdersResource")
		.to("log:rest-input-log")
		.log("Here is the message that was enriched: ${body}");*/
		
		
//		from("rest:post:orders")
//			.to("log:rest-xml-log");
		
		// REST DSL
//		rest("/myRestApp")        
//        	.post("/neworders").consumes("application/xml")
//        	.to("log:rest-xml-log");
		
//		from(gadgetDivisionQueue)
//		.split(xPathBuilder)
//			.choice()
//				.when(method(MyValidationBean.class, "isKnownCustomer"))
//					.choice()
//						.when(method(MyValidationBean.class, "isOrderUnderLimit"))
//							// known customer and order is under the limit, send to fulfillment
//							.to("xslt:orders.xsl")
//							.to(fulfillmentQueue)
//							.to("log:fulfillment")
//						.otherwise()
//							// order is over the limit, send to accounting
//							.to("xslt:orders.xsl")
//							.to(accountingQueue)
//							.to("log:accounting-overlimit")
//					.endChoice()
//				.otherwise()
//					// not a known customer, send to accounting
//					.to("xslt:orders.xsl")
//		            .to(accountingQueue)
//					.to("log:accounting-newcustomer")
//			.endChoice();
	}

}
