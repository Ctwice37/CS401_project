package librarysystem;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import business.Book;
import business.BookCopy;
import business.LibraryMember;
import dataaccess.DataAccessFacade;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.awt.event.ActionEvent;

public class CheckoutWindow extends JFrame implements LibWindow{
	
	/**
	 * @rstephens added 4/7 am
	 */
	private static final long serialVersionUID = 1L;

	public static final CheckoutWindow INSTANCE = new CheckoutWindow();
	
	private boolean isInitialized = false;
	
	private JPanel contentPane;
	private JTextField idTextField;
	private JTextField isbnTextField;
	private JTextField copyTextField;
	
	private boolean isAvailable = false;
	private boolean checked = false;
	
	@Override
	public boolean isInitialized() {
		return isInitialized;
	}
	
	@Override
	public void isInitialized(boolean val) {
		isInitialized = val;
	}
	
	private CheckoutWindow() {
	
	}
	
	private void clearTextFields() {
		idTextField.setText("");
		isbnTextField.setText("");
		copyTextField.setText("");
	}

	@Override
	public void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 461, 225);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		setTitle("Checkout");
		getContentPane().setLayout(null);
		contentPane.setLayout(null);

		idTextField = new JTextField();
		idTextField.setBounds(154, 31, 144, 20);
		getContentPane().add(idTextField);
		idTextField.setColumns(10);

		isbnTextField = new JTextField();
		isbnTextField.setBounds(154, 62, 144, 20);
		getContentPane().add(isbnTextField);
		isbnTextField.setColumns(10);

		copyTextField = new JTextField();
		copyTextField.setBounds(154, 93, 144, 20);
		getContentPane().add(copyTextField);
		copyTextField.setColumns(10);

		JLabel memberID = new JLabel("Member ID");
		memberID.setHorizontalAlignment(SwingConstants.TRAILING);
		memberID.setBounds(47, 34, 88, 14);
		getContentPane().add(memberID);

		JButton checkAvailabilityButton = new JButton("\u2714");
		checkAvailabilityButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String id = idTextField.getText().trim();
				String isbn = isbnTextField.getText().trim();
				String copynum = copyTextField.getText().trim();
				HashMap<String, LibraryMember> members = LibrarySystem.DATA.readMemberMap();
				HashMap<String, Book> books = LibrarySystem.DATA.readBooksMap();
				Book b = books.get(isbn);
				System.out.println(b.toString());
				if (id.isEmpty() || isbn.isEmpty()) {
					JOptionPane.showMessageDialog(contentPane, "Fields must be nonempty");
				}
				if (!members.containsKey(id)) {
					JOptionPane.showMessageDialog(contentPane, "Member ID not found");
				}
				if (!books.containsKey(isbn)) {
					JOptionPane.showMessageDialog(contentPane, "ISBN not found");
				}
				else if(!b.isAvailable()){
					JOptionPane.showMessageDialog(contentPane, "No Available Copies");
				}
				else {	
					JOptionPane.showMessageDialog(contentPane, "Checkout Available");
				checked = true;
				isAvailable = true;
				}
			}
		});

		checkAvailabilityButton.setBounds(308, 62, 89, 58);
		contentPane.add(checkAvailabilityButton);

		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String id = idTextField.getText().trim();
				String isbn = isbnTextField.getText().trim();
				String copynum = copyTextField.getText().trim();
				if (checked) {

					if (isAvailable == false) {
						JOptionPane.showMessageDialog(contentPane, "Not Available.");
					} else {
						HashMap<String, Book> books = LibrarySystem.DATA.readBooksMap();
						Book checkoutBook = books.get(isbn);
						BookCopy copy;
						if (copynum.isEmpty()) {
							copy = checkoutBook.getNextAvailableCopy();
						} else {
							copy = checkoutBook.getCopy(Integer.parseInt(copynum));
						}
						if (copy != null) {
							LibrarySystem.DATA.saveCheckoutRecord(id, copy);
							JOptionPane.showMessageDialog(contentPane, "Checkout added to Records");
							CheckoutWindow.INSTANCE.clearTextFields();
						} else {
							System.out.println("Error! Record not saved!"); // testing
						}
					}

				}
				// if not checked
				else {
					JOptionPane.showMessageDialog(contentPane, "Please Check Availability");
				}
			}
		});

		submitButton.setBounds(57, 124, 89, 23);
		getContentPane().add(submitButton);

		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LibrarySystem.hideAllWindows();
				LibrarySystem.INSTANCE.setVisible(true);
			}
		});
		backButton.setBounds(164, 124, 89, 23);
		getContentPane().add(backButton);

		JLabel copyLabel = new JLabel("Copy Number");
		copyLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		copyLabel.setBounds(56, 96, 88, 14);
		contentPane.add(copyLabel);

		JLabel isbnLabel = new JLabel("ISBN");
		isbnLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		isbnLabel.setBounds(56, 65, 88, 14);
		contentPane.add(isbnLabel);

		JLabel lblNewLabel = new JLabel("Check Availability");
		lblNewLabel.setBounds(308, 34, 89, 14);
		contentPane.add(lblNewLabel);

		isInitialized(true);
	}

}
