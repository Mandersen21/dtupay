package beijing.merchantservice.rest;


import beijing.merchantservice.domain.Merchant;
import beijing.merchantservice.domain.MerchantController;
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

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;


@Path("/merchants")
public class MerchantsEndpoint {
	
	private static IMerchantRepository repository = new MerchantRepository();
	protected MerchantController controller;
	
	/**
	 * 
	 * @throws IOException
	 * @throws TimeoutException
	 */
	public MerchantsEndpoint()  {
		controller = new MerchantController(repository);
	}
	
	/**
	 * 
	 * @param merchantId
	 * @param tokenId
	 * @param amount
	 * @return
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response requestTransaction(
									@FormParam("merchantId") String merchantId,
									@FormParam("tokeId") String tokenId,
									@FormParam("amount") String amount){
		TransactionObject to;
		try {
			to = controller.requestTransaction(merchantId, tokenId, amount);
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response createMerchant(
                                 @FormParam("CVR") String CVR,
                                 @FormParam("Name") String name) {
    	
    		Merchant m;
			try {
				m = controller.createMerchant( CVR, name);
			} catch (DataAccessException e) {
				e.printStackTrace();
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
			} catch (RequestRejected e) {
				e.printStackTrace();
				return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
			}
    		if(m == null) {
    			String message ="Failed";
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
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMerchant(@PathParam("id") String id) {
    	Merchant merchant = null;
    	
         try {
			merchant = controller.getMerchantById(id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
         
//        if (merchant == null) {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        } 
        return Response.ok(merchant, "application/json").build();
        
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMerhcants() {
    	List<Merchant> merchantList = null;
		try {
			merchantList = controller.getAllMerhcants();
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return Response.ok(merchantList, "application/json").build();
    	
    }
    

}
