package org.doslande;

import org.apache.camel.Endpoint;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.spi.DataFormat;
import org.doslande.model.Order;

public class FtpToDivisionRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		JaxbDataFormat jaxbDataFormat = new JaxbDataFormat();
		jaxbDataFormat.setContextPath(Order.class.getPackage().getName());
		
		// adds namespace to the ouputted xml
//		jaxbDataFormat.setSchemaLocation("schema/order.xsd");
		
		DataFormat bindy = new BindyCsvDataFormat(org.doslande.model.Order.class);
		
		Predicate isWidget = xpath("/order/product = 'widget'");
		Predicate isGadget = xpath("/order/product = 'gadget'");
        Endpoint widget = endpoint("file:src/test/resources/divisions?fileName=ftp_widget_out.xml&fileExist=Append");
        Endpoint gadget = endpoint("file:src/test/resources/divisions?fileName=ftp_gadget_out.xml&fileExist=Append");
        Endpoint other = endpoint("file:src/test/resources/divisions?fileName=ftp_other_out.xml&fileExist=Append");
		
		from("file:src/test/resources/from-ftp?noop=true&fileName=jaxb_orders.csv")
		
		  .unmarshal(bindy)
		  .split(body())
		  .marshal(jaxbDataFormat)		  
          .choice()
	          .when(isWidget)
	              .to("mock:widget") // add a log so we can see this happening in the shell
//	              .to("log:before_xslt")
	              .to("xslt:orders.xsl")
	              .to(widget)
//	              .to("log:xsl_temp")
		      .when(isGadget)
	              .to("mock:gadget") // add a log so we can see this happening in the shell
	              .to("xslt:orders.xsl")
	              .to(gadget)
	          .otherwise()
	              .to("log:other") // add a log so we can see this happening in the shell
	              .to(other);
	              
		  // To Widget Queue OR Gadget REST endpoint
//		  .to("file:src/data/TESTOUT?fileName=orders_jaxb.xml&fileExist=Append"); 
		

	}

}
