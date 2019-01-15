package beijing.customerservice.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import beijing.customerservice.domain.Customer;
import beijing.customerservice.domain.Persistency;
public class DataAccessRepository implements Serializable {
	private static final long serialVersionUID = 5057209122447393507L;

	// Persistency related objects
	String filename = "persistencyCustomer.txt";

	// User Register
	private Set<Customer> reg;

	// Constructor
	public DataAccessRepository() {
		Set<Customer> loadedInformation = Persistency.load(filename);
		if (loadedInformation != null) {
			reg = (Set<Customer>) loadedInformation;
		} else {
			reg = new TreeSet<Customer>();
		}
	}

	public void save() {
		Persistency.save(filename, reg);
	}

	// Check existence of user
	public boolean customerExists(Customer customer) {
		return reg.contains(customer);
	}

	// Sign up user in system
	public void signUp(Customer customer) {
		reg.add(customer);
		this.save();
	}

	// Remove user in system
	public boolean remove(Customer customer) {
		if (reg.contains(customer)) {
			reg.remove(customer);
			this.save();
			return true;
		} else {
			return false;
		}
	}

	// Searching users in system
	public ArrayList<Customer> searchUserByName(String name) {
		ArrayList<Customer> searchResults = new ArrayList<Customer>();
		for (Customer customer : reg) {
			if (customer.firstName.equals(name) || customer.lastName.equals(name)) {
				searchResults.add(customer);
		
			}
		}
		return searchResults;
	}
	
	
	public ArrayList<Customer> showAll() {
		ArrayList<Customer> searchResults = new ArrayList<Customer>();
		for (Customer customer : reg) {
			searchResults.add(customer);
		}
		return searchResults;
	}
	
}