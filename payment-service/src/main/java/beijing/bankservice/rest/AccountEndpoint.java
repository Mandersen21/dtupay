package beijing.bankservice.rest;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import beijing.bankservice.domain.BankServiceManager;
import beijing.bankservice.repository.IPaymentRepository;
import beijing.bankservice.repository.PaymentRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.ws.rs.PathParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

@Path("/accounts")
public class AccountEndpoint {

	private static IPaymentRepository repository = new PaymentRepository();
	private BankServiceManager manager;
	
	public AccountEndpoint() throws IOException, TimeoutException {
		manager = new BankServiceManager(repository);
	}
	
	@GET
	@Produces("application/json")
	public Response getAccount() {
		List<String> accounts = new ArrayList<String>();
		return Response.ok(accounts, "application/json").build();
	}
}
