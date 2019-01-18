package beijing.customerservice.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import beijing.customerservice.domain.Customer;
import beijing.customerservice.domain.CustomerManager;
import beijing.customerservice.exception.CustomerNotFoundException;
import beijing.customerservice.exception.RequestRejected;
import java.io.IOException;
import java.util.List;

@Path("customers")
public class CustomerRestService {
    private CustomerManager customerManager;

    //localhost:8080/customers (Need to place a json file in the Body)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response createCustomer(@FormParam("id") String id,
    						     @FormParam("name") String name,
                                 @FormParam("cpr") String cpr,
                                 @FormParam("tokenList") List<String> tokenList) {
        try {
        	if (customerManager.addCustomer(id, cpr, name, tokenList)) {
        		return Response.ok().entity(customerManager.getCustomerById(id)).build();
        	}
        	return Response.status(500).build();
		} catch (RequestRejected e) {
			return Response.status(404).build();
		} catch (IOException e) {
			return Response.status(500).build();
		} catch (CustomerNotFoundException e) {
			return Response.status(404).build();
		}
    }

    //localhost:8080/customers/1 (if it exists, otherwise - create it first)
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomer(@PathParam("id") String id) {
        Customer customer;
		try {
			customer = customerManager.getCustomerById(id);
		} catch (CustomerNotFoundException e) {
			return Response.status(404).build();
		}
		
        if (customer == null) {
            return Response.status(404).build();
        } else {
            return Response.ok().entity(customer).build();
        }
    }
    
    //localhost:8080/customers/1 (Deletes the customer with id 1)
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCustomer(@PathParam("id") String id) {
    	Customer customer;
		try {
			customer = customerManager.getCustomerById(id);
		} catch (CustomerNotFoundException e) {
			return Response.status(404).build();
		}
		
    	if (customer == null) {
            return Response.status(404).build();
        } else {
            try {
				return Response.ok().entity(customerManager.removeCustomer(customer)).build();
			} catch (CustomerNotFoundException e) {
				return Response.status(404).build();
			}
        }
    }
}