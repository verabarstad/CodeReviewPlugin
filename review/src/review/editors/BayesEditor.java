package review.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import com.mysql.jdbc.ResultSetMetaData;

import java.awt.TextArea;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import review.classification.NaiveBayes;
import review.model.CodeFile;
import review.model.Database;

public class BayesEditor extends EditorPart implements IMenuListener {
    
	private Integer intTotal;
	private Integer intLikes;
	private Integer intDislikes;
	private NaiveBayes naiveB = new NaiveBayes();

	private CodeFile reviewFile;
	public static final String ID = "review.editors.BayesEditor";

	public BayesEditor() {
		super();
	}


	// -------------------------------------
	public void createPartControl(Composite parent) {
	buildPage(parent);
	}
    // --------------------------------------------------
	private void buildPage(final Composite composite)  {
	//  -------------------------------------------------
		 GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		 gridData.horizontalSpan = 7;
		    
		//Composite composite = new Composite(parent, SWT.NONE);
		Group training = new Group(composite,SWT.NONE);
		training.setText("Training data");
		training.setLayoutData(gridData);
		training.setLayout(new RowLayout(SWT.HORIZONTAL));
		Label lbl = new Label(training,SWT.NONE);
		lbl.setText("Total");
		
		Text total = new Text(training,SWT.BORDER|SWT.SINGLE);	
		total.setText(""+ NaiveBayes.getTotal() + "");
		
		Text like = new Text(training,SWT.BORDER|SWT.SINGLE);
		like.setText(""+ NaiveBayes.getLikes() + "");
		
		Text dislike = new Text(training,SWT.BORDER|SWT.SINGLE);
		dislike.setText(""+ naiveB.getDislikes() + "");
		
		
		Group group = new Group(composite, SWT.NONE);
	    group.setText("Input data");
	   
	    group.setLayoutData(gridData);
	    group.setLayout(new RowLayout(SWT.HORIZONTAL));
	    Text txtAnotherTest = new Text(group, SWT.NONE);

	    txtAnotherTest.setText("Another test ");
	    
	    final Text F1 = new Text(group, SWT.BORDER | SWT.SINGLE) ;
		F1.setText("Y");
		final Text F2 = new Text(group, SWT.BORDER | SWT.SINGLE) ;
		F2.setText("Y");
		final Text F3 = new Text(group, SWT.BORDER | SWT.SINGLE) ;
		F3.setText("N");
		final Text F4 = new Text(group, SWT.BORDER | SWT.SINGLE) ;
		F4.setText("Y");
		final Text F5 = new Text(group, SWT.BORDER | SWT.SINGLE) ;
		F5.setText("N");
		final Text F6 = new Text(group, SWT.BORDER | SWT.SINGLE) ;
		F6.setText("M");
		Button analyze = new Button(group, SWT.PUSH);
		analyze.setText("Analyze");
	    
		
	    
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		layout.numColumns = 7;

		GridData gd = new GridData(GridData.BEGINNING);
	    	    
		final Text textArea = new Text(composite, SWT.MULTI | SWT.BORDER
				| SWT.WRAP | SWT.V_SCROLL);
		textArea.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		textArea.setEnabled(false);
		textArea.setText("Test");
	
		analyze.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				HashMap<String,String> analyzeMap = new HashMap<String,String>();
				analyzeMap.put("F1",F1.getText());
				analyzeMap.put("F2",F2.getText());
				analyzeMap.put("F3",F3.getText());
				analyzeMap.put("F4",F4.getText());
				analyzeMap.put("F5",F5.getText());
				analyzeMap.put("F6",F6.getText());
				
				String result = NaiveBayes.classify(analyzeMap);
				textArea.setText(result);
				composite.layout();
				}
		}); 
	}


	public void dispose() {
	}

	// -------------------------------------
	// This stuff below is just needed to make the EditorPart happy
	public void doSave(IProgressMonitor monitor) {	}

	public void doSaveAs() {	}

	public void init(IEditorSite site, IEditorInput input) 	throws PartInitException {
		setSite(site);
		setInput(input);
		reviewFile = (CodeFile) input;
		setPartName(reviewFile.getFilename());
	}

	public boolean isDirty() {
		return false;
	}

	public boolean isSaveAsAllowed() {
		return false;
	}

	public void setFocus() {

	}
	
	
	@Override
	public void menuAboutToShow(IMenuManager manager) {
		// TODO Auto-generated method stub
	
	}
}
