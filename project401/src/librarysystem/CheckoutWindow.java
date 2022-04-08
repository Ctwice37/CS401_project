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
	public void init(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 461, 225);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		setTitle("Checkout");
		getContentPane().setLayout(null);
		contentPane.setLayout(null);
		
		idTextField = new JTextField();
		idTextField.setBounds(212, 31, 86, 20);
		getContentPane().add(idTextField);
		idTextField.setColumns(10);
		
		isbnTextField = new JTextField();
		isbnTextField.setBounds(212, 62, 86, 20);
		getContentPane().add(isbnTextField);
		isbnTextField.setColumns(10);
		
		copyTextField = new JTextField();
		copyTextField.setBounds(212, 93, 86, 20);
		getContentPane().add(copyTextField);
		copyTextField.setColumns(10);
		
		JLabel memberID = new JLabel("Member ID");
		memberID.setHorizontalAlignment(SwingConstants.TRAILING);
		memberID.setBounds(114, 34, 88, 14);
		getContentPane().add(memberID);
		
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String id = idTextField.getText().trim();
				String isbn = isbnTextField.getText().trim();
				String copynum = copyTextField.getText().trim();
				
				if(id.isEmpty() || isbn.isEmpty()) {
					JOptionPane.showMessageDialog(contentPane,"Fields must be nonempty");
				}
				HashMap<String, LibraryMember> members = LibrarySystem.DATA.readMemberMap();
//				System.out.println(members.toString()); // for testing purposes
				HashMap<String, Book> books = LibrarySystem.DATA.readBooksMap();
				System.out.println(books.toString()); //for testing purposes
				
				if(!members.containsKey(id)) {
					JOptionPane.showMessageDialog(contentPane,"Member ID not found");
					CheckoutWindow.INSTANCE.clearTextFields();
				}
				if(!books.containsKey(isbn)) {
					JOptionPane.showMessageDialog(contentPane,"ISBN not found");
					CheckoutWindow.INSTANCE.clearTextFields();
				}
				else {
				Book checkoutBook = books.get(isbn);
				BookCopy copy;
					if(copynum.isEmpty()) {
						copy = checkoutBook.getNextAvailableCopy();
					}else {
						copy = checkoutBook.getCopy(Integer.parseInt(copynum));
					}
				
				LibrarySystem.DATA.saveCheckoutRecord(id, copy);
					
				}
				
			}
		});
		
		submitButton.setBounds(209, 124, 89, 23);
		getContentPane().add(submitButton);
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	    			LibrarySystem.hideAllWindows();
	    			LibrarySystem.INSTANCE.setVisible(true);		
				}		
			});
		backButton.setBounds(114, 124, 89, 23);
		getContentPane().add(backButton);
		
		JLabel copyLabel = new JLabel("Copy Number");
		copyLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		copyLabel.setBounds(114, 65, 88, 14);
		contentPane.add(copyLabel);
		
		JLabel isbnLabel = new JLabel("ISBN");
		isbnLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		isbnLabel.setBounds(114, 96, 88, 14);
		contentPane.add(isbnLabel);
		
		isInitialized(true);
	}
}
