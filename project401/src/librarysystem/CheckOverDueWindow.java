package librarysystem;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import business.CheckoutRecord;
import business.CheckoutRecordEntry;
import business.LibraryMember;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.awt.event.ActionEvent;

public class CheckOverDueWindow extends JFrame implements LibWindow{


	private static final long serialVersionUID = 1L;

	public static final CheckOverDueWindow INSTANCE = new CheckOverDueWindow();


	private CheckOverDueWindow() {}



	private boolean isInitialized = false;

	private JPanel contentPane;
	private JTextField textField;
	private JTextPane textPane;


	public boolean isInitialized() {
		return isInitialized;
	}
	public void isInitialized(boolean val) {
		isInitialized = val;
	}


	@Override
	public void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(216, 11, 195, 211);
		contentPane.add(textPane);

		JLabel lblNewLabel = new JLabel("MemberID");
		lblNewLabel.setBounds(10, 34, 91, 26);
		contentPane.add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(67, 37, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton btnSearchUserID = new JButton("Search");
		btnSearchUserID.setBounds(64, 71, 89, 23);
		contentPane.add(btnSearchUserID);
		btnSearchUserID.addActionListener(new SeachActionListener());


		JButton btnBackButton = new JButton("< == Back");
		btnBackButton.setBounds(29, 199, 89, 23);
		contentPane.add(btnBackButton);
		btnBackButton.addActionListener(new BackToMainListener());

	}





	private String generateCheckout(String id) {
		
		StringBuilder sb = new StringBuilder();
		CheckoutRecord  record =  LibrarySystem.DATA.readMemberMap().get(id).getCheckoutRecord();
		if(record == null) {
			JOptionPane.showMessageDialog(INSTANCE, "Member Has No Record");
		}else {
		for(CheckoutRecordEntry cr : record.getCheckoutRecordEntries()) {
			boolean overdue = (cr.getDueDate().isBefore(LocalDate.now()));
			sb.append("Book Title: \"" + cr.getBookCopy().getBook().getTitle() + "\"\n");
			sb.append("CheckoutDate: " + cr.getCheckoutDate() + "\n");
			sb.append("Check is OverDue: " + overdue + "\n");
			sb.append("-----------------------------------------------");
		
		}
		
		
	}
		return sb.toString();
	}



	class BackToMainListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent evt) {
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);

		}

	}


	//use a JTable
	class SeachActionListener implements ActionListener{

		StringBuilder sb = new StringBuilder();
		@Override
		public void actionPerformed(ActionEvent e) {
			String id = textField.getText().trim();
			LibraryMember cust = LibrarySystem.DATA.readMemberMap().get(textField.getText().trim());
			textField.setText("");
			
			if(cust == null) {
				JOptionPane.showMessageDialog(INSTANCE, "INVALID MEMBER ID");
				
			}else {
				sb.append("******************************\n");
				sb.append(cust.getFirstName() + " " + cust.getLastName() + "\n");
				sb.append("******************************\n");
				sb.append(generateCheckout(id));
				
			textPane.setText(sb.toString());
				
				
				
			}
			


		}

		

	}
}

