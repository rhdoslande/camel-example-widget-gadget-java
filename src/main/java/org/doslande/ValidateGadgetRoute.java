package org.doslande;

import org.apache.camel.builder.NoErrorHandlerBuilder;
import org.apache.camel.builder.RouteBuilder;

public class ValidateGadgetRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		// start from "gadgetDivision" web service
		// get the customer, if not known in the system, send to accounting queue
		// get order "amount" from xml input, if less than 50K, send to fulfillment db
		// otherwise send to accounting queue
		
		errorHandler(new NoErrorHandlerBuilder());
		
		String CXF_RS_ENDPOINT_URI = "cxfrs://http://localhost:9090/gadgetdivision?resourceClasses=org.doslande.OrderServiceResource";
		
		from(CXF_RS_ENDPOINT_URI).process(new OrderProcessor())
		;
		
		
		
		
		
		
		
		
		
		
		
		
		
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
