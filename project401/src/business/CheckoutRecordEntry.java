package business;

import java.io.Serializable;
import java.time.LocalDate;

public class CheckoutRecordEntry implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	LocalDate checkoutDate;
	LocalDate dueDate;
	BookCopy bookCopy;

	//@rstephens - only need bookCopy
	public CheckoutRecordEntry(BookCopy bookCopy) {
		this.checkoutDate = LocalDate.now();
		this.dueDate = checkoutDate.plusDays(bookCopy.getBook().getMaxCheckoutLength());;
		this.bookCopy = bookCopy;
	}

	public CheckoutRecordEntry(LocalDate checkoutDate, LocalDate dueDate, BookCopy bookCopy) {
		super();
		this.checkoutDate = checkoutDate;
		this.dueDate = dueDate;
		this.bookCopy = bookCopy;
	}




	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public BookCopy getBookCopy() {
		return bookCopy;
	}
	
	
}
