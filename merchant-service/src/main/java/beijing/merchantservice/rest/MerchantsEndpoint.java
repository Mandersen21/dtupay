package beijing.merchantservice.rest;


import beijing.merchantservice.domain.Merchant;
import beijing.merchantservice.domain.MerchantController;
import beijing.merchantservice.domain.TransactionObject;
import beijing.merchantservice.exception.CorruptedTokenException;
import beijing.merchantservice.exception.DataAccessException;
import beijing.merchantservice.exception.RequestRejected;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;


@Path("Merchant")
public class MerchantsEndpoint {

	private MerchantController controller;
	
	public MerchantsEndpoint() throws IOException, TimeoutException {
		controller = new MerchantController();
		Merchant m = new Merchant("123","123","qwe");
		Merchant m2 = new Merchant("1234","1234","qwee");
		controller.getRepository().createMerchant(m);
		controller.getRepository().createMerchant(m2);
	}
	
	//Return all merchants
    @GET
    @Produces("application/json")
    public Response getMerchantss() {
        return Response.ok(controller.getRepository().getMerchants(), "application/json").build();
    }
    
    //Post merchant
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response createCustomer(
    						     @FormParam("merhcantId") String merhcantId,
                                 @FormParam("CVR") String CVR,
                                 @FormParam("Name") String name) {
        return Response.ok(controller.getRepository().createMerchant(new Merchant(merhcantId, CVR, name)), "application/json").build();    
    }
	
    //Get merchant by id
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomer(@PathParam("id") String id) {
        Merchant merchant = controller.getRepository().getMerchant(id);
        if (merchant == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok(merchant, "application/json").build();
        }
    }
    
//	@GET
//	@Produces("application/json")
//	public Response doGet() {
//		Merchant m = new Merchant("123","74875858","ThornTail");
//		return Response.ok(m, "application/json").build();
//	}


	@POST
	@Path("/{id}")
	@Produces("application/json")
	public Response requestTransaction(String merchantId, String tokenId, String amount) throws CorruptedTokenException, IOException {
		TransactionObject to;
		try {
			to = controller.requestTransaction(merchantId, tokenId, amount);
		} catch (RequestRejected requestRejected) {
			requestRejected.printStackTrace();
			return Response.status(500).build();

		} catch (DataAccessException e) {
			e.printStackTrace();
			return Response.status(503).build();
		}

		return Response.ok(to, "application/json").build();
	}
	
	
}
