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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

@Path("/customers")
public class CustomerEndpoint {

	private static ICustomerRepository repository = new CustomerRepository();
	private CustomerManager customerManager;

	public CustomerEndpoint() throws IOException, TimeoutException, ConnectionException {
		customerManager = new CustomerManager(repository);
		repository.createCustomer(new Customer("123", "Mikkel", "2141235432", new ArrayList<String>()));
	}

	@POST
	@Produces("application/json")
	public Response createCustomer(@FormParam("name") String name, @FormParam("cpr") String cpr,
			@FormParam("tokenList") List<String> tokenList) {
		try {
			String id = UUID.randomUUID().toString();
			if (customerManager.addCustomer(id, cpr, name, tokenList)) {
				return Response.ok(customerManager.getCustomerById(id), "application/json").build();
			}
			return Response.status(500).entity("RequestRejected for addCustomer").build();
		} catch (RequestRejected e) {
			return Response.status(404).entity("RequestRejected").build();
		} catch (IOException e) {
			return Response.status(500).entity("IOException").build();
		} catch (CustomerNotFoundException e) {
			return Response.status(404).entity("CustomerNotFoundException").build();
		} catch (TimeoutException e) {
			return Response.status(500).entity("TimeoutException for RabbitMQ").build();
		}
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomer(@PathParam("id") String id) {
		Customer customer;
		try {
			customer = customerManager.getCustomerById(id);
		} catch (CustomerNotFoundException e) {
			return Response.status(404).entity("CustomerNotFoundException").build();
		}

		if (customer == null) {
			return Response.status(404).entity("CustomerNotFoundException").build();
		} else {
			return Response.ok().entity(customer).build();
		}
	}

	@GET
	@Produces("application/json")
	public Response getCustomers() {
		List<Customer> customers;
		try {
			customers = customerManager.getAllCustomers();
		} catch (Exception e) {
			return Response.status(404).entity("CustomerNotFoundException").build();
		}
		return Response.ok(customers, "application/json").build();
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCustomer(@PathParam("id") String id) {
		Customer customer;
		try {
			customer = customerManager.getCustomerById(id);
		} catch (CustomerNotFoundException e) {
			return Response.status(404).entity("CustomerNotFoundException").build();
		}

		if (customer == null) {
			return Response.status(404).entity("CustomerNotFoundException").build();
		} else {
			try {
				return Response.ok().entity(customerManager.removeCustomer(customer)).build();
			} catch (CustomerNotFoundException e) {
				return Response.status(404).entity("CustomerNotFoundException").build();
			}
		}
	}
}