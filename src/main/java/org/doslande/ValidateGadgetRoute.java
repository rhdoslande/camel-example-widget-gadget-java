package org.doslande;

import org.apache.camel.Endpoint;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.NoErrorHandlerBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.spi.DataFormat;
import org.doslande.model.MyValidationBean;

public class ValidateGadgetRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		errorHandler(new NoErrorHandlerBuilder());
		
		// TODO complaint: when defining cxfrs, I can't get it to work with Order jaxb elements nested in Orders,
		// therefore I need to workaround that by passing in order elements (not orders element) to the service.
		String CXF_RS_ENDPOINT_URI = "cxfrs://http://localhost:9090/gadgetdivision?resourceClasses=org.doslande.OrderServiceResource";

		Endpoint accountingQueue = endpoint("activemq:queue:accounting");
		
		DataFormat jaxbformat = new JaxbDataFormat("org.doslande.model");
		
		
		// now expects one "order" element in the body. not "orders". 
		from(CXF_RS_ENDPOINT_URI)
			.setExchangePattern(ExchangePattern.InOnly)
				.process(new OrderProcessor())
					.choice()
						.when(method(MyValidationBean.class, "isKnownCustomer"))
							.choice()
								.when(method(MyValidationBean.class, "isOrderUnderLimit"))
									// known customer & order is under limit; send to fulfillment
									.to("sql:{{sql.insertOrder}}")
									.to("log:gadget-order-to-fulfillment")
								.otherwise()
									// order is over the limit, send to accounting
									.marshal(jaxbformat)
									.to(accountingQueue)
									.to("log:gadget-accounting-overlimit")
							.endChoice()
						.otherwise()
							// not a known customer, send to accounting
							.marshal(jaxbformat)
				            .to(accountingQueue)
							.to("log:accounting-newcustomer")
					.endChoice();
	}
}
