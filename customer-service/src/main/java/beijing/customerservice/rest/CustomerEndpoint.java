package beijing.customerservice.rest;


import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

import beijing.customerservice.domain.Customer;
import beijing.customerservice.domain.CustomerManager;
import beijing.customerservice.repository.CustomerRepository;
import beijing.customerservice.repository.ICustomerRepository;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;


@Path("/cutomer")
public class CustomerEndpoint {
	
	private static ICustomerRepository customerRepository = new CustomerRepository();
	private CustomerManager customerManager;
	ObjectMapper mapper = new ObjectMapper();

	public CustomerEndpoint() {
		customerManager = new CustomerManager(customerRepository);
		customerRepository.createCustomer(new Customer("123", "Stephen", "123102", null));
	}
	
	@GET
	@Path("/{customerId}") 
	@Produces("application/json")
	public Response getCustomerById(@PathParam("customerId") String customerId) throws Exception {
		Customer customer = customerManager.getCustomerId(customerId);
		return Response.ok(customer, "application/json").build();
	}
	
	@GET
	@Path("/{customerName}") 
	@Produces("application/json")
	public Response getCustomerByName(@PathParam("customerName") String customerName) throws Exception {
		Customer customer = customerManager.getCustomerName(customerName);
		return Response.ok(customer, "application/json").build();
	}
	
}
