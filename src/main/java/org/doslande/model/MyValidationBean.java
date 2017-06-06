package org.doslande.model;

import java.util.HashSet;
import java.util.Set;

import org.apache.camel.Exchange;
import org.apache.camel.language.XPath;

public class MyValidationBean {
	
	public static Set<String> knownCustomers = new HashSet<String>();
	
	static {
		knownCustomers.add("123");
		knownCustomers.add("234");
		knownCustomers.add("678");
		knownCustomers.add("789");
//		knownCustomers.add("999");
//		knownCustomers.add("111");
	}
	
	// use xpath to bind customer ID to a method parameter
	public boolean isGoldCustomer(
			String body, 
			Exchange exchange, 
			@XPath("/order/customerId") String customerId) {
		System.out.println("customerId is:" + customerId + ", known=" + knownCustomers.contains(customerId));
		
		return MyValidationBean.knownCustomers.contains(customerId);
	  }

}
