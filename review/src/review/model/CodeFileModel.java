package review.model;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CodeFileModel  {
	
	public static CodeFileModel  model;

  public static boolean existProject(String project) {
	  return false;
  }
  public static List<Project> getProjects() {	  	    
	  List<Project> projects = new ArrayList<Project>();  
	  Database db = new Database();
	  projects = db.getProjects();
	 
//	  
//	  Registry reg = Registry.getRegistry();
//	  
//	  Set<Project> codeSet = reg.getEntrySet();	 
//	 
//	
//	  
//	  Iterator i = codeSet.iterator();
//
//      while(i.hasNext()){
//        Map.Entry me = (Map.Entry)i.next();
//        String myClass = me.getValue().getClass().getName();
//        if (myClass == "review.model.Project"){
//        	projects.add( (Project)me.getValue());
//        }         	
//      }
   db.close();
   return projects;
  }
 
  public static void addCodeFile(String newProject, CodeFile file){
	  List<Project> projects = model.getProjects();
	  Project project;
	  
	  if (projects.indexOf(newProject) > 0)
	  { 
		  //project exists
		  project = projects.get(projects.indexOf(newProject));
	  }
	  else
	  {
		   //new project, insert into db
		   project = new Project();
		   project.setName(newProject);
	  }
	   
	   
	  project.getCodeFiles().add(file);  
	  
	  }
	 
  }
 