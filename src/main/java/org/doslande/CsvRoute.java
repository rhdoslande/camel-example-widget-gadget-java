package org.doslande;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.CsvDataFormat;

public class CsvRoute extends RouteBuilder {
	
	public void configure() throws Exception {
		
		CsvDataFormat csv = new CsvDataFormat();
		csv.setDelimiter("|");
		
		// Unmarshalling a CSV message into a Java List
		
		from("file:src/test/resources/from-ftp?noop=true&fileName=csv_orders.csv")
		
		// CSV to ArrayList
		.unmarshal().csv()
		
//		to InputStream
		.to("mock:result");
//        .to("file:src/data/TESTOUT?fileName=csv_generated.xml"); 
		
	}

}
