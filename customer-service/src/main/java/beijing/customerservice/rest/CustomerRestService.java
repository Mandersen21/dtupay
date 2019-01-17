package beijing.customerservice.rest;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beijing.customerservice.domain.Customer;
import beijing.customerservice.exception.CustomerNotFoundException;
import beijing.customerservice.repository.CustomerRepository;

import java.util.List;

@Path("customers")
public class CustomerRestService {
    private CustomerRepository dataService = CustomerRepository.getInstance();

    //localhost:8080/customers (Output all the customers)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> getCustomers() {
        return dataService.getCustomerList();
    }

    //localhost:8080/customers (Need to place a json file in the Body)
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String createCustomer(
    						     @FormParam("name") String name,
                                 @FormParam("cpr") String cpr,
                                 @FormParam("tokenList") List<String> tokenList) {
        return dataService.addCustomer(name, cpr, tokenList);
    }

    //localhost:8080/customers/1 (if it exists, otherwise - create it first)
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomer(@PathParam("id") String id) {
        Customer customer = dataService.getCustomerById(id);
        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok().entity(customer).build();
        }
    }
    
    //localhost:8080/customers/1 (Deletes the customer with id 1)
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCustomer(@PathParam("id") String id) {
    	Customer customer = dataService.getCustomerById(id);
    	if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok().entity(dataService.removeCustomer(customer)).build();
        }
    }
}