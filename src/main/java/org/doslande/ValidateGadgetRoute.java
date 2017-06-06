package org.doslande;

import org.apache.camel.builder.RouteBuilder;

public class ValidateGadgetRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		// get the customer, if not known in the system, send to accounting queue
		// get order "amount" from xml input
		// compare versus "inventory amount"
		// if less than 50K, send to fulfillment db
		// otherwise send to accounting queue

	}

}
