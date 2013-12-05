package review.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Project  implements Serializable{
  private String name;
  //new
  private String id;
  private int sort;
  private List<CodeFile> codefiles =  new ArrayList<CodeFile>();

  //new method
  public String getId() {
	    return id;
	  }

 public void setId(String id) {
	    this.id = id;
	  }
   //new 
	  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getSort() {
    return sort;
  }

  public void setSort(int sort) {
    this.sort = sort;
  }

  public List getCodeFiles() {
    return codefiles;
  }
  public CodeFile getCodeFile(String filename) {
	  CodeFile codeFile = null;
	  String funnet = "";
	  
	  Iterator<CodeFile> i = codefiles.iterator();
	  while (i.hasNext()) {
		  codeFile = i.next();
		  
		  if ( codeFile.getFilename().equals(filename) ) {
			  funnet = codeFile.getFilename() ;
			  break;
		  }
		  
	  }
	  if (funnet.equals(filename))
		  return  codeFile;
	  else
	    return null;
	 
  }
  
  public void addCodeFile(CodeFile file) {
	  if (codefiles.indexOf(file) > 0) {
		  System.out.print("exists");
		  
	  }
	  else
	  {
		  codefiles.add(file);
	  }
	  
	  
  }

public void setCodeFiles(ArrayList<CodeFile> codeFiles2) {
	this.codefiles = codeFiles2;
	
}

} 