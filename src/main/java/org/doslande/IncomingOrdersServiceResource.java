package org.doslande;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

//import org.doslande.model.Order;
import org.doslande.model.Orders;

@Path("/myservice/")
public class IncomingOrdersServiceResource {
	
	public IncomingOrdersServiceResource() {
		
	}
	
    @POST
    @Path("/items/")
    public Response createOrders(String input) {
        return null;
    }

}
