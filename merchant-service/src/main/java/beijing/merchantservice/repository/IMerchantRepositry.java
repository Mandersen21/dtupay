package beijing.merchantservice.repository;

import java.util.List;

import beijing.merchantservice.domain.Merchant;

public interface IMerchantRepositry {

	public int createMerchant(Merchant uid);	
	public Merchant getMerchant(String uid);
	
	
	
}
