package review.utils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.osgi.service.prefs.BackingStoreException;











import com.mysql.jdbc.Statement;

import review.model.CodeFile;
import review.model.CodeReview;
import review.model.Database;
import review.model.Project;
import review.model.Registry;
import review.preferences.basicPreferences;
import review.views.CodeFileDetails;
import review.views.FileList;

public class Common {
	public static Database db;
	
	public static String readFile(String path) throws IOException {
		  FileInputStream stream = new FileInputStream(new File(path));
		  try {
		    FileChannel fc = stream.getChannel();
		    MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
		    /* Instead of using default, pass in a decoder. */
		    return Charset.defaultCharset().decode(bb).toString();
		  }
		  finally {
		    stream.close();
		  }
		}
	
	public static String checkDB() {
		String dbName = "vbarstad_cr";
		String dbUserName = "vbarstad_user";
		String dbPassword = "webuser";
		String connectionString = "jdbc:mysql://mysql1000.mochahost.com:3306/" + dbName + "?user=" + dbUserName + "&password=" + dbPassword + "&useUnicode=true&characterEncoding=UTF-8";
		//String connectionString = "jdbc:mysql://mysql1000.mochahost.com/vbarstad_cr?user=vbarstad_user&password=webuser";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			Connection conn = DriverManager.getConnection(connectionString);
			String query = "SELECT * FROM user";

			
			java.sql.Statement st = conn.createStatement();

			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery(query);
			String fromDB = " ! ";
			while (rs.next())
			{
			  int id = rs.getInt("id");
			  String firstName = rs.getString("fname") + " " + rs.getString("lname");
			  fromDB = fromDB + " " + firstName + " ";
			}
			return  fromDB;

		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
		
	}
	
	public static ITextSelection getCurrentSelection()
	{
	   IEditorPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().
	   getActivePage().getActiveEditor();
		if (part instanceof ITextEditor)
		{
		final ITextEditor editor = (ITextEditor) part;
		IDocumentProvider prov = editor.getDocumentProvider();
		IDocument doc = prov.getDocument(editor.getEditorInput());
		ISelection sel = editor.getSelectionProvider().getSelection();
		if (sel instanceof TextSelection)
			{
			ITextSelection textSel = (ITextSelection) sel;
				return textSel;
			}
		}
	return null;
	}
	
	   
	
	
	public static String getUser() {
		//todo get loged on user, if not anyone logged on ... trigger logon dialog
		 db = new Database();
		 String user = basicPreferences.currentUser;
		 while (user.isEmpty() )
		 {
			  setCurrentUser();
			  user = basicPreferences.currentUser;
		 }
		 
	
	     return user;
	}
	
	
	public static String getCodeReviewPath() {
	     
	    
		 String CodeReviewData = System.getenv("CODE_REVIEW_DATA");
	        
	       if (CodeReviewData == null ) {
	    	   MessageDialog.openError(null, "Error",
				          "Environmentvariable CODE_REVIEW_DATA must be defined. This variable must contain the path to the folder where the shared data files is stored! (If the variable is created and not found, you may need to restart eclipse)");

			  return null;
		} else {
			return CodeReviewData;
			
		}
		
	}
	@SuppressWarnings("unchecked")
	public static Boolean setCurrentUser()
	{
		String currentUser="";
		boolean passwordIsRight=false;
		basicPreferences preferences = new basicPreferences();
		preferences.LoadPreferances();

		JPanel myPanel = new JPanel(new BorderLayout());

//		JTextArea textArea = new JTextArea();
//		textArea.setPreferredSize(new Dimension(150, 300));

		JPanel userJPanel = new JPanel();
		JLabel userJLabel = new JLabel("User:");
		JComboBox userField=new JComboBox();
		try {
			userField = new JComboBox(preferences.ReadPreferences());
		} catch (BackingStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Dimension dm = userField.getPreferredSize();
		userField.setPreferredSize(new Dimension(150, dm.height));
		userJPanel.add(userJLabel);
		userJPanel.add(userField);

		JPanel passwordJPanel = new JPanel();
		JLabel passwordJLabel = new JLabel("Password:");
		JPasswordField passwordField = new JPasswordField(15);
		passwordJPanel.add(passwordJLabel);
		passwordJPanel.add(passwordField);

//		myPanel.add(textArea, BorderLayout.NORTH);
		int result;
		if (currentUser.isEmpty()) {
			myPanel.add(userJPanel, BorderLayout.CENTER);
			myPanel.add(passwordJPanel, BorderLayout.SOUTH);
		} else {

		}
		result = JOptionPane.showConfirmDialog(null, myPanel, "Log in"
				+ currentUser, JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			if (basicPreferences.currentUser.isEmpty()) {
				if (passwordField.getPassword() != null) {
					String inputPassword = String.copyValueOf(passwordField
							.getPassword());
					String realPassword = preferences.GetPassword(userField
							.getSelectedItem().toString());
					passwordIsRight = inputPassword.equals(realPassword);
					if (passwordIsRight) {
						passwordIsRight = true;
						basicPreferences.currentUser = userField.getSelectedItem().toString();
					}
				}

			}
			if (passwordIsRight) {
				return true;

			} else {
				MessageDialog.openInformation(null, "Wrong password",
						"Password doesn't match "
								+ userField.getSelectedItem().toString()
								+ "'s password");
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	

}
