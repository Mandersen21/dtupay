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

@Path("/merchants")
public class MerchantsEndpoint {

	private static IMerchantRepository repository = new MerchantRepository();
	protected MerchantManager controller;

	/**
	 * 
	 * @throws IOException
	 * @throws TimeoutException
	 */
	public MerchantsEndpoint() {
		controller = new MerchantManager(repository);
		try {
			controller.receiveNewTokens("123,123");
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param merchantId
	 * @param tokenId
	 * @param amount
	 * @return
	 */
	@PUT
	@Produces("application/json")
	public Response requestTransaction(@FormParam("merchantId") String merchantId, @FormParam("tokenId") String tokenId,
			@FormParam("amount") String amount) {
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
	 * 
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
	 * 
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

}
