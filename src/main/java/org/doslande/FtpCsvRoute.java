package org.doslande;

import java.util.List;

import javax.xml.bind.JAXBContext;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.model.dataformat.CsvDataFormat;
import org.apache.camel.spi.DataFormat;
import org.doslande.model.Order;

import com.thoughtworks.xstream.XStream;

public class FtpCsvRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		DataFormat bindy = new BindyCsvDataFormat(org.doslande.model.Order.class);
		
		CsvDataFormat csv = new CsvDataFormat();
		csv.setDelimiter("|");
		
		DataFormat jaxb = new JaxbDataFormat("org.doslande.bindy");		
		
		JaxbDataFormat jaxb2 = new JaxbDataFormat();
		JAXBContext con = JAXBContext.newInstance(Order.class);
		jaxb2.setContext(con);
		
//		XStream xStream = new XStream();
//		xStream.aliasField("money", Order.class, "org.doslande.bindy.Order");
		
		from("file:src/test/resources/from-ftp?noop=true&fileName=orders.csv")
		
/*		  .unmarshal().csv()
		  .convertBodyTo(String.class) // see [1]
		  .marshal(jaxb2) */
		
		
		.unmarshal(bindy)  // unmarshals csv records into an arraylist
		.marshal()
        .xstream()
        .to("file:src/test/resources/generated?fileName=orders_generated.xml"); 
//		.to("file:src/data/widget-outbox");
		
	}

}
