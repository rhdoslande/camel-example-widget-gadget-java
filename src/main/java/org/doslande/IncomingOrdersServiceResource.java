package org.doslande;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/myservice/")
public class IncomingOrdersServiceResource {
	
	public IncomingOrdersServiceResource() {
	}
	
    @POST
    @Path("/items/")
    @Produces("text/xml")
    public Response createOrders(String input) {
        return null;
    }

}
