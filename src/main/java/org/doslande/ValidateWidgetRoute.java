package org.doslande;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.xml.XPathBuilder;
import org.doslande.model.MyValidationBean;

public class ValidateWidgetRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		// start from "widget" queue
		// get the customer, if not known in the system, send to accounting queue
		// get order "amount" from xml input
		// compare versus "inventory amount"
		// if less than 50K, send to fulfillment db
		// otherwise send to accounting queue
		
		MyValidationBean myBean = new MyValidationBean();
		
		XPathBuilder xPathBuilder = new XPathBuilder("//orders/order");
		
		
		
//		from("mock:widget-queue")
		// when set of known customers contains the customerId
		// .method(MyBean.class, "isGoldCustomer")
		// .method("myBean", "isGoldCustomer")
		// .method(my, "isGoldCustomer")
		
		from("file:src/test/resources/from-rest?noop=true&fileName=rest_orders.xml")
		.split(xPathBuilder)
			.choice()
				.when(method(MyValidationBean.class, "isGoldCustomer"))
					.to("mock:fulfillment")
				.otherwise()
		            .to("mock:accouting");

	}

}
