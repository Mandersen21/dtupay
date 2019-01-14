package beijing.tokenservice.rest;


import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;


@Path("/tokens")
public class TokensEndpoint {

	@GET
	@Produces("text/plain")
	public Response doGet() {
		return Response.ok("Hello from Thorntail!").build();
	}
}
