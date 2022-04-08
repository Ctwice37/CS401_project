package librarysystem;


import java.awt.Component;
import java.awt.Dimension;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import business.Address;
import business.LibraryMember;


import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class AddANewMemberWindow extends JFrame  implements LibWindow {

	private static final long serialVersionUID = 1L;
	private boolean isInitialized = false;
	public static final AddANewMemberWindow INSTANCE = new AddANewMemberWindow();



	private AddANewMemberWindow() {}



	private JPanel mainpanel;
	private JTextField idTextField;
	private JTextField FirstNameTextField;
	private JTextField LastNameTextField;
	private JTextField streetTextField;
	private JTextField cityTextField;
	private JTextField stateTextField;
	private JTextField zipTextField;
	private JTextField phoneNumberTextField;
	private JButton btnNewButton;



	public void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 554, 431);
		setMinimumSize(new Dimension(450, 450));
		mainpanel = new JPanel();
		mainpanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainpanel.setLayout(null);

		JButton btnAddMemberButton = new JButton("Add Member");
		btnAddMemberButton.setBounds(184, 297, 117, 29);
		mainpanel.add(btnAddMemberButton);
		btnAddMemberButton.addActionListener(new addMemberListener());


		idTextField = new JTextField();
		idTextField.setBounds(129, 15, 130, 26);
		mainpanel.add(idTextField);
		idTextField.setColumns(10);

		JLabel lblMemberIdLabel = new JLabel("Member ID");
		lblMemberIdLabel.setBounds(28, 20, 102, 16);
		mainpanel.add(lblMemberIdLabel);

		FirstNameTextField = new JTextField();
		FirstNameTextField.setBounds(129, 53, 130, 26);
		mainpanel.add(FirstNameTextField);
		FirstNameTextField.setColumns(10);

		JLabel lblFistNameLabel = new JLabel("First Name");
		lblFistNameLabel.setBounds(28, 58, 74, 16);
		mainpanel.add(lblFistNameLabel);

		JLabel lblLastNameLabel = new JLabel("Last Name");
		lblLastNameLabel.setBounds(28, 98, 74, 16);
		mainpanel.add(lblLastNameLabel);

		LastNameTextField = new JTextField();
		LastNameTextField.setBounds(129, 93, 130, 26);
		mainpanel.add(LastNameTextField);
		LastNameTextField.setColumns(10);

		JLabel lblStreetLabel = new JLabel("Street");
		lblStreetLabel.setBounds(28, 141, 61, 16);
		mainpanel.add(lblStreetLabel);

		streetTextField = new JTextField();
		streetTextField.setBounds(81, 136, 130, 26);
		mainpanel.add(streetTextField);
		streetTextField.setColumns(10);

		JLabel lblCityLabel = new JLabel("City");
		lblCityLabel.setBounds(239, 141, 38, 16);
		mainpanel.add(lblCityLabel);

		cityTextField = new JTextField();
		cityTextField.setBounds(280, 136, 130, 26);
		mainpanel.add(cityTextField);
		cityTextField.setColumns(10);

		JLabel lblStateLabel = new JLabel("State");
		lblStateLabel.setBounds(28, 180, 48, 16);
		mainpanel.add(lblStateLabel);

		stateTextField = new JTextField();
		stateTextField.setBounds(81, 175, 74, 26);
		mainpanel.add(stateTextField);
		stateTextField.setColumns(10);

		JLabel lblZipLabel = new JLabel("Zip");
		lblZipLabel.setBounds(198, 185, 38, 16);
		mainpanel.add(lblZipLabel);

		zipTextField = new JTextField();
		zipTextField.setBounds(249, 175, 130, 26);
		mainpanel.add(zipTextField);
		zipTextField.setColumns(10);

		JLabel lblPhoneNumberLabel = new JLabel("Phone Number");
		lblPhoneNumberLabel.setBounds(28, 218, 92, 16);
		mainpanel.add(lblPhoneNumberLabel);

		phoneNumberTextField = new JTextField();
		phoneNumberTextField.setBounds(129, 213, 130, 26);
		mainpanel.add(phoneNumberTextField);
		phoneNumberTextField.setColumns(10);

		btnNewButton = new JButton("<-  Back to Main");
		btnNewButton.setBounds(13, 368, 136, 29);
		mainpanel.add(btnNewButton);
		btnNewButton.addActionListener(new BackToMainListener());
		getContentPane().add(mainpanel);

		pack();
		isInitialized(true);
		centerFrameOnDesktop(this);
		

	}

	public static void centerFrameOnDesktop(Component f) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int height = toolkit.getScreenSize().height;
		int width = toolkit.getScreenSize().width;
		int frameHeight = f.getSize().height;
		int frameWidth = f.getSize().width;
		f.setLocation(((width - frameWidth) / 2), (height - frameHeight) / 3);
	}

	@Override
	public boolean isInitialized() {

		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		isInitialized=val;

	}

	class BackToMainListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent evt) {
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);
    		
		}
	}
	

	class addMemberListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent evt) {
			String id= idTextField.getText().trim();
			String street= streetTextField.getText().trim();
			String firstName= FirstNameTextField.getText().trim();
			String lastName= LastNameTextField.getText().trim();
			String city= cityTextField.getText().trim();
			String state= stateTextField.getText().trim();
			String zip= zipTextField.getText().trim();
			String phoneNumber= phoneNumberTextField.getText().trim();

			if(id.isEmpty() || firstName.isEmpty() || lastName.isEmpty()||
					street.isEmpty() || city.isEmpty() || state.isEmpty() ||
					zip.isEmpty() || phoneNumber.isEmpty()) {

				JOptionPane.showMessageDialog(mainpanel,"Fields must be nonempty");


			}
			else {	LibrarySystem.DATA.saveNewMember(new LibraryMember(id,firstName, lastName, phoneNumber, new Address(street, city, state, zip)));

			JOptionPane.showMessageDialog(mainpanel,"Member Added");

			}


		}
		


	}
}
