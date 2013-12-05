package review.providers;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import review.model.CodeFileModel;
import review.model.Project;


public class CodeFilesContentProvider implements ITreeContentProvider {

  private static CodeFileModel model;

  @Override
  public void dispose() {
  }

  public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    this.model = (CodeFileModel) newInput;
  }

  @Override
  public Object[] getElements(Object inputElement) {
    return model.getProjects().toArray();
  }

  @Override
  public Object[] getChildren(Object parentElement) {
    if (parentElement instanceof Project) {
      Project project = (Project) parentElement;
      return project.getCodeFiles().toArray();
    }
    return null;
  }

  @Override
  public Object getParent(Object element) {
    return null;
  }

  @Override
  public boolean hasChildren(Object element) {
    if (element instanceof Project) {
      return true;
    }
    return false;
  }

public static void inputChanged() {
	// TODO Auto-generated method stub
	model.notify();
	
	
}

} 