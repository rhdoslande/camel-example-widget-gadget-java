package org.doslande;

import org.apache.camel.builder.NoErrorHandlerBuilder;
import org.apache.camel.builder.RouteBuilder;

public class CustomerRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		errorHandler(new NoErrorHandlerBuilder());
		
		String CXF_RS_ENDPOINT_URI = "cxfrs://http://localhost:9090/rest?resourceClasses=org.doslande.CustomerServiceResource";
		
		from(CXF_RS_ENDPOINT_URI).process(new ProcessorDavid1())
//		.to("log:cxfrs-log")
		;

	}

}
