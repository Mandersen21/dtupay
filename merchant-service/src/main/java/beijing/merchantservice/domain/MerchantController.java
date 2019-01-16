package beijing.merchantservice.domain;

import beijing.merchantservice.exception.DataAccessException;
import beijing.merchantservice.exception.RequestRejected;
import beijing.merchantservice.repository.IMerchantRepositry;
import beijing.merchantservice.repository.MerchantRepositry;

public class MerchantController {

	IMerchantRepositry repositry;

	public MerchantController() {
		repositry = new MerchantRepositry();
	}

	public TransactionObject requestTransaction(String merchantid, String tokenid, String amount) throws RequestRejected, DataAccessException {

		TokenValidation tv = getTokenValidation(tokenid);
		if(!tv.isValid()){
			throw new RequestRejected("token is invalid");
		}

		TransactionObject to;
		try{
			to = requestPayment(merchantid,tv.getCustomerId(),amount);
		}catch (Exception e){
			updateToken("tokenId","INVALID");
			throw new RequestRejected("request failed");
		}

		updateToken("tokenId","PAID");
		repositry.createTransaction(to);

		return to;
	}




	private TokenValidation getTokenValidation(String tokenId){
		//TODO: send request to tokenService through rabbitMQ
		return null;
	}

	private void updateToken(String tokenId, String status){
		//TODO: send request to tokenService through rabbitMQ
	}


	private TransactionObject requestPayment(String merchantId, String customerId, String amount){
		//TODO: send request to paymentService through rabbitMQ
		return null;
	}

}
