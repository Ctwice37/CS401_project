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

import business.LibraryMember;
/**
 * PrintCheckedRecordWindow
 * @author TaoWu
 *
 */
public class PrintCheckedRecordWindow extends JFrame implements LibWindow {
	public static final PrintCheckedRecordWindow INSTANCE = new PrintCheckedRecordWindow();

	private boolean isInitialized = false;

	private JPanel mainPanel;
	private JPanel upperHalf;
	private JPanel middleHalf;
	private JPanel lowerHalf;

	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;
	private JPanel leftTextPanel;
	private JPanel rightTextPanel;

	private JTextField memberId;
	private JLabel labelBook;
	private JButton searchButton;
	private JButton printCheckedRecordButton;
	private List<LibraryMember> allMembers;

	public List<LibraryMember> getAllMembers() {
		return allMembers;
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
	private PrintCheckedRecordWindow() {
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
		setSize(1100, 500);

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
		JLabel label = new JLabel("Print the checkout record");
		Util.adjustLabelFont(label, Color.BLUE.darker(), true);
		intPanel.add(label, BorderLayout.CENTER);
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		topPanel.add(intPanel);

	}

	private void defineMiddlePanel() {
		middlePanel = new JPanel();
		middlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		defineLeftTextPanel();
		defineRightTextPanel();
		middlePanel.add(leftTextPanel);
		middlePanel.add(rightTextPanel);
	}

	private void defineLowerPanel() {
		lowerPanel = new JPanel();
		labelBook = new JLabel("");
		lowerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
		lowerPanel.add(labelBook);
		printCheckedRecordButton.setEnabled(false);
	}

	private void defineLeftTextPanel() {

		JPanel topText = new JPanel();
		JPanel bottomText = new JPanel();
		topText.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
		bottomText.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

		memberId = new JTextField(10);
		JLabel label = new JLabel("memberID : ");
		topText.add(label);
		topText.add(memberId);

		leftTextPanel = new JPanel();
		leftTextPanel.setLayout(new BorderLayout());
		leftTextPanel.add(topText, BorderLayout.NORTH);
	}

	private void defineRightTextPanel() {
		searchButton = new JButton("Search");
		JPanel topText = new JPanel();
		JPanel bottomText = new JPanel();
		topText.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
		bottomText.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

		printCheckedRecordButton = new JButton("PrintCheckedRecord");
		topText.add(searchButton);
		topText.add(printCheckedRecordButton);

		rightTextPanel = new JPanel();
		rightTextPanel.setLayout(new BorderLayout());
		rightTextPanel.add(topText, BorderLayout.NORTH);

		searchButtonListener(searchButton);
		printCheckedRecordButtonListener(printCheckedRecordButton);
	}

	private void addBackButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);
			memberId.setText("");
			labelBook.setText("");
			printCheckedRecordButton.setEnabled(false);

		});
	}

	private void searchButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			String strMemberId = memberId.getText().trim();
			String libraryMemberInfo = LibrarySystem.INSTANCE.ci.searchLibraryMemberById(strMemberId);
			labelBook.setText(libraryMemberInfo);
			if ("".equals(libraryMemberInfo)) {
				printCheckedRecordButton.setEnabled(false);
				JOptionPane.showMessageDialog(this, "memberId : "+strMemberId+" not be found.");
			} else {
				printCheckedRecordButton.setEnabled(true);
			}
		});
	}

	private void printCheckedRecordButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			String strMemberId = memberId.getText().trim();
			String libraryMemberInfo = LibrarySystem.INSTANCE.ci.printCheckedRecordToConsole(strMemberId);
			if ("".equals(libraryMemberInfo)) {
				JOptionPane.showMessageDialog(this, "memberId : "+strMemberId+" not be found.");
			} else {
				System.out.println(libraryMemberInfo);
				JOptionPane.showMessageDialog(this, "the information of memberId : "+strMemberId+" be print sucessfully.");
			}
		});
	}

}
