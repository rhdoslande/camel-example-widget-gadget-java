package org.doslande;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.doslande.model.Order;

public class OrderProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
        Message inMessage = exchange.getIn();
        exchange.setPattern(ExchangePattern.InOnly);

        System.out.println("Order block!");
        
        // alternative: get the Body as a string, 
        // remove xml prolog from Body, then get Order
        String tempBody = inMessage.getBody(String.class);
        System.out.println("Order block-1! tempBody is: " + tempBody);
        // now remove the xml prolog first line
        
        Order order1 = inMessage.getBody(Order.class);            
        System.out.println("Order block! toString is: " + order1);
        exchange.getOut().setBody(order1);
        return;
	}

}
