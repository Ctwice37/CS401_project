package librarysystem;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import business.ControllerInterface;
import business.SystemController;
import dataaccess.Auth;
import librarysystem.LibrarySystem.AddBookCopyListener;
import librarysystem.LibrarySystem.AddBookListener;
import librarysystem.LibrarySystem.PrintCheckedRecordListener;

/*
 * Functionality added 4/6 @rstephens:
 * added Librarian and Admin menus to menu bar(which will contain new member, checkout, add book, etc functions)
 * 	these will be set visible or invisible based on authentication
 */

public class LibrarySystem  extends JFrame implements LibWindow {


	SystemController ci = new SystemController();
	
	public final static LibrarySystem INSTANCE =new LibrarySystem();
	JPanel mainPanel;
	JMenuBar menuBar;
    JMenu options;
    JMenuItem login, allBookIds, allMemberIds; 
    String pathToImage;
    private boolean isInitialized = false;
    
    //added 4/6 @rstephens:
    JMenu librarianOptions;
    JMenuItem checkout, printCheckoutRecord, checkOverdue;
    JMenu adminOptions;
    JMenuItem newMember, addBookCopy, addBook,printCheckRecord;
    //=======================
    private static LibWindow[] allWindows = { 
    	LibrarySystem.INSTANCE,
		LoginWindow.INSTANCE,
		AllMemberIdsWindow.INSTANCE,	
		AllBookIdsWindow.INSTANCE,
		// TaoWu start
		AddBookCopyWindow.INSTANCE,
		AddBookWindow.INSTANCE,
		PrintCheckedRecordWindow.INSTANCE
		// TaoWu end
	};
    	
	public static void hideAllWindows() {
		
		for(LibWindow frame: allWindows) {
			frame.setVisible(false);
			
		}
	}
    
    
    private LibrarySystem() {}
    
    public void init() {
    	formatContentPane();
    	setPathToImage();
    	insertSplashImage();
		
		createMenus();
		//pack();
		setSize(660,500);
		isInitialized = true;
    }
    
    private void formatContentPane() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1,1));
		getContentPane().add(mainPanel);	
	}
    
    private void setPathToImage() {
    	String currDirectory = System.getProperty("user.dir");
    	pathToImage = currDirectory+"\\src\\librarysystem\\library2.jpg";
    }
    
    private void insertSplashImage() {
        ImageIcon image = new ImageIcon(pathToImage);
		mainPanel.add(new JLabel(image));	
    }
    private void createMenus() {
    	menuBar = new JMenuBar();
		menuBar.setBorder(BorderFactory.createRaisedBevelBorder());
		addMenuItems();
		setJMenuBar(menuBar);		
    }
    
    private void addMenuItems() {
       options = new JMenu("Options");  
 	   menuBar.add(options);
 	   login = new JMenuItem("Login");
 	   login.addActionListener(new LoginListener());
 	   allBookIds = new JMenuItem("All Book Ids");
 	   allBookIds.addActionListener(new AllBookIdsListener());
 	   allMemberIds = new JMenuItem("All Member Ids");
 	   allMemberIds.addActionListener(new AllMemberIdsListener());
 	   // TaoWu start
	   addBookCopy = new JMenuItem("Add a bookCopy");
	   addBookCopy.addActionListener(new AddBookCopyListener());
	   addBook = new JMenuItem("Add a book");
	   addBook.addActionListener(new AddBookListener());
	   printCheckRecord = new JMenuItem("Print the checked record");
	   printCheckRecord.addActionListener(new PrintCheckedRecordListener());
	   addBookCopy.setEnabled(false);
	   addBook.setEnabled(false);
	   printCheckRecord.setEnabled(false);
	   // TaoWu end
 	   options.add(login);
 	   options.add(allBookIds);
 	   options.add(allMemberIds);
 	   // TaoWu start
	   options.add(addBookCopy);
	   options.add(addBook);
	   options.add(printCheckRecord);
	   // TaoWu end
 	//added 4/6 @rstephens:
 	   librarianOptions = new JMenu("Librarian"); 
 	   menuBar.add(librarianOptions);
 	   librarianOptions.setVisible(false);
 	   
 	   adminOptions = new JMenu("Admin");
 	   menuBar.add(adminOptions);
   	   adminOptions.setVisible(false);
   	   
 	 	//visibility set by login credentials
	 	   if(SystemController.currentAuth == Auth.LIBRARIAN) {
	 		  librarianOptions.setVisible(true);
	 		  LibrarySystem.INSTANCE.repaint();
	 	   }
	 	  if(SystemController.currentAuth == Auth.ADMIN) {
	 		  adminOptions.setVisible(true);
	 		  LibrarySystem.INSTANCE.repaint();
	 	  }
	 	 if(SystemController.currentAuth == Auth.BOTH) {
	 		librarianOptions.setVisible(true);
	 		 adminOptions.setVisible(true);
	 		  LibrarySystem.INSTANCE.repaint();
	 	 }	 	   

 	   //TODO set menu items
 	   //newMember, addBookCopy, addBook;
 	   //checkout, printCheckoutRecord, checkOverdue;
	 //=====================   
    }
    
    class LoginListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			LoginWindow.INSTANCE.init();
			Util.centerFrameOnDesktop(LoginWindow.INSTANCE);
			LoginWindow.INSTANCE.setVisible(true);
			
		}
    	
    }
    class AllBookIdsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AllBookIdsWindow.INSTANCE.init();
			
			List<String> ids = ci.allBookIds();
			Collections.sort(ids);
			StringBuilder sb = new StringBuilder();
			for(String s: ids) {
				sb.append(s + "\n");
			}
			System.out.println(sb.toString());
			AllBookIdsWindow.INSTANCE.setData(sb.toString());
			AllBookIdsWindow.INSTANCE.pack();
			//AllBookIdsWindow.INSTANCE.setSize(660,500);
			Util.centerFrameOnDesktop(AllBookIdsWindow.INSTANCE);
			AllBookIdsWindow.INSTANCE.setVisible(true);
			
		}
    	
    }
    
    class AllMemberIdsListener implements ActionListener {

    	@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AllMemberIdsWindow.INSTANCE.init();
			AllMemberIdsWindow.INSTANCE.pack();
			AllMemberIdsWindow.INSTANCE.setVisible(true);
			
			
			LibrarySystem.hideAllWindows();
			AllBookIdsWindow.INSTANCE.init();
			
			List<String> ids = ci.allMemberIds();
			Collections.sort(ids);
			StringBuilder sb = new StringBuilder();
			for(String s: ids) {
				sb.append(s + "\n");
			}
			System.out.println(sb.toString());
			AllMemberIdsWindow.INSTANCE.setData(sb.toString());
			AllMemberIdsWindow.INSTANCE.pack();
			//AllMemberIdsWindow.INSTANCE.setSize(660,500);
			Util.centerFrameOnDesktop(AllMemberIdsWindow.INSTANCE);
			AllMemberIdsWindow.INSTANCE.setVisible(true);
			
			
		}
    	
    }
    // TaoWu start
 	class AddBookCopyListener implements ActionListener {

 		@Override
 		public void actionPerformed(ActionEvent e) {
 			LibrarySystem.hideAllWindows();
 			AddBookCopyWindow.INSTANCE.init();
 			Util.centerFrameOnDesktop(AddBookCopyWindow.INSTANCE);
 			AddBookCopyWindow.INSTANCE.setVisible(true);
 		}
 	}

 	class AddBookListener implements ActionListener {

 		@Override
 		public void actionPerformed(ActionEvent e) {
 			LibrarySystem.hideAllWindows();
 			AddBookWindow.INSTANCE.init();
 			Util.centerFrameOnDesktop(AddBookWindow.INSTANCE);
 			AddBookWindow.INSTANCE.setVisible(true);
 		}
 	}

 	class PrintCheckedRecordListener implements ActionListener {

 		@Override
 		public void actionPerformed(ActionEvent e) {
 			LibrarySystem.hideAllWindows();
 			PrintCheckedRecordWindow.INSTANCE.init();
 			Util.centerFrameOnDesktop(PrintCheckedRecordWindow.INSTANCE);
 			PrintCheckedRecordWindow.INSTANCE.setVisible(true);
 		}
 	}
 	// TaoWu end

	@Override
	public boolean isInitialized() {
		return isInitialized;
	}


	@Override
	public void isInitialized(boolean val) {
		isInitialized =val;
		
	}
    
}
