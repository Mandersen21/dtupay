package beijing.customerservice.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import beijing.customerservice.domain.Customer;
import beijing.customerservice.domain.CustomerManager;
import beijing.customerservice.exception.ConnectionException;
import beijing.customerservice.exception.CustomerNotFoundException;
import beijing.customerservice.exception.RequestRejected;
import beijing.customerservice.repository.CustomerRepository;
import beijing.customerservice.repository.ICustomerRepository;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

@Path("customers")
public class CustomerRestService {
	private static ICustomerRepository repository = new CustomerRepository();
    private CustomerManager customerManager;
    
    public CustomerRestService() throws IOException, TimeoutException, ConnectionException {
    	try {
			customerManager = new CustomerManager(repository);
		} catch (ConnectionException e) {
			throw new ConnectionException("Connection lost");
		}
		repository.createCustomer(new Customer("123","Mikkel","2141235432",null));
	}

    //localhost:8080/customers (Need to place a json file in the Body)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response createCustomer(
    						     @FormParam("name") String name,
                                 @FormParam("cpr") String cpr,
                                 @FormParam("tokenList") List<String> tokenList) {
        try {
        	String id = UUID.randomUUID().toString();
        	if (customerManager.addCustomer(id, cpr, name, tokenList)) {
        		return Response.ok(customerManager.getCustomerById(id), "application/json").build();
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