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
	public boolean isKnownCustomer(
			String body, 
			Exchange exchange, 
			@XPath("/order/customerId") String customerId) {
		System.out.println("customerId is:" + customerId + ", known=" + knownCustomers.contains(customerId));
		
		return MyValidationBean.knownCustomers.contains(customerId);
	  }
	
	public boolean isOrderUnderLimit(
			String body, 
			Exchange exchange, 
			@XPath("/order/amount") String amount) {
		System.out.print("amount is:" + amount);
		
		int myAmount = 50001;
		try {
			myAmount = Integer.parseInt(amount);	
		} catch (NumberFormatException nfe) {
			System.out.println("amount is not an int:" + amount);
		}		
		
		boolean result = (myAmount < 50000);
		System.out.println(" , result is:" + result);
		return result;
		
	}
	

}
