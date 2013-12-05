package review.model;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class EditorInput implements IEditorInput {
    
	private static Project project ;
	private static Integer fileId;
	private static String fileName;
	
	
	public EditorInput() {}
	
	public EditorInput(Project project, Integer fileId , String fileName) {
		EditorInput.project = project;
		EditorInput.fileName = fileName;
		EditorInput.fileId = fileId;
	}
	
	public void setProject(Project project)
	{
		this.project = project;
		
	}
	
	public String getFilename(){
		return fileName;
	}
	
	public void setFilename(String fileName) {
		this.fileName = fileName;
	}
	
	public static Object getProject() {
		return project;
	}
	
	
	@Override
	public Object getAdapter(Class arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getToolTipText() {
		// TODO Auto-generated method stub
		return null;
	}

}
 