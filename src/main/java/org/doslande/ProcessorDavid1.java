package org.doslande;

import javax.servlet.ServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.doslande.model.Customer;


public class ProcessorDavid1 implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
        Message inMessage = exchange.getIn();                        
        // Get the operation name from in message
        String operationName = inMessage.getHeader(CxfConstants.OPERATION_NAME, String.class);
        if ("getCustomer".equals(operationName)) {
            String httpMethod = inMessage.getHeader(Exchange.HTTP_METHOD, String.class);
//            assertEquals("Get a wrong http method", "GET", httpMethod);
            String path = inMessage.getHeader(Exchange.HTTP_PATH, String.class);
            // The parameter of the invocation is stored in the body of in message
            String id = inMessage.getBody(String.class);
            if ("/customerservice/customers/126".equals(path)) {                            
                Customer customer = new Customer();
                customer.setId(Long.parseLong(id));
                customer.setName("Willem");
                // We just put the response Object into the out message body
                exchange.getOut().setBody(customer);
            } else {
                if ("/customerservice/customers/400".equals(path)) {
                    // We return the remote client IP address this time
                    org.apache.cxf.message.Message cxfMessage = inMessage.getHeader(CxfConstants.CAMEL_CXF_MESSAGE, org.apache.cxf.message.Message.class);
                    ServletRequest request = (ServletRequest) cxfMessage.get("HTTP.REQUEST");
                    String remoteAddress = request.getRemoteAddr();
                    Response r = Response.status(200).entity("The remoteAddress is " + remoteAddress).build();
                    exchange.getOut().setBody(r);
                    return;
                }
                if ("/customerservice/customers/123".equals(path)) {
                    // send a customer response back
                    Response r = Response.status(200).entity("customer response back!").build();
                    exchange.getOut().setBody(r);
                    return;
                }
                if ("/customerservice/customers/456".equals(path)) {
                    Response r = Response.status(404).entity("Can't found the customer with uri " + path).build();
                    throw new WebApplicationException(r);
                } else {
                    throw new RuntimeCamelException("Can't found the customer with uri " + path);
                }
            }
        }
        if ("updateCustomer".equals(operationName)) {
        	System.out.println("updateCustomer block!");
            Customer customer = inMessage.getBody(Customer.class);            
            System.out.println("customer toString is: " + customer);
            exchange.getOut().setBody(customer);
        }
	}
}
