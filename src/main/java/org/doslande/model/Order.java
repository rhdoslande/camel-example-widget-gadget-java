package org.doslande.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

// TODO toggle value below to order 
@XmlRootElement(name = "order")
@XmlAccessorType(XmlAccessType.FIELD)
@CsvRecord( separator = ",", skipFirstLine = true )
//It represents a record = a line of a CSV file
public class Order implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8121852098472447597L;

	@XmlElement
	@DataField(pos = 1)
	private String customerId;
	
	@XmlElement
	@DataField(pos = 2)
	private String product;
	
	@XmlElement
	@DataField(pos = 3)
	private String amount;

}
