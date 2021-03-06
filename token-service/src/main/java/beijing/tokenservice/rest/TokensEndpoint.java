package beijing.tokenservice.rest;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import beijing.tokenservice.domain.Token;
import beijing.tokenservice.domain.TokenManager;
import beijing.tokenservice.domain.TokenRepresentation;
import beijing.tokenservice.exception.CustomerNotFoundException;
import beijing.tokenservice.exception.DataAccessException;
import beijing.tokenservice.exception.RequestRejected;
import beijing.tokenservice.exception.TokenNotFoundException;
import beijing.tokenservice.repository.CustomerRepository;
import beijing.tokenservice.repository.ICustomerRepository;
import beijing.tokenservice.repository.ITokenRepository;
import beijing.tokenservice.repository.TokenRepository;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/tokens")
public class TokensEndpoint {

	private static ITokenRepository tRepository = new TokenRepository();
	private static ICustomerRepository cRepository = new CustomerRepository(); 
	private TokenManager tokenManager;

	public TokensEndpoint() throws IOException, TimeoutException {
		tokenManager = new TokenManager(tRepository, cRepository);
	}
	
    /**
     * 
     * @param tokenId
     * @return A token based on a given path param "tokenId".
     */
	@GET
	@Path("/{tokenId}") 
	@Produces("application/json")
	public Response getToken(@PathParam("tokenId") String tokenId) {
		Token token = null;
		try {
			token = tokenManager.getToken(tokenId);
		} catch (TokenNotFoundException e) {
			return Response.status(404).entity("Token was not found").build();
		}
		return Response.ok(token, "application/json").build();
	}
	
    /**
     * 
     * @param customerId
     * @return Get all tokens based on customerId, or if not provided, it will return every token.
     * @throws IOException
     * @throws TimeoutException
     */
	@GET
	@Produces("application/json")
	public Response getAllTokens(@QueryParam("customerId") String customerId) throws IOException, TimeoutException {
		List<Token> tokens = null;
		try {
			tokens = tokenManager.getAllTokens(customerId);
		} catch (Exception e) {
			return Response.status(406).entity("Request has been rejected").build();
		}
		return Response.ok(tokens, "application/json").build();
	}
	
    /**
     * 
     * @param customerId
     * @param tokenAmount
     * @return Request tokens based on the provided customerId and the amount of tokens 1-5.
     */
	@POST
	@Produces("application/json")
	public Response getTokens(@QueryParam("customerId") String customerId, @QueryParam("tokenAmount") int tokenAmount) {
		List<TokenRepresentation> tokens = null;
		try {
			tokens = tokenManager.requestToken(customerId, tokenAmount);
		} catch (RequestRejected e) {
			return Response.status(406).entity("Request has been rejected").build();
		} catch (DataAccessException e) {
			return Response.status(406).entity("Data access error").build();
		}
		 catch (CustomerNotFoundException e) {
			 return Response.status(406).entity("Customer was not found").build();
		 }
		catch (Exception e) {
			return Response.status(500).entity("Internal server error").build();
		}
		return Response.ok(tokens, "application/json").build();
	}
	
    /**
     * 
     * @param customerId
     * @return Delete tokens based on the provided customerId, or if not provided deletes every token.
     */
	@DELETE
	@Produces("application/json")
	public Response deleteTokens(@QueryParam("customerId") String customerId) {
		boolean response = tokenManager.deleteTokens(customerId);
		return Response.ok(Response.status(200),"application/json").build();
	}
	
}
