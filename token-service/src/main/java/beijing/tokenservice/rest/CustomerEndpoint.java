package beijing.tokenservice.rest;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

import beijing.tokenservice.domain.CustomerManager;
import beijing.tokenservice.repository.CustomerRepository;
import beijing.tokenservice.repository.ICustomerRepository;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.ws.rs.GET;


@Path("/customers")
public class CustomerEndpoint {

	private static ICustomerRepository repository = new CustomerRepository();
	private CustomerManager customerManager;
	ObjectMapper mapper = new ObjectMapper();
	private List<String> customers = null;

	public CustomerEndpoint() throws IOException, TimeoutException {
		customerManager = new CustomerManager(repository);
	}
	
	@GET
	@Produces("application/json")
	public Response getCustomers() throws IOException, TimeoutException {

		try {
			customers = customerManager.getCustomers();
		} catch (Exception e) {
			return Response.status(406).entity("Request has been rejected").build();
		}
		return Response.ok(customers, "application/json").build();
	}
}
