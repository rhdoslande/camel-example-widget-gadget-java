package org.doslande;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.builder.NoErrorHandlerBuilder;
import org.apache.camel.builder.RouteBuilder;
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
		
		String CXF_RS_ENDPOINT_URI = "cxfrs://http://localhost:9090/gadgetdivision?resourceClasses=org.doslande.OrderServiceResource";
		
		Endpoint fulfillmentQueue = endpoint("activemq:queue:fulfillment");
		Endpoint accountingQueue = endpoint("activemq:queue:accounting");
		
		DataFormat jaxbformat = new JaxbDataFormat("org.doslande.model");
		
		JaxbDataFormat jaxbformat2 = new JaxbDataFormat("org.doslande.model");
//		JAXBContext jc = jaxbformat2.getContext();
		JAXBContext jc2 = JAXBContext.newInstance("org.doslande.model");
		Marshaller marshaller = jc2.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
		
		from(CXF_RS_ENDPOINT_URI)
		// maybe do xslt to remove the xml prolog
		.process(new OrderProcessor())
			.choice()
				.when(method(MyValidationBean.class, "isKnownCustomer"))
					.choice()
						.when(method(MyValidationBean.class, "isOrderUnderLimit"))
							// known customer and order is under the limit, send to fulfillment
	//						.to("xslt:orders.xsl")
							.to("log:fulfillment1")
							.marshal(jaxbformat2)
							.to("log:fulfillment2")
							.to(fulfillmentQueue)						
	//						.to("log:fulfillment2")
						.otherwise()
							// order is over the limit, send to accounting
	//						.to("xslt:orders.xsl")
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
