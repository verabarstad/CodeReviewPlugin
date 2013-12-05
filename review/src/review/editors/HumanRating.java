package review.editors;

import java.awt.BorderLayout;
import java.awt.Dimension;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.jface.text.source.AnnotationPainter;
import org.eclipse.jface.text.source.AnnotationRulerColumn;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.IAnnotationAccess;
import org.eclipse.jface.text.source.IOverviewRuler;
import org.eclipse.jface.text.source.ISharedTextColors;
import org.eclipse.jface.text.source.LineNumberRulerColumn;
import org.eclipse.jface.text.source.OverviewRuler;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.internal.editors.text.EditorsPlugin;
import org.eclipse.ui.internal.util.BundleUtility;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.texteditor.SourceViewerDecorationSupport;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.osgi.framework.Bundle;

import review.utils.Common;
import review.views.CodeFileDetails;
import review.analyzis.Analyse;
import review.analyzis.Static;
import review.model.CodeFile;
import review.model.Database;

public class HumanRating extends EditorPart implements IMenuListener {
	public static final String ANNO_TYPE = "review";
	public static final String ANNO_GOOD = "annotateElemOverviewRuler";

	private CodeFile reviewFile;

	private Action like;
	private Action dislike;
	
	String output = "";
	SourceViewer sv;
	Document document;

	public SourceViewer getSourceViewer() {
		return sv;
	}

	public Document getDocument() {
		return document;
	}

	// identifiers, images and colors
	public static Image ANNO_IMAGE;
	public static final RGB GOOD_RGB = new RGB(0, 255, 0);
	public static final String ID = "review.editors.HumanRating";

	// annotation model
	public AnnotationModel annotationModel = new AnnotationModel();
	public AnnotationModel fAnnotationModel = new AnnotationModel();

	protected SourceViewerDecorationSupport _sds, _sds2;

	public HumanRating() {
		super();
	}


	// -------------------------------------
	public void createPartControl(Composite parent) {
	// -------------------------------------
		buildPage(parent);
		makeActions();
		hookContextMenu();
	}

	// -------------------------------------
	public void addAnnotation(int offset, int length, String text) {
	// -------------------------------------
		Annotation annotation = new Annotation(true);
		annotation.setType(ANNO_GOOD);
        annotationModel.addAnnotation(annotation, new Position(offset, length));  
		// sv.refresh();
	}
	
	// -------------------------------------	
	public void addAnnotations() {	
	// -------------------------------------
			Connection conn = Database.getDBConnection();
		    String query = "SELECT * from Review where fileID = " + reviewFile.getFileId() + " ORDER BY date";    
		    java.sql.Statement st;
			try {
				st = conn.createStatement();
				ResultSet rs = st.executeQuery(query);		    
				while (rs.next()) {
					addAnnotation(rs.getInt("offset"),rs.getInt("length"),rs.getString("addedBy"));
				}
				st.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}


	// -------------------------------------
    private void addReview(int reviewType) {
    // -------------------------------------
    	String selection = sv.getTextWidget().getSelectionText();
		String addedBy = Common.getUser();
    	int result;
    	JPanel myPanel = new JPanel(new BorderLayout());

		JTextArea textArea = new JTextArea();
		textArea.setPreferredSize(new Dimension(150, 300));
		
		myPanel.add(textArea, BorderLayout.NORTH);
		
		result = JOptionPane.showConfirmDialog(null, myPanel, "Add comment by "
				+ Common.getUser() + " , Type : " + reviewType, JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {

			int offset = reviewFile.getContentString().indexOf(selection);
			int length = selection.length();
			int reviewId = 0;
			addAnnotation(offset, length, textArea.getText());
			
			
			reviewId = Database.insertCodeReview(reviewFile.getFileId(), reviewType, offset, length, textArea.getText(), 
					addedBy, Database.getCurrentTimeStamp());
			// generate static analysis
			Analyse.insertAnalyzis(reviewFile.getFileId(), reviewId, reviewType, selection);
		
			CodeFileDetails.updateText(reviewFile);
		}
		      
    	
    }

    // --------------------------------------------------
	private void buildPage(Composite parent) {
	//  -------------------------------------------------
		   IAnnotationAccess fAnnotationAccess = new AnnotationMarkerAccess();

		int VERTICAL_RULER_WIDTH = 12;
		ISharedTextColors sharedColors = EditorsPlugin.getDefault().getSharedTextColors();

		IOverviewRuler overviewRuler = new OverviewRuler(fAnnotationAccess,	VERTICAL_RULER_WIDTH, sharedColors);
		CompositeRuler ruler = new CompositeRuler(VERTICAL_RULER_WIDTH);

		AnnotationRulerColumn annotationRuler = new AnnotationRulerColumn(annotationModel, 16, fAnnotationAccess);
		ruler.setModel(annotationModel);

		overviewRuler.setModel(annotationModel);

		ruler.addDecorator(2, new LineNumberRulerColumn());
		overviewRuler.addAnnotationType(ANNO_GOOD);
		overviewRuler.addHeaderAnnotationType(ANNO_GOOD);

		overviewRuler.setAnnotationTypeLayer(ANNO_GOOD, 3);

		overviewRuler.setAnnotationTypeColor(ANNO_GOOD,new Color(Display.getDefault(), GOOD_RGB));

		sv = new SourceViewer(parent, ruler, overviewRuler, true, SWT.READ_ONLY	| SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);

		document = new Document();
		sv.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		annotationModel.connect(document);
		sv.setDocument(document, annotationModel);

		//  paint the annotations
		AnnotationPainter ap = new AnnotationPainter(sv, fAnnotationAccess);
		ap.addAnnotationType(ANNO_TYPE);
		ap.setAnnotationTypeColor(ANNO_TYPE, new Color(Display.getDefault(),GOOD_RGB));
		document.set(reviewFile.getContentString());

		addAnnotations();
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
	
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				HumanRating.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(sv.getTextWidget());
		sv.getTextWidget().setMenu(menu);
		getSite().registerContextMenu(menuMgr, sv);
	}
	
	private void fillContextMenu(IMenuManager manager) {
		manager.add(like);
		manager.add(dislike);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	@SuppressWarnings("restriction")
	private void makeActions() {
		like = new Action() {
			public void run() {	
				addReview(1);  //1 = Like 
			}				
		};
		like.setText("Like");
		like.setToolTipText("Select text and Push to like");
		like.setId("A2");
		Bundle bundle = Platform.getBundle("review");
		java.net.URL fullPathString = BundleUtility.find(bundle, "icons/thumb_up2.jpg");	
        like.setImageDescriptor(ImageDescriptor.createFromURL(fullPathString));
		
        dislike = new Action() {
			public void run() {					addReview(2);  //2 = Like
			}	
		};
		dislike.setText("Dislike");
		dislike.setToolTipText("Select text and Push to dislike");
		dislike.setId("D1");
		fullPathString = BundleUtility.find(bundle, "icons/thumb_down2.jpg");
        dislike.setImageDescriptor(ImageDescriptor.createFromURL(fullPathString));
		
	}

	@Override
	public void menuAboutToShow(IMenuManager manager) {
		// TODO Auto-generated method stub
	
	}
}
