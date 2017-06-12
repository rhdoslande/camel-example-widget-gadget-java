package org.doslande;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.doslande.model.Customer;

@Path("/customerservice/")
public class CustomerServiceResource {

    public CustomerServiceResource() {
    }

    @GET
    @Path("/customers/{id}/")
    public Customer getCustomer(@PathParam("id") String id) {
        return null;
    }

    @POST
    @Path("/customers/")
    public Response updateCustomer(Customer customer) {
        return null;
    }
}
