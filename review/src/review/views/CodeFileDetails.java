package review.views;


import java.util.Iterator;


import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.part.ViewPart;

import review.model.CodeFile;
import review.model.CodeReview;
import review.model.Project;
import review.model.Database;

public class CodeFileDetails extends ViewPart{

	public static final String ID = "review.views.CodeFileDetails";
	
	private static TextViewer textViewer;
	private static TableViewer tableViewer;
	
	private static String inputText;
	private static CodeFile codeFile;
	private static Project project;
	private static Database db = new Database();
	
	private static void setCodeFile(CodeFile codeFile) {
		CodeFileDetails.codeFile = codeFile;
	}
	private static void setProject(Project project) {
		CodeFileDetails.project = project;
	}
	
	public static void setInputText(String inputText) {
		CodeFileDetails.inputText = inputText;
	}
	
	public void createTableViewer(Composite parent) {
		 tableViewer = new TableViewer(parent, SWT.H_SCROLL|SWT.V_SCROLL);
		 tableViewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
			      | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);

			// Create the columns 
			// Not yet implemented
			//createColumns(parent);

			// Make lines and make header visible
			final Table table = tableViewer.getTable();
			table.setHeaderVisible(true);
			table.setLinesVisible(true); 
			
			// Set the ContentProvider
			tableViewer.setContentProvider(ArrayContentProvider.getInstance());

			// Get the content for the Viewer,
			// setInput will call getElements in the ContentProvider
			//tableViewer.setInput(someData...); 
			
			getSite().setSelectionProvider(tableViewer);
		
	}
	
	public void createTextViewer(Composite parent) {
textViewer = new TextViewer(parent,SWT.MULTI | SWT.H_SCROLL|SWT.V_SCROLL);
	    
		textViewer.setDocument(new Document(inputText));
		 TextPresentation style = new TextPresentation();	 
		 getSite().setSelectionProvider(textViewer);
		
	}
	@Override
	public void createPartControl(Composite parent) {
		//les fil fra noe i stedet..
		createTextViewer(parent);		
		//createTableViewer(parent);    
	}
	
	public static void updateText(Object o) {
		
		if (o instanceof CodeFile) {
			codeFile = (CodeFile)o;			
			inputText = "CodeFile = " + codeFile.getFullPathname() 
			  + " FileId : "  + codeFile.getFileId() 
					+ " Added by " + codeFile.getAddedBy() 
					+ " Date  : " + codeFile.getDate().toString() + "\n";
			 inputText += db.getFileInfo(codeFile.getFileId());
			 			
		}
		if (o instanceof Project) {
			CodeFile c;
			project = (Project)o;
			inputText = "Project : " + project.getName();
			Iterator i = project.getCodeFiles().iterator();
			while (i.hasNext()) {
						
			  c = (CodeFile)i.next();
			  inputText += "\n  " + c.getFilename();
			}
		}
				
		textViewer.setDocument(new Document(inputText));
		textViewer.refresh();		
		
    }

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}
	public static void refresh() {
		textViewer.refresh();
	}

}
