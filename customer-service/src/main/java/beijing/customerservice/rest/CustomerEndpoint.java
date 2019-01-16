package beijing.customerservice.rest;


import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import beijing.customerservice.domain.Customer;
import beijing.customerservice.repository.CustomerRepository;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;


@Path("/customer")
public class CustomerEndpoint {

	CustomerRepository customerRepository = new CustomerRepository();
	Customer customer = new Customer("123", "Stephen", "123102", null);
	
	@GET
	@Produces("text/plain")
	public Response doGetName() {
		return Response.ok(customerRepository.getCustomerByName(customer.getName())).build();
	}
	
	@GET
	@Produces("text/plain")
	public Response doGetId() {
		return Response.ok(customerRepository.getCustomerByName(customer.getId())).build();
	}
	
	@GET
	@Produces("text/plain")
	public Response doGetCpr() {
		return Response.ok(customerRepository.getCustomerByName(customer.getCpr())).build();
	}
	
	@GET
	@Produces("text/plain")
	public Response doGetTokens() {
		return Response.ok(customerRepository.getTokens(customer)).build();
	}
	
	@DELETE
	@Produces("text/plain")
	public Response doRemove() {
		return Response.ok(customerRepository.removeCustomer(customer)).build();
	}
	
	@PUT
	@Produces("text/plain")
	public Response doCreateCustomer() {
		return Response.ok(customerRepository.createCustomer(customer)).build();
	}
	
}
