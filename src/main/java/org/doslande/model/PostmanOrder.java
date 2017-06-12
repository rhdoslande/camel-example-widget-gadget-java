package org.doslande.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "porder")
public class PostmanOrder implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7737175671437425463L;

	@XmlElement(required=true)
	private String customerId;
	
	@XmlElement(required=true)
	private String product;
	
	@XmlElement(required=true)
	private String amount;
	
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}


}
