package org.doslande.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


//@XmlRootElement(name = "orders")
@XmlAccessorType(XmlAccessType.FIELD)
public class Orders {
	
    private List<Order> orderItems;

    @XmlElementWrapper(name="orders")
	@XmlElement(name="order")
    public List<Order> getItems() {
        return orderItems;
    }

    public void setItems(List<Order> orders) {
		this.orderItems = orders;
	}

}
