package beijing.customerservice.rest;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beijing.customerservice.domain.Customer;
import beijing.customerservice.domain.CustomerManager;
import beijing.customerservice.exception.CustomerNotFoundException;
import beijing.customerservice.repository.CustomerRepository;
import beijing.customerservice.repository.ICustomerRepository;

@Path("/CustomerService")
public class CustomerEndpoint {

   private static final String FAILURE_RESULT="<result>failure</result>";

   private static ICustomerRepository customerRepository = new CustomerRepository();
   private CustomerManager customerManager;
   List<String> listToken = new ArrayList<String>();
   
   public CustomerEndpoint() {
		customerManager = new CustomerManager(customerRepository);
		listToken.add("32434");
		customerRepository.createCustomer(new Customer("12345", "123102", "Stephen", listToken));
	}
   
   @GET
   @Path("/customers/{customerId}")
   @Produces("application/json")
   public Response getCustomer(@PathParam("customerId") String customerId){
	   Customer customer = null;
		try {
			customer = customerManager.getCustomerId(customerId);
		} catch (CustomerNotFoundException e) {
			return Response.status(404).entity(FAILURE_RESULT).build();
		}
		return Response.ok(customer).build();
   }

   @POST
   @Path("/customers")
   @Produces("application/json")
   @Consumes("application/json")
   public Response createCustomer(@FormParam("name") String name,
      @FormParam("cpr") String cpr,
      @FormParam("id") String id,
      @FormParam("tokens") List<String> tokens) throws IOException{
      List<Customer> result = null;
	  try {
		  result = customerManager.addCustomer(id, cpr, name, tokens);
	  } catch (Exception e) {
		  return Response.status(404).entity(FAILURE_RESULT).build();
		}
      if(result != null){
         return Response.ok(result).build();
      }
	return null;
   }

   @DELETE
   @Path("/customers/{customerId}")
   @Produces("application/json")
   public Response deleteCustomer(@PathParam("customerId") String customerId){
      List<Customer> result = null;
      try {
    	  result = customerManager.removeCustomer(customerManager.getCustomerId(customerId));
      } catch (CustomerNotFoundException e) {
    	  e.printStackTrace();
      }
      if(result != null){
         return Response.ok(result).build();
      }
      return Response.status(404).entity(FAILURE_RESULT).build();
   }

   @OPTIONS
   @Path("/customers")
   @Produces("application/json")
   public String getSupportedOperations(){
      return "<operations>GET, POST, DELETE</operations>";
   }
}