package org.doslande;

import org.apache.camel.Endpoint;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.xml.XPathBuilder;

public class RestToDivisionRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		Predicate isWidget = xpath("/order/product = 'widget'");
		Predicate isGadget = xpath("/order/product = 'gadget'");
        Endpoint widget = endpoint("file:src/data/divisions?fileName=rest_widget_out.xml&fileExist=Append");
        Endpoint gadget = endpoint("file:src/data/divisions?fileName=rest_gadget_out.xml&fileExist=Append");
        Endpoint other = endpoint("file:src/data/divisions?fileName=rest_other_out.xml&fileExist=Append");
        XPathBuilder xPathBuilder = new XPathBuilder("//orders/order"); 
        
		from("file:src/data/from-rest?noop=true&fileName=rest_orders.xml")
		.split(xPathBuilder)
          .choice()
	          .when(isWidget)
	              .to("mock:widget")
	              .to(widget)
		      .when(isGadget)
	              .to("mock:gadget")
	              .to(gadget)
	          .otherwise()
	              .to("log:other")
	              .to(other);
	}
}