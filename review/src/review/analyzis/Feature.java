package review.analyzis;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;



import review.model.CodeFile;
import review.model.Database;

public class Feature {
  
  
  public static ArrayList<FeatureValue> featureValues = new ArrayList<FeatureValue>();
  
  
  public Feature()  {
	  //TODO read from database 
	  //select featureId,value,limit_from,limit_to from featureValues 
	  FeatureValue fValue;
	  Connection conn = Database.getDBConnection();
	  
	    String query = "SELECT featureId,value,limit_from,limit_to from FeatureValue ";
	    		query += "order by featureId, value, limit_from, limit_to";    
	    java.sql.Statement st;
		try {
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);		    
			while (rs.next()) {
				fValue = new FeatureValue(rs.getString("featureId"),rs.getString("value"),
						rs.getInt("limit_from"),rs.getInt("limit_to"));
				featureValues.add(fValue);
			}
			System.out.println("FatureValues added from db");
			st.close();
			conn.close();
		} catch (SQLException e) {
			  //if db go wrong add som dummy values
			  fValue = new FeatureValue("F1","N",0,50);
			  featureValues.add(fValue);
			  fValue = new FeatureValue("F1","Y",51,100);
			  featureValues.add(fValue);
			  fValue = new FeatureValue("F2","N",0,50);
			  featureValues.add(fValue);
			  fValue = new FeatureValue("F2","Y",51,100);
			  featureValues.add(fValue);
			  fValue = new FeatureValue("F3","N",0,50);
			  featureValues.add(fValue);
			  fValue = new FeatureValue("F3","Y",51,100);
			  featureValues.add(fValue);
			  fValue = new FeatureValue("F4","N",0,50);
			  featureValues.add(fValue);
			  fValue = new FeatureValue("F4","Y",51,100);
			  featureValues.add(fValue);
			  fValue = new FeatureValue("F5","N",0,50);
			  featureValues.add(fValue);
			  fValue = new FeatureValue("F5","Y",51,100);
			  featureValues.add(fValue);
			e.printStackTrace();
		}
	  

  }
  public  static FeatureValue getFeatureValue(String feature, double val) {
	  FeatureValue fValue = null;
	  String funnet = "";
	  String sFeature;
	  Integer limit_from;
	  Integer limit_to;
	  
	  
	  Iterator<FeatureValue> i = featureValues.iterator();
	  while (i.hasNext()) {
		  fValue = i.next();
		  
		  sFeature = fValue.feature;
		  limit_from = fValue.limit_from;
		  limit_to = fValue.limit_to;
		  
		//  System.out.println ("F : " + sFeature + "L " + limit_from + "-" + limit_to);
		  if ( sFeature.equals(feature) &&  (val >= limit_from)  && (val <= limit_to) ) {
			  funnet = "true" ;
			  break;
		  }
		  
	  }
	  if (funnet.equals("true"))
		  return  fValue;
	  else
	    return null;
	 
  }
  public class FeatureValue {
	  public String feature;
	  public String value;
	  public int limit_from;
	  public int limit_to;
	  
	  
	  public String getFeature() {
		  
		  return feature;
	  }
	  
	  public FeatureValue(String feature, String value, int limit_from, int limit_to){
		  this.feature = feature;
		  this.value = value;
		  this.limit_from = limit_from;
		  this.limit_to = limit_to;
	  }

	public String getValue() {
		return value;
	}
  }
  
  
}
