package org.doslande;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

//import org.doslande.model.Order;
import org.doslande.model.Orders;

@Path("/orderservice/")
public class IncomingOrdersServiceResource {
	
	public IncomingOrdersServiceResource() {
		
	}
	
    @POST
    @Path("/gadgets/")
    public Response createOrders(Orders orders) {
        return null;
    }

}
