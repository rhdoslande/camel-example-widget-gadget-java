package org.doslande.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@XmlRootElement(name = "orders")
@XmlAccessorType(XmlAccessType.FIELD)
@CsvRecord( separator = ",", skipFirstLine = true )
public class Orders {
	
    private List<Order> orders;

	@XmlElement(name="order")
	@DataField(pos = 1)
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

}
