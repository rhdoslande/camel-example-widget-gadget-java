package org.doslande;

import org.apache.camel.Endpoint;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.xml.XPathBuilder;
import org.doslande.model.MyValidationBean;

public class ValidateWidgetRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		// start from "widgetDivision" queue
		// get the customer, if not known in the system, send to accounting queue
		// get order "amount" from xml input, if less than 50K, send to fulfillment db
		// otherwise send to accounting queue
		
		MyValidationBean myBean = new MyValidationBean();
		
		XPathBuilder xPathBuilder = new XPathBuilder("//orders/order");
		
		Endpoint widgetFullfillmentFileEndpoint = endpoint("file:src/test/resources/fulfillment?fileName=fulfillment_widget_out.xml&fileExist=Append");
		Endpoint widgetAccountingFileEndpoint = endpoint("file:src/test/resources/accounting?fileName=accounting_widget_out.xml&fileExist=Append");
		Endpoint widgetOrdersFileStartPoint = endpoint("file:src/test/resources/from-rest?noop=true&fileName=rest_widget_orders.xml");
		
		Endpoint widgetDivisionQueue = endpoint("activemq:queue:widgetDivision");
		Endpoint fulfillmentQueue = endpoint("activemq:queue:fulfillment");
		Endpoint accountingQueue = endpoint("activemq:queue:accounting");
		
		from(widgetDivisionQueue)
		.split(xPathBuilder)
			.choice()
				.when(method(MyValidationBean.class, "isKnownCustomer"))
					.choice()
						.when(method(MyValidationBean.class, "isOrderUnderLimit"))
							// known customer and order is under the limit, send to fulfillment
							.to("xslt:orders.xsl")
							.to(fulfillmentQueue)
							.to("log:fulfillment")
						.otherwise()
							// order is over the limit, send to accounting
							.to("xslt:orders.xsl")
							.to(accountingQueue)
							.to("log:accounting-overlimit")
					.endChoice()
				.otherwise()
					// not a known customer, send to accounting
					.to("xslt:orders.xsl")
		            .to(accountingQueue)
					.to("log:accounting-newcustomer")
			.endChoice();				;

	}

}
