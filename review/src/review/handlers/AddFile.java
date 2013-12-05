package review.handlers;


import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageDeclaration;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;
import review.model.Database;

public class AddFile extends AbstractHandler {
 
  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
    Shell shell = HandlerUtil.getActiveShell(event);
    ISelection sel = HandlerUtil.getActiveMenuSelection(event);
    IStructuredSelection selection = (IStructuredSelection) sel;

    Object firstElement = selection.getFirstElement();
    if (firstElement instanceof ICompilationUnit) {
    	fileToDB((ICompilationUnit) firstElement);
    } 
    else {
        MessageDialog.openInformation(shell, "Info","Please select a Java source file");
    }
    return null;
  }
  
  private void fileToDB(ICompilationUnit cu) {
	  Database.addCodeFile(cu,getPackage(cu));	  
  }

  
  private String getPackage(ICompilationUnit cu){  
	  IPackageDeclaration[] pDeclaration  = null;
	try {
		pDeclaration = cu.getPackageDeclarations();
		if (pDeclaration.length > 0 ) {
			String[] decString = pDeclaration[0].toString().split("\\[");
			decString = decString[0].toString().split(" ");
			return decString[1];	
		}
		else {
			return "";
		}
		
	} catch (JavaModelException e) {
		//  no package declaration
		return "";			
	}
  }
  
}