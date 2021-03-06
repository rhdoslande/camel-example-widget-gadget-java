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
        Order order1 = inMessage.getBody(Order.class);            
        exchange.getOut().setBody(order1);
        exchange.getOut().setHeader("customerId", order1.getCustomerId());
        exchange.getOut().setHeader("product", order1.getProduct());
        exchange.getOut().setHeader("amount", order1.getAmount());
        return;
	}

}
