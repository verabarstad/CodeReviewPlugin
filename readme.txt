
The environment variable CODE_REVIEW_DATA must be defined before using the plug-in. This variable must contain the path to the folder that contains the XML file that contains user data. 
Example : CODE_REVIEW_DATA = “c:\drop-box\codereview\data”
The users are defined in properties.XML. To add a new user include a new entry key in the XML file. This file has to be stored in the folder defined by CODE_REVIEW_DATA.
properties.xml : 
<?xml version="1.0" encoding="UTF-8" standalone="no"?> 
<!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd"> 
<properties>
<comment>Users and passwords</comment> 
 <entry key="Vladimir Shulgin">123</entry> 
 <entry key="Vera Barstad">123</entry> 
</properties>

The connection strings for the MySql database has to be specified in the file review.model/Database.java.
example :
private static String dbName = "cr"; 	
private static String dbDriver= "com.mysql.jdbc.Driver"; 	
private static String dbUserName = "root"; 	
private static String dbPassword = "root"; 	
private static String host = "localhost";