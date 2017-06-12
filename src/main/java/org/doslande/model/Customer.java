package org.doslande.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "customer")
@XmlType(namespace="http://www.example.org/type")
public class Customer {
	

	@XmlElement
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@XmlElement
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	private long id;
	
	private String name;

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[Customer: id=" + this.id + ", name=" + this.name + "]";
	}
	
	

}
