package beijing.merchantservice.rest;


import beijing.merchantservice.domain.MerchantController;
import beijing.merchantservice.domain.TransactionObject;
import beijing.merchantservice.exception.DataAccessException;
import beijing.merchantservice.exception.RequestRejected;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;


@Path("/Merchant")
public class MerchantsEndpoint {

	MerchantController controller = new MerchantController();

	@GET
	@Produces("text/plain")
	public Response doGet() {
		return Response.ok("Hello from Thorntail!").build();
	}
//
//
//	@POST
//	@Produces("application/json")
//	public Response requestTransaction(String merchantId, String tokenId, String amount) {
//		TransactionObject to;
//		try {
//			to = controller.requestTransaction(merchantId, tokenId, amount);
//		} catch (RequestRejected requestRejected) {
//			requestRejected.printStackTrace();
//			return Response.status(500).build();
//
//		} catch (DataAccessException e) {
//			e.printStackTrace();
//			return Response.status(503).build();
//		}
//
//		return Response.ok(to, "application/json").build();
//	}
}
