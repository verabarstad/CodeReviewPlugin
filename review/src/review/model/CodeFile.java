package review.model;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class CodeFile implements IEditorInput  , Serializable {
  private Integer fileId;
  private String filename = "";
  private String project = "";
  private String description = "";
  private String fullpathname = "";
  private Date date;
  private Path path;
  
  private byte[] contentByte;
  private String contentString;
  private String addedBy;
  private List<CodeReview> codeReviews = new ArrayList<CodeReview>();
  
  public Integer getFileId(){
	  return this.fileId;
  }
  
  public CodeFile(String filename) {
    this.filename = filename;
  }
  public List<CodeReview> getCodeReviews() {
	    return codeReviews;
  }
  
  public CodeFile() {
	  
  }
  
  public CodeFile(Integer fileId, String filename, String addedBy, String project) {
    this.fileId = fileId;
	this.filename = filename;
    this.addedBy = addedBy;
    this.project = project;
    this.date = new Date();
  }

  public String getFilename() {
    return filename;
  }
  
  public Date getDate() {
	  return this.date;
  }
  
  public Path getPath() {
	  return path;
  }
  
  
 

  public void setFilename(String filename) {
    this.filename = filename;
  }
  
  public void setContentByte(byte[] content) {
	  this.contentByte = content;
  }
  public byte[] getContentByte () {
    return contentByte;
  }
  
  public void setContentString(String content) {
	  this.contentString = content;
  }
  public String getContentString () {
    return contentString;
  }
  
  public String getFullPathname() {
	    return filename;
	  }

	  public void setFullPathname(String pathname) {
	    this.fullpathname = pathname;
	  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
  
  public String getAddedBy() {
	    return addedBy;
	  }

	  public void setAddedBy(String addedBy) {
	    this.addedBy = addedBy;
	  }

public void setPath(Path path) {
	this.path = path;
	
}

public String getProject() {
  return this.project;
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
