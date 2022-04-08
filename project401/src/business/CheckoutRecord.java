package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckoutRecord implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<CheckoutRecordEntry> checkoutRecordEntries = new ArrayList<CheckoutRecordEntry>();
	public void addCheckoutRecordEntries(CheckoutRecordEntry checkoutRecordEntry) {
		if (checkoutRecordEntries == null) {
			checkoutRecordEntries = new ArrayList<CheckoutRecordEntry>();
		}
		checkoutRecordEntries.add(checkoutRecordEntry);
	}
	public List<CheckoutRecordEntry> getCheckoutRecordEntries() {
		return checkoutRecordEntries;
	}
	
}
