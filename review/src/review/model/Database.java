package review.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;









import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jface.dialogs.MessageDialog;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.Statement;

import review.utils.Common;
import review.views.FileList;

public class Database {
	private static String dbName = "cr";
	private static String dbDriver= "com.mysql.jdbc.Driver";
	private static String dbUserName = "root";
	private static String dbPassword = "root";
	private static String host = "localhost";
	
	private static String connectionString = "jdbc:mysql://" + host + ":3306/" + dbName + "?user=" + dbUserName + "&password=" + dbPassword ;
	
	private static java.sql.PreparedStatement preparedStatement = null;
	public static Connection conn;;
	
	public Database() {
		conn = getDBConnection();
	}
	
	public static Integer getInteger(String sql) throws SQLException {
		//Connection conn = getDBConnection();
		Integer res = null;
		java.sql.Statement st = Database.conn.createStatement();
		try {
			ResultSet rs = st.executeQuery(sql);
			if(rs!=null && rs.next()){
	            res = rs.getInt(1);
	        }
			st.close();
			//conn.close();
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;		
		}
		finally {
			if (st != null) {
				st.close();
			//    conn.close();	
			}
		}		
	}
	public static void setInitialParameters(int leaveOutFileId, Parameters parameters) {
		int intTotal = 0;
         int intLikes = 0; 
         int intDislike = 0;
		try {
			String sql = "select * from " 
					+ "(SELECT ra.fileId, ra.reviewId, ra.reviewType from " 
					  + "ReviewFeatureAnalysis ra group by ra.fileId, ra.reviewId)  r " 
					+ "where fileId <> " + leaveOutFileId ;
			System.out.println("Parsing fileid : " + leaveOutFileId);
			//Connection conn = Database.getDBConnection();
			java.sql.Statement st = Database.conn.createStatement();

			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				 intTotal = intTotal + 1;
				 if (rs.getInt("reviewType") == 1)
					 intLikes = intLikes + 1;
				 else 
					 intDislike = intDislike + 1;
				 
			  }
			rs.close();
			parameters.setTotal(intTotal);
			parameters.setLikes(intLikes);
			parameters.setDislikes(intDislike);
			//conn.close();
			
			//intTotal = db.getInteger(sql);
		//	System.out.println(sql);
		     } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
	}
	
	
//	public static String getString(String sql) throws SQLException {
//		//Connection conn = getDBConnection();
//		String res = null;
//		java.sql.Statement st = conn.createStatement();
//		try {
//			ResultSet rs = st.executeQuery(sql);
//			res = rs.getString(1);			
//			
//			return res;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return null;		
//		}
//		finally {
//			if (st != null) {
//				st.close();
//				//conn.close();
//			}
//		}
//	
//	}
//	
//	
	public static Connection getDBConnection() {
		 
		Connection dbConnection = null;
		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		try {
			dbConnection = DriverManager.getConnection(connectionString);
			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbConnection; 
	}
 

	 
	public static String getProject(String project) {
	  
		try {			
			String projectFound = "";
			//Connection conn = getDBConnection();
			String query = "SELECT * FROM Project where name = '" + project + "'";
	      java.sql.Statement st = Database.conn.createStatement();

			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery(query);
		    //read files from db
			if(rs!=null && rs.next()){
				projectFound = rs.getString("name");
	        }		
			st.close();
			//conn.close();
			return  projectFound;
			
			} catch (SQLException  e) {
				e.printStackTrace();
				
				return null;
			}
	}

	
	
	public static void insertProject(String project) {
		//Connection conn = null;
		Statement statement = null;
			try {
			    //conn = getDBConnection();
				statement = (Statement) Database.conn.createStatement();
				String query = "INSERT into Project(name) values('"+ project + "')";
				statement.executeUpdate(query);
				statement.close();
				//conn.close();
				statement.close();				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			   
			}			
	}
	
	public static Integer getCodeFile(String fileName, String packageString, String project) {
		  
		try {			
			Integer found = 0;
			//Connection conn = getDBConnection();
			String query = "SELECT * FROM CodeFile where project = '" + project + "' and fileName = '" + fileName + "' and package = '" + packageString + "'";	     
			java.sql.Statement st = Database.conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			if(rs!=null && rs.next()){
				found = rs.getInt("fileId");
	        }		
			rs.close();
			//conn.close();
			return  found;
			
			} catch (SQLException  e) {
				e.printStackTrace();
				return null;
			}
	}
	
	public ArrayList<Integer> getAllFiles() {
		ArrayList<Integer> files= new ArrayList<Integer>();
		
		String query = "Select fileId from CodeFile order by fileId ";
		//ection conn = getDBConnection();
		try {
		java.sql.Statement st = Database.conn.createStatement();

		ResultSet rs = st.executeQuery(query);
		while (rs.next()) {
			  
			    files.add(rs.getInt("fileId"));
		  }
		rs.close();
		//conn.close();
		return  files;
		
		} catch (SQLException  e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	public static int getHumanReviewType(int fileId, int offset, int length) {
		int type = 0;
		int countDislikes = 0;
		int countLikes = 0;
		String query = "Select reviewType from Review where "
				+ " fileId = ? " 
        		+ " and offset = ? " 
        		+ " and length = ? ";
		//Connection conn = getDBConnection();
		try {
	       	 preparedStatement = Database.conn.prepareStatement(query);
	    		
	    		preparedStatement.setInt(1, fileId);
	    		preparedStatement.setInt(2, offset);
	    		preparedStatement.setInt(3, length);
	            ResultSet rs = preparedStatement.executeQuery();
	            // Display all the data in the table.
	            while (rs.next()) {
	            	if (rs.getInt(1) == 1) 
	            		countLikes ++;
	            	else {
						countDislikes ++;
					}
	            
	            }
	            rs.close();
	      //      conn.close();
	            
			} catch (SQLException e1) {
				e1.printStackTrace();
			}	
		if (countLikes == 0 && countDislikes == 0)
			//no likes
			type = 0; //undefined, no likes/dislikes for this method
		else {
			if (countLikes == countDislikes)
				type = 9;  //equal
			else {
				
			
			if (countLikes > countDislikes ) {
				type = 1;
			}
			else {
				type = 2;
			}
			}
		}
		
		return type;
		
	}
	
	public static CodeReview  getHumanReview(int fileId, int offset, int length) {
		CodeReview codeReview = null;
		
		
		String query = "Select * from Review where "
				+ " fileId = ? " 
        		+ " and offset = ? " 
        		+ " and length = ? ";
		//Connection conn = getDBConnection();
		try {
	       	 preparedStatement = Database.conn.prepareStatement(query);
	    		
	    		preparedStatement.setInt(1, fileId);
	    		preparedStatement.setInt(2, offset);
	    		preparedStatement.setInt(3, length);
	            ResultSet rs = preparedStatement.executeQuery();
	            // Display all the data in the table.
	            while (rs.next()) {
	            	codeReview = new CodeReview(rs.getInt("fileId"), rs.getInt("offset"),
	            			rs.getInt("length"), rs.getInt("reviewType"), rs.getString("comment"),
	            			rs.getString("addedBy"), rs.getString("date"));
	            	codeReview.setReviewId(rs.getInt("reviewId"));
	            
	            }
	            rs.close();
	      //      conn.close();
	            
			} catch (SQLException e1) {
				e1.printStackTrace();
			}	
		
		
		return codeReview;
	
	}
	
	public static int getReviewId(int fileId, int reviewType, int offset, int length, String comment,String addedBy, String date){
		int id = 0;
        String query = "Select reviewId from Review where " 
        		+ " fileId = ? " 
        		+ " and reviewType = ? " 
        		+ " and offset = ? " 
        		+ " and length = ? "
        		+ " and comment = ? and addedBy = ? and date = ?";
        
        //Connection conn = getDBConnection();	
        try {
       	 preparedStatement = Database.conn.prepareStatement(query);
    		
    		preparedStatement.setInt(1, fileId);
    		preparedStatement.setInt(2, reviewType);
    		preparedStatement.setInt(3, offset);
    		preparedStatement.setInt(4, length);
    		preparedStatement.setString(5, comment);
    		preparedStatement.setString(6, addedBy);
           preparedStatement.setString(7, date);
     		//preparedStatement.executeUpdate();
     		  // Run an SQL query on the table.
            ResultSet rs = preparedStatement.executeQuery();
            // Display all the data in the table.
            while (rs.next()) {
            	id = rs.getInt(1);
                //System.out.println("ReviewId :  " + rs.getInt(1) );
            }
            rs.close();
          //  conn.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}	
		
		return id;
	}
	
	public static int insertCodeReview(int fileId, int reviewType, int offset, int length, String comment,String addedBy, String date)
	{
		
		String query = "INSERT into Review(fileId,reviewType,offset, length, comment,addedBy, date) " + " values(?,?,?,?,?,?,?)";
		
         try {
        	// Connection conn = getDBConnection();	
        	 preparedStatement = Database.conn.prepareStatement(query);
     		
     		preparedStatement.setInt(1, fileId);
     		preparedStatement.setInt(2, reviewType);
     		preparedStatement.setInt(3, offset);
     		preparedStatement.setInt(4, length);
     		preparedStatement.setString(5, comment);
     		preparedStatement.setString(6, addedBy);
            preparedStatement.setString(7, date);
      		preparedStatement.executeUpdate();
      		//conn.close();
      		return getReviewId(fileId,reviewType,offset, length, comment,addedBy, date);
      		
		} catch (SQLException e1) {
			
			e1.printStackTrace();
			return 0;
		}			
	}
	
	
	public static void insertFeatureAnalysis(int fileId, int reviewId,int reviewType, String featureId, String value, int pValue) {
		String query = "INSERT into ReviewFeatureAnalysis(fileId,reviewId,reviewType,featureId,value, pValue) " 
	       + " values(?,?,?,?,?,?)";
		//Connection conn = getDBConnection();	
         try {
        	 preparedStatement = Database.conn.prepareStatement(query);
     		preparedStatement.setInt(1,fileId);
     		preparedStatement.setInt(2, reviewId);
     		preparedStatement.setInt(3,reviewType);
     		preparedStatement.setString(4, featureId);
     		preparedStatement.setString(5, value);
     		preparedStatement.setInt(6, pValue);
      		preparedStatement.executeUpdate();
      		//return getReviewId(fileId,reviewType,offset, length, comment,addedBy, date);
      	//	conn.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}	
         
	}
	
	
	public static void insertCodeFile(String fileName, String packageString, String project, String content) {
		  
		String query = "INSERT into CodeFile(fileName,package,project,addedBy, date, contentString) " + " values(?,?,?,?,?,?)";
		//Connection conn = getDBConnection();	
         try {
        	preparedStatement = Database.conn.prepareStatement(query);
     		preparedStatement.setString(1, fileName);
     		preparedStatement.setString(2, packageString);
     		preparedStatement.setString(3, project);
     		preparedStatement.setString(4, Common.getUser());
     		preparedStatement.setString(5, getCurrentTimeStamp());
            preparedStatement.setString(6, content);
      		preparedStatement.executeUpdate();
      	//	conn.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}	
         
	}
	
	public static void addCodeFile(ICompilationUnit cu, String packageString) {
		  try {	 
			  String fileName = cu.getCorrespondingResource().getName();	
			  String project = cu.getCorrespondingResource().getProject().getName();
			  String reviewer = Common.getUser();
			  //Project myProject = null;  
				   if (getProject(project) == "" ) 
				   {
					   insertProject(project);
					   //myProject = new Project();
					  // myProject.setName(project);
				   }				   
				   // check if file exists
				   if (getCodeFile(fileName,packageString,project) == 0 ){
					   
					  insertCodeFile(fileName,packageString,project,cu.getSource());
					   FileList.getViewer().refresh();
				   }
				   else {
				    	  MessageDialog.openInformation(null, "Info",
	    	          "This file are already added for CodeReview!");		    
				}			   
 
	} catch (Exception e) {
			// TODO: handle exception
		}
	  }
	

	public static ArrayList<CodeFile> getCodeFiles(String project) {
		try {
	
		ArrayList<CodeFile> files = new ArrayList<CodeFile>();	
		//Connection conn = getDBConnection();
		String query = "SELECT * FROM CodeFile "; 
	      
		if (project.equals("") )
		  {
			
		  }
		else {
			query += "where project = '" + project + "'";
		}
			
		  CodeFile file ;
		java.sql.Statement st = Database.conn.createStatement();

		ResultSet rs = st.executeQuery(query);
		while (rs.next()) {
			  file = new CodeFile(rs.getInt("fileId"), rs.getString("fileName")+"("+ rs.getString("package")+")",rs.getString("addedBy"),
					  rs.getString("project"));
			    file.setContentString(rs.getString("contentString"));
			    files.add(file);
		  }
		//conn.close();
		return  files;
		
		} catch (SQLException  e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	public static ArrayList<Project> getProjects() {
		try {
			ArrayList<Project> projects = new ArrayList<Project>();  
		//	Connection conn = getDBConnection();
			String query = "SELECT * FROM Project";

			Project project ;
			java.sql.Statement st = Database.conn.createStatement();

			ResultSet rs = st.executeQuery(query);		    
			while (rs.next()) {
				  project = new Project();
				  project.setId(rs.getString("id"));
				  project.setName(rs.getString("name"));
				  project.setCodeFiles(getCodeFiles(rs.getString("name")));
				  projects.add(project);
			  }
			//conn.close();
			return  projects;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	public static String getFileInfo(int fileId){
		String resultString = "";
		//Connection conn = getDBConnection();
		
	    String query = "SELECT * from Review where fileID = " + fileId + " ORDER BY reviewId desc";
	    
	    java.sql.Statement st;
		try {
			st = Database.conn.createStatement();
			ResultSet rs = st.executeQuery(query);		    
			while (rs.next()) {
				if (rs.getInt("reviewType") == 1) 
					resultString += "Like, ";
				if (rs.getInt("reviewType") == 2) 
					resultString += "Dislike, ";
				resultString += "Added by :" + rs.getString("addedBy")  + ", " + rs.getString("date") + " \n";
				resultString += "    Comment : " + rs.getString("comment") + " (position : " + rs.getInt("offset") + ", length : " + rs.getInt("length") + " )\n";
				}
			//conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		

		return resultString;
	}
	
	public static String getCurrentTimeStamp() {
	  java.util.Date today = new java.util.Date();
	  SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yy:HH:mm:SS");
      return DATE_FORMAT.format(today);
    }

	public void close() {
		// TODO Auto-generated method stub
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static ArrayList<String> getReviewFeatures(int reviewId) {
		// TODO Auto-generated method stub
		ArrayList<String> list = new ArrayList<String>();
		String sql = "Select value from ReviewFeatureAnalysis where reviewId = " + reviewId 
				+ " order by featureId asc";
		try {
			java.sql.Statement st = Database.conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				list.add(rs.getString("value"));			
			}
			rs.close();
			st.close();		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
