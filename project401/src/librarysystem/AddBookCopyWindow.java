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
 * AddBookCopyWindow
 * @author TaoWu
 *
 */
public class AddBookCopyWindow extends JFrame implements LibWindow {
	public static final AddBookCopyWindow INSTANCE = new AddBookCopyWindow();

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

	private JTextField isbn;
	private JLabel labelBook;
	private JButton searchButton;
	private JButton addBookCopyButton;
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
	private AddBookCopyWindow() {
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
		setSize(660, 500);

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
		JLabel label = new JLabel("Add a bookCopy");
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
		addBookCopyButton.setEnabled(false);
	}

	private void defineLeftTextPanel() {

		JPanel topText = new JPanel();
		JPanel bottomText = new JPanel();
		topText.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
		bottomText.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

		isbn = new JTextField(10);
		JLabel label = new JLabel("ISBN : ");
		topText.add(label);
		topText.add(isbn);

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

		addBookCopyButton = new JButton("AddBookCopy");
		topText.add(searchButton);
		topText.add(addBookCopyButton);

		rightTextPanel = new JPanel();
		rightTextPanel.setLayout(new BorderLayout());
		rightTextPanel.add(topText, BorderLayout.NORTH);

		searchButtonListener(searchButton);
		addBookCopyButtonListener(addBookCopyButton);
	}

	private void addBackButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);
			isbn.setText("");
			labelBook.setText("");
			addBookCopyButton.setEnabled(false);

		});
	}

	private void searchButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			String isbnNum = isbn.getText().trim();
			String bookInfo = LibrarySystem.INSTANCE.ci.searchBookByIsbn(isbnNum);
			labelBook.setText(bookInfo);
			if ("".equals(labelBook.getText())) {
				addBookCopyButton.setEnabled(false);
				JOptionPane.showMessageDialog(this, "isbnNum : "+isbnNum+" not be found.");
			} else {
				addBookCopyButton.setEnabled(true);
			}
		});
	}

	private void addBookCopyButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			String isbnNum = isbn.getText().trim();
			boolean rs = LibrarySystem.INSTANCE.ci.addBookCopyByIsbn(isbnNum);
			if(rs) {
				JOptionPane.showMessageDialog(this, "A BookCopy was added successfully.");
			}else {
				JOptionPane.showMessageDialog(this, "A BookCopy was added fail.");
			}
		});
	}

}
