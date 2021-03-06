package review.views;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import review.editors.Analyzis;
import review.editors.BayesEditor;
import review.editors.HumanRating;
import review.model.CodeFile;
import review.model.CodeFileModel;
import review.model.Project;
import review.providers.CodeFilesContentProvider;
import review.providers.StyledCodeFileLabelProvider;
import review.utils.Common;

public class FileList extends ViewPart {
	public static final String ID = "review.views.FileList";
	private static TreeViewer viewer;	
	//private DrillDownAdapter drillDownAdapter;

	private Action action1;
	private Action action2;
	private Action automatic;

	public static TreeViewer getViewer() {
		return viewer;
	}
	
	ISelectionListener listener = new ISelectionListener() {   

		@Override
		public void selectionChanged(IWorkbenchPart arg0, ISelection sel) {
			if (!(sel instanceof IStructuredSelection))
	              return;
	           IStructuredSelection ss = (IStructuredSelection) sel;
	           Object o = ss.getFirstElement();
	           if (o instanceof CodeFile){
	              viewer.setInput(ss.size()==1 ? o : null);
	              System.out.println("show codefile details..");
	           } 
	           if (o instanceof Project){
	        	   System.out.println("show project details..");        
	           }	
		}
     };
     

	public void createPartControl(Composite parent) {	
	
		
		getSite().getPage().addSelectionListener(listener);
		getSite().setSelectionProvider(viewer);
		
		
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new CodeFilesContentProvider());
		viewer.setLabelProvider(new StyledCodeFileLabelProvider());
		
		viewer.setAutoExpandLevel(2);
		viewer.setInput(new CodeFileModel());
		makeActions();
		hookContextMenu();
		
		Common.setCurrentUser();
		
	    viewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				TreeViewer viewer = (TreeViewer) event.getViewer();
				IStructuredSelection thisSelection = (IStructuredSelection) event.getSelection();
				Object selectedNode = thisSelection.getFirstElement();
				
				if (thisSelection.getFirstElement() instanceof CodeFile) {							
					CodeFile o = (CodeFile) thisSelection.getFirstElement();					
					//open o in editor
					IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					try {
						page.openEditor((IEditorInput) o, HumanRating.ID);
					} catch (PartInitException e) {
						e.printStackTrace();
					}				
				}
				viewer.setExpandedState(selectedNode,
						!viewer.getExpandedState(selectedNode));
			}
		});
		
		
		 viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {			
				IStructuredSelection thisSelection = (IStructuredSelection) event.getSelection();
				if (thisSelection.getFirstElement()  instanceof Project) {
					Project p = (Project) thisSelection.getFirstElement();
				    //do something with p
					CodeFileDetails.updateText(p);
					CodeFileDetails.refresh();
					
				}
				if (thisSelection.getFirstElement() instanceof CodeFile) {
					//do something with codefile
					CodeFile o = (CodeFile) thisSelection.getFirstElement(); 
					CodeFileDetails.updateText(o);
					CodeFileDetails.refresh();
					}	
			}
		 });


		viewer.getTree().addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(final KeyEvent e) {
				if (e.keyCode == SWT.DEL) {
					final IStructuredSelection selection = (IStructuredSelection) viewer
							.getSelection();
					if (selection.getFirstElement() instanceof CodeFile) {
						//CodeFile o = (CodeFile) selection.getFirstElement(); 
						//delete
					}

				}
			}
		});

	}
	
    public static Object getSelectedElement() {
    	ISelection selection = viewer.getSelection();
		Object obj = ((IStructuredSelection)selection).getFirstElement();
		return obj;
    }
    
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				FileList.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}
	
	

	private void fillContextMenu(IMenuManager manager) {
		manager.add(action1);
		//manager.add(action2);
		manager.add(automatic);
		manager.add(new Separator());
		//drillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void makeActions() {
		action1 = new Action() {
			public void run() {
				viewer.refresh();
			}
		};
		action1.setText("Reload");
		action1.setToolTipText("Reload from database");
		action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
		action2 = new Action() {
			public void run() {
				Object obj = getSelectedElement();
				if (obj instanceof CodeFile) {							
					IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					try {
						page.openEditor((IEditorInput) obj, BayesEditor.ID);
					} catch (PartInitException e) {
						
						e.printStackTrace();
					}				
				}
			}
		};
		action2.setText("Analyze file");
		action2.setToolTipText("Analyze this file");
		action2.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
		automatic = new Action() {
			public void run() {
				Object obj = getSelectedElement();
				if (obj instanceof CodeFile) {							
					IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					try {
						page.openEditor((IEditorInput) obj, Analyzis.ID);
					} catch (PartInitException e) {
						
						e.printStackTrace();
					}				
				}
			}
		};
		automatic.setText("Automatic analyze file");
		automatic.setToolTipText("Automaticaly analyze this file");
		automatic.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	
	public void refresh() {
		viewer.refresh();
	}
	
	public void update() {
		//viewer.update(viewer, null);
	}
	
	public void dispose() {
        getSite().getPage().removeSelectionListener(listener);
     }
	
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}
