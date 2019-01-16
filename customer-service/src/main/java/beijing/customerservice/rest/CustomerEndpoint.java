package beijing.customerservice.rest;


import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

import beijing.customerservice.domain.Customer;
import beijing.customerservice.domain.CustomerManager;
import beijing.customerservice.exception.CustomerNotFoundException;
import beijing.customerservice.repository.CustomerRepository;
import beijing.customerservice.repository.ICustomerRepository;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;


@Path("/customer")
public class CustomerEndpoint {
	
	private static ICustomerRepository customerRepository = new CustomerRepository();
	private CustomerManager customerManager;
	ObjectMapper mapper = new ObjectMapper();
	List<String> listToken = new ArrayList<String>();

	public CustomerEndpoint() {
		customerManager = new CustomerManager(customerRepository);
		listToken.add("123");
		customerRepository.createCustomer(new Customer("123", "123102", "Stephen", listToken));
	}
	
	@GET
	@Path("/{customerId}")
	@Produces("application/json")
	public Response getCustomerId(@PathParam("customerId") String customerId) {
		Customer customer = null;
		try {
			customer = customerManager.getCustomerId(customerId);
		} catch (CustomerNotFoundException e) {
			return Response.status(404).entity("Customer was not found").build();
		} 
		return Response.ok(customer, "application/json").build();
	}
	
	@GET
	@Path("/{customerToken}")
	@Produces("application/json")
	public Response getCustomerTokem(@PathParam("customerToken") String customerToken) {
		List<String> tokens = null;
		try {
			tokens = customerManager.getCustomerToken(customerToken);
		} catch (CustomerNotFoundException e) {
			return Response.status(404).entity("Customer was not found").build();
		} 
		return Response.ok(tokens, "application/json").build();
	}
	
	@PUT
	@Produces("application/json")
	public Response putCustomer(@PathParam("customerId") String customerId, @PathParam("customerId") String cpr, 
								@PathParam("customerId") String name, @PathParam("customerId") List<String> tokenList) {
		boolean customer;
		try {
			customer = customerManager.addCustomer(customerId, cpr, name, tokenList);
		} catch (Exception e) {
			return Response.status(404).entity("Customer was not found").build();
		} 
		return Response.ok(customer, "application/json").build();
	}
	
}
