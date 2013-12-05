package review.handlers;


import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

import review.model.CodeFile;


public class AnalyzeFile extends AbstractHandler {
 
  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
    Shell shell = HandlerUtil.getActiveShell(event);
    ISelection sel = HandlerUtil.getActiveMenuSelection(event);
    IStructuredSelection selection = (IStructuredSelection) sel;

    Object firstElement = selection.getFirstElement();
    if (firstElement instanceof CodeFile) {
    	MessageDialog.openInformation(shell, "Info","Analyze this file");
    } 
    else {
        MessageDialog.openInformation(shell, "Info","Please select a Code file added for review");
    }
    return null;
  }
  

  
}