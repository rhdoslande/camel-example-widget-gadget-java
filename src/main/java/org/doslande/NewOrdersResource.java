package org.doslande;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.doslande.model.Order;
import org.doslande.model.PostmanOrder;

@Path("/myrestapp")
public class NewOrdersResource {

    // NOTE: The instance member variables will not be available to the
    // Camel Exchange. They must be used as method parameters for them to
    // be made available
    @Context
    private UriInfo uriInfo;

    public NewOrdersResource() {
    }

//    @GET
//    @Path("/customers/{id}/")
//    @Produces("text/xml")
//    public Customer getCustomer(@PathParam("id") String id) {
//        return null;
//    }

    @POST
    @Path("/create")
//    @Consumes("text/xml")
//    @Consumes("application/xml")
    public Response createOrder(Order argOrder) {
        return null;
    }
}