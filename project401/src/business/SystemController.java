package business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import librarysystem.LibrarySystem;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;
	
	
	public void login(String id, String password) throws LoginException {
		HashMap<String, User> map =  LibrarySystem.DATA.readUserMap();
		if(!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}
		currentAuth = map.get(id).getAuthorization();
		
	}
	@Override
	public List<String> allMemberIds() {
		List<String> retval = new ArrayList<>();
		retval.addAll(LibrarySystem.DATA.readMemberMap().keySet());
		return retval;
	}
	
	@Override
	public List<String> allBookIds() {
		List<String> retval = new ArrayList<>();
		retval.addAll(LibrarySystem.DATA.readBooksMap().keySet());
		return retval;
	}
	
	// TaoWu start
		/**
		 * find a Book through isbnNum
		 * 
		 * @param isbnNum
		 * @return book
		 */
		public String searchBookByIsbn(String isbnNum) {
			HashMap<String, Book> allBooks = LibrarySystem.DATA.readBooksMap();
			StringBuffer sb = new StringBuffer();
			for (Book b : allBooks.values()) {
				if (isbnNum.equals(b.getIsbn())) {
					List<Author> authorList = b.getAuthors();
					String name = "";
					for (Author a : authorList) {
						name += a.getLastName() + " " + a.getFirstName();
						if (authorList.indexOf(a) != authorList.size() - 1) {
							name += ",";
						}
					}
					sb.append("<html><body>");
					sb.append("ISBN : " + b.getIsbn() + "<br>");
					sb.append("title : " + b.getTitle() + "<br>");
					sb.append("copyeNum : " + b.getCopies().length + "<br>");
					sb.append("maxCheckoutLength : " + b.getMaxCheckoutLength() + "<br>");
					sb.append("authors : " + name + "<br>");
					sb.append("<body></html>");
					break;
				}
			}
			return sb.toString();
		}

		/**
		 * add a Book through isbnNum
		 * 
		 * @param isbnNum
		 * @return true or false
		 */
		public boolean addBookCopyByIsbn(String isbnNum) {
			boolean rs = true;
			try {
				List<Book> bl = new ArrayList<>();
				for (Book b : LibrarySystem.DATA.readBooksMap().values()) {
					if (isbnNum.equals(b.getIsbn())) {
						b.addCopy();
						bl.add(b);
						break;
					}
				}
				DataAccessFacade.loadBookMap(bl);
			} catch (Exception ex) {
				rs = false;
				ex.printStackTrace();
			}
			return rs;
		}

		/**
		 * add a Book
		 * 
		 * @param isbnNum
		 * @param strTitle
		 * @param strMaxCheckoutLength
		 * @param strFirstName
		 * @param strLastName
		 * @param strTelephone
		 * @param strBio
		 * @param strStreet
		 * @param strCity
		 * @param strState
		 * @param strZip
		 * 
		 * @return true or false
		 */
		public boolean addBook(String isbnNum, String strTitle, String strMaxCheckoutLength, String strFirstName,
				String strLastName, String strTelephone, String strBio, String strStreet, String strCity,
				String strState, String strZip) {
			boolean rs = true;
			try {
				List<Book> bl = new ArrayList<Book>();
				for (Book b : LibrarySystem.DATA.readBooksMap().values()) {
					bl.add(b);
				}
				List<Author> authors = new ArrayList<Author>();
				String[] strFirstNames = strFirstName.split(",");
				String[] strLastNames = strLastName.split(",");
				String[] strTelephones = strTelephone.split(",");
				String[] strBios = strBio.split(",");
				String[] strStreets = strStreet.split(",");
				String[] strCitys = strCity.split(",");
				String[] strStates = strState.split(",");
				String[] strZips = strZip.split(",");
				for (int i = 0; i < strFirstNames.length; i++) {
					Address address = new Address(strStreets[i], strCitys[i], strStates[i], strZips[i]);
					Author author = new Author(strFirstNames[i], strLastNames[i], strTelephones[i], address,
							strBios[i]);
					authors.add(author);
				}
				Book book = new Book(isbnNum, strTitle, Integer.parseInt(strMaxCheckoutLength), authors);
				bl.add(book);
				DataAccessFacade.loadBookMap(bl);
			} catch (Exception ex) {
				rs = false;
				ex.printStackTrace();
			}
			return rs;
		}

		/**
		 * find a libraryMember through memberId
		 * @param memberId
		 * @return libraryMemberInfo
		 */
		public String searchLibraryMemberById(String memberId) {
			StringBuffer sb = new StringBuffer();
			for (LibraryMember lm : LibrarySystem.DATA.readMemberMap().values()) {
				if (memberId.equals(lm.getMemberId())) {
					sb.append("<html><body>");
					sb.append("memberId : " + lm.getMemberId() + "<br>");
					sb.append("firstName : " + lm.getFirstName() + "<br>");
					sb.append("lastName : " + lm.getLastName() + "<br>");
					sb.append("telephone : " + lm.getTelephone() + "<br>");

					sb.append("street : " + lm.getAddress().getStreet() + "<br>");
					sb.append("city : " + lm.getAddress().getCity() + "<br>");
					sb.append("state : " + lm.getAddress().getState() + "<br>");
					sb.append("zip : " + lm.getAddress().getZip() + "<br>");
					for (int i = 0; i < lm.getCheckoutRecord().getCheckoutRecordEntries().size(); i++) {
						CheckoutRecordEntry cre = lm.getCheckoutRecord().getCheckoutRecordEntries().get(i);
						sb.append("CheckoutRecordEntry[" + i + "] CheckoutDate : " + cre.getCheckoutDate().toString()
								+ " DueDate : " + cre.getDueDate().toString() + " BookCopy : (CopyNum : "
								+ cre.getBookCopy().getCopyNum() + " isAvailable : " + cre.getBookCopy().isAvailable() + " Book{"
								+ cre.getBookCopy().getBook().toString() + ",copyNum:"+cre.getBookCopy().getBook().getCopies().length+"})" + "<br>");
					}

					sb.append("<body></html>");
					break;
				}
			}
			return sb.toString();
		}
		
		/**
		 * print a checkedRecord to Console through memberId
		 * @param memberId
		 * @return libraryMemberInfo
		 */
		public String printCheckedRecordToConsole(String memberId) {
			String str =  searchLibraryMemberById(memberId);
			str = str.replace("<br>", "\n");
			str = str.replace("<html><body>", "");
			str = str.replace("<body></html>", "");
			return str;
		}

		/**
		 * isNumeric
		 * 
		 * @param str
		 * @return true or false
		 */
		public boolean isNumeric(String str) {
			Pattern pattern = Pattern.compile("[0-9]*");
			Matcher isNum = pattern.matcher(str);
			if (!isNum.matches()) {
				return false;
			}
			return true;
		}

		// TaoWu end
	
	
}
