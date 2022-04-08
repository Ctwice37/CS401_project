package librarysystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;

import business.Book;

/**
 * AddBookWindow
 * @author TaoWu
 *
 */
public class AddBookWindow extends JFrame implements LibWindow {
	public static final AddBookWindow INSTANCE = new AddBookWindow();

	private boolean isInitialized = false;

	private JPanel mainPanel;
	private JPanel upperHalf;
	private JPanel middleHalf;
	private JPanel lowerHalf;

	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;
	private JPanel leftTextPanel;

	private JTextField isbn;
	private JTextField title;
	private JTextField maxCheckoutLength;
	private JTextField firstName;
	private JTextField lastName;
	private JTextField telephone;
	private JTextField bio;
	private JTextField street;
	private JTextField city;
	private JTextField state;
	private JTextField zip;

	private JButton addBookButton;
	private List<Book> allBooks;

	public List<Book> getAllBooks() {
		return allBooks;
	}

	public boolean isInitialized() {
		return isInitialized;
	}

	public void isInitialized(boolean val) {
		isInitialized = val;
	}

	private JTextField messageBar = new JTextField();

	public void clear() {
		messageBar.setText("");
	}

	/* This class is a singleton */
	private AddBookWindow() {
	}

	public void init() {
		mainPanel = new JPanel();
		defineUpperHalf();
		defineMiddleHalf();
		defineLowerHalf();
		BorderLayout bl = new BorderLayout();
		bl.setVgap(30);
		mainPanel.setLayout(bl);

		mainPanel.add(upperHalf, BorderLayout.NORTH);
		mainPanel.add(middleHalf, BorderLayout.CENTER);
		mainPanel.add(lowerHalf, BorderLayout.SOUTH);
		getContentPane().add(mainPanel);
		isInitialized(true);
		pack();
		// setSize(830, 500);

	}

	private void defineUpperHalf() {

		upperHalf = new JPanel();
		upperHalf.setLayout(new BorderLayout());
		defineTopPanel();
		defineMiddlePanel();
		defineLowerPanel();
		upperHalf.add(topPanel, BorderLayout.NORTH);
		upperHalf.add(middlePanel, BorderLayout.CENTER);
		upperHalf.add(lowerPanel, BorderLayout.SOUTH);

	}

	private void defineMiddleHalf() {
		middleHalf = new JPanel();
		middleHalf.setLayout(new BorderLayout());
		JSeparator s = new JSeparator();
		s.setOrientation(SwingConstants.HORIZONTAL);
		middleHalf.add(s, BorderLayout.SOUTH);

	}

	private void defineLowerHalf() {

		lowerHalf = new JPanel();
		lowerHalf.setLayout(new FlowLayout(FlowLayout.LEFT));

		JButton backButton = new JButton("<= Back to Main");
		addBackButtonListener(backButton);
		lowerHalf.add(backButton);

	}

	private void defineTopPanel() {
		topPanel = new JPanel();
		JPanel intPanel = new JPanel(new BorderLayout());
		intPanel.add(Box.createRigidArea(new Dimension(0, 20)), BorderLayout.NORTH);
		JLabel loginLabel = new JLabel("Add a book");
		Util.adjustLabelFont(loginLabel, Color.BLUE.darker(), true);
		intPanel.add(loginLabel, BorderLayout.CENTER);
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		topPanel.add(intPanel);

	}

	private void defineMiddlePanel() {
		middlePanel = new JPanel();
		middlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		defineLeftTextPanel();
		// defineRightTextPanel();
		middlePanel.add(leftTextPanel);
		// middlePanel.add(rightTextPanel);
	}

	private void defineLowerPanel() {
		lowerPanel = new JPanel();
//		labelBook = new JLabel("");
//		lowerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
		addBookButton = new JButton("AddBook");
		lowerPanel.add(addBookButton);
		addBookButtonListener(addBookButton);
		// addBookCopytButton.setEnabled(false);
	}

	private void defineLeftTextPanel() {
		JPanel topText1 = new JPanel();
		JPanel topText2 = new JPanel();
		JPanel topText3 = new JPanel();
		topText1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
		topText2.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
		topText3.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

		isbn = new JTextField(10);
		JLabel label = new JLabel("ISBN :          ");
		topText1.add(label);
		topText1.add(isbn);
		JLabel label2 = new JLabel("title :             ");
		title = new JTextField(10);
		topText1.add(label2);
		topText1.add(title);
		JLabel label3 = new JLabel("maxCheckoutLength : ");
		maxCheckoutLength = new JTextField(10);
		topText1.add(label3);
		topText1.add(maxCheckoutLength);

		JLabel label4 = new JLabel("firstName : ");
		firstName = new JTextField(10);
		topText2.add(label4);
		topText2.add(firstName);
		lastName = new JTextField(10);
		JLabel labe5 = new JLabel("lastName : ");
		topText2.add(labe5);
		topText2.add(lastName);
		JLabel labe6 = new JLabel("telephone :                      ");
		telephone = new JTextField(10);
		topText2.add(labe6);
		topText2.add(telephone);
		JLabel labe7 = new JLabel("bio : ");
		bio = new JTextField(10);
		topText2.add(labe7);
		topText2.add(bio);

		JLabel labe8 = new JLabel("street :        ");
		street = new JTextField(10);
		topText3.add(labe8);
		topText3.add(street);
		city = new JTextField(10);
		JLabel labe9 = new JLabel("city :             ");
		topText3.add(labe9);
		topText3.add(city);
		JLabel labe10 = new JLabel("state :                               ");
		state = new JTextField(10);
		topText3.add(labe10);
		topText3.add(state);
		JLabel labe11 = new JLabel("zip : ");
		zip = new JTextField(10);
		topText3.add(labe11);
		topText3.add(zip);

		leftTextPanel = new JPanel();
		leftTextPanel.setLayout(new BorderLayout());
		leftTextPanel.add(topText1, BorderLayout.NORTH);
		leftTextPanel.add(topText2, BorderLayout.CENTER);
		leftTextPanel.add(topText3, BorderLayout.SOUTH);
	}

	private void addBackButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);

		});
	}

	private void addBookButtonListener(JButton butn) {
		
		butn.addActionListener(evt -> {
			String isbnNum = isbn.getText().trim();
			String strTitle = title.getText().trim();
			String strMaxCheckoutLength = maxCheckoutLength.getText().trim();
			String strFirstName = firstName.getText().trim();
			String strLastName = lastName.getText().trim();
			String strTelephone = telephone.getText().trim();
			String strBio = bio.getText().trim();
			String strStreet = street.getText().trim();
			String strCity = city.getText().trim();
			String strState = state.getText().trim();
			String strZip = zip.getText().trim();
			
			String bookInfo = LibrarySystem.INSTANCE.ci.searchBookByIsbn(isbnNum);
			if(!bookInfo.isEmpty()) {
				JOptionPane.showMessageDialog(this, "This isbn already exists. Can not add.");
				return;
			}else if(isbnNum.isEmpty()||strTitle.isEmpty()||strMaxCheckoutLength.isEmpty()||strFirstName.isEmpty()||strLastName.isEmpty()||strTelephone.isEmpty()||
					strBio.isEmpty()||strBio.isEmpty()||strStreet.isEmpty()||strCity.isEmpty()||strState.isEmpty()||strZip.isEmpty()) {
				JOptionPane.showMessageDialog(this, "input value can not be empty.");
				return;
			}else if(!LibrarySystem.INSTANCE.ci.isNumeric(strMaxCheckoutLength)) {
				JOptionPane.showMessageDialog(this, "strMaxCheckoutLength must be numeric.");
				return;
			}
			
			String[] strFirstNames = strFirstName.split(",");
			String[] strLastNames = strLastName.split(",");
			String[] strTelephones = strTelephone.split(",");
			String[] strBios = strBio.split(",");
			String[] strStreets = strStreet.split(",");
			String[] strCitys = strCity.split(",");
			String[] strStates = strState.split(",");
			String[] strZips = strZip.split(",");
			if(strFirstNames.length != strLastNames.length || 
					strFirstNames.length != strTelephones.length || 
					strFirstNames.length != strBios.length || 
					strFirstNames.length != strStreets.length || 
					strFirstNames.length != strCitys.length || 
					strFirstNames.length != strStates.length || 
					strFirstNames.length != strZips.length ) {
				JOptionPane.showMessageDialog(this, "the count of author is different.");
				return;
			}
			boolean rs = LibrarySystem.INSTANCE.ci.addBook(isbnNum, strTitle, strMaxCheckoutLength, strFirstName, strLastName, strTelephone, strBio, strStreet, strCity, strState, strZip);
			if(rs) {
				JOptionPane.showMessageDialog(this, "A Book was added successfully.");
			}else {
				JOptionPane.showMessageDialog(this, "A Book was added fail.");
			}
			
		});
	}

}
