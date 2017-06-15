package org.doslande;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.doslande.model.Order;

public class OrderProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
        Message inMessage = exchange.getIn();                        

        System.out.println("Order block!");
        Order order1 = inMessage.getBody(Order.class);            
        System.out.println("Order block! toString is: " + order1);
        exchange.getOut().setBody(order1);
	}

}
