package beijing.merchantservice.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import beijing.merchantservice.domain.Merchant;

public class MerchantRepositry implements IMerchantRepositry {

	List<Merchant> merchantList;

	@Override
	public int createMerchant(Merchant uid) {
		if (merchantList.contains(uid)) {
			return 0;
		}

		merchantList.add(uid);

		return 1;

	}

	@Override
	public Merchant getMerchant(String uid) {
		if (uid == null) {
			return null;
		}
		for (Merchant m : merchantList) {
			if (m.getMerchantID().equals(uid)) {
				return m;
			}

		}
		return null;
	}
}
