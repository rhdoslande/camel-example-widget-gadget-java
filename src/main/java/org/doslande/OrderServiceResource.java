package org.doslande;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.doslande.model.Order;

@Path("/orderservice/")
public class OrderServiceResource {
	
    public OrderServiceResource() {
    }

    @POST
    @Path("/order/")
    public Response createOrder(Order order) {
        return null;
    }
}
