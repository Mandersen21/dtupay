package beijing.merchantservice.rest;

import beijing.merchantservice.domain.Merchant;
import beijing.merchantservice.domain.MerchantManager;
import beijing.merchantservice.domain.TransactionObject;
import beijing.merchantservice.exception.CorruptedTokenException;
import beijing.merchantservice.exception.DataAccessException;
import beijing.merchantservice.exception.RequestRejected;
import beijing.merchantservice.repository.IMerchantRepository;
import beijing.merchantservice.repository.MerchantRepository;

import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.tools.ant.types.Description;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.ws.rs.PathParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/merchants")
public class MerchantsEndpoint {

	private static IMerchantRepository repository = new MerchantRepository();
	protected MerchantManager controller;

	/**
	 * creates the Endpoint for the merchat-service
	 * instaciates the MerchantManager with a given repository
	 * @throws IOException
	 * @throws TimeoutException
	 */
	public MerchantsEndpoint() {
		controller = new MerchantManager(repository);
//		try {
//			controller.receiveNewTokens("123,123456");
//		} catch (DataAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	/**
	 * requests transaction from the owner of token to the merchant
	 * for the given amount
	 * @param merchantId
	 * @param tokenId
	 * @param amount
	 * @return
	 */
	@PUT
	@Produces("application/json")
	public Response requestTransaction(
			@QueryParam("merchantId") String merchantId, 
			@QueryParam("tokenId") String tokenId,
			@QueryParam("amount") String amount) {
		TransactionObject to;
		try {
			to = controller.requestTransaction(merchantId, tokenId, amount, "DTU Pay Service");
		} catch (RequestRejected requestRejected) {
			return Response.status(Response.Status.BAD_REQUEST).entity(requestRejected.getMessage()).build();
		} catch (DataAccessException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		} catch (CorruptedTokenException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		} catch (IOException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}

		return Response.ok(to, MediaType.APPLICATION_JSON).build();
	}

	/**
	 * creates a new merchant and stores it in the repository.
	 * @param merhcantId
	 * @param CVR
	 * @param name
	 * @return
	 */
	@POST
	@Produces("application/json")
	public Response createMerchant(@FormParam("cvr") String CVR, @FormParam("name") String name) {

		Merchant m;
		try {
			m = controller.createMerchant(CVR, name);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		} catch (RequestRejected e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
		if (m == null) {
			String message = "Failed";
			return Response.status(404).entity(message).build();
		}
		return Response.ok(m, MediaType.APPLICATION_JSON).build();
	}

	/**
	 * retrieves a merchant from the repository with the given id
	 * @param id
	 * @return
	 */
	@GET
	@Path("/{merchantId}") 
	@Produces("application/json")
	public Response getMerchant(@PathParam("merchantId") String merchantId) {
		Merchant merchant = null;

		try {
			merchant = controller.getMerchantById(merchantId);
		} catch (DataAccessException e) {
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.ok(merchant, "application/json").build();
	}

	/**
	 * Retrieves a list of all merchants stored in the database.
	 * @return
	 */
	@GET
	@Produces("application/json")
	public Response getAllMerhcants() {
		List<Merchant> merchantList = null;
		try {
			merchantList = controller.getAllMerhcants();
		} catch (DataAccessException e) {
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		}
		return Response.ok(merchantList, "application/json").build();
	}
	
	/**
	 * retrieves a list of transactions that involves the given user
	 * @param id
	 * @return list of transactions
	 */
	@GET
	@Path("/transactions/{userId}") 
	@Produces("application/json")
	public Response getTransactionList(@PathParam("userId") String userId) {
		List<TransactionObject> transactionList = null;

		try {
			transactionList = controller.getTransactionsById(userId);
		} catch (DataAccessException e) {
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.ok(transactionList, "application/json").build();
	}
	
	/**
	 * retrieves the full list of transactions
	 * @return list of transactions
	 */
	@GET
	@Path("/transactions") 
	@Produces("application/json")
	public Response getFullTransactionList() {
		List<TransactionObject> transactionList = null;
		try {
			transactionList = controller.getTransactions();
		} catch (DataAccessException e) {
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.ok(transactionList, "application/json").build();
	}

}
