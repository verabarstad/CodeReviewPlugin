package review.classification;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import review.model.AutomaticReviewResult;
import review.model.Database;
import review.model.Parameters;

public class NaiveBayes {
	private static int intTotal;
	private static int intLikes;
	private static int intDislikes;
	private static Double dPrioLike;
	private static Double dPrioDislike;
	private static String db = "Review";
	private static HashMap<String,Integer> countLikes;
	private static HashMap<String,Integer> countDislikes;
	
	//private static Database database = new Database();
	//private static Connection conn = database.getDBConnection();
    
	
	
	public NaiveBayes(int leaveOutFileId) {
	   //initiate counters
	   Parameters parameters = new Parameters();
	  Database.setInitialParameters(leaveOutFileId, parameters);
	   intTotal = parameters.getTotal();
	   intDislikes = parameters.getDislikes();
	   intLikes = parameters.getLikes();
	   
//	   setTotal(leaveOutFileId);
//	   setLikes(leaveOutFileId);
//	   setDislikes(leaveOutFileId);
	   countLikes = getMap(leaveOutFileId,1);
	   countDislikes = getMap(leaveOutFileId,2);
	   
	   dPrioLike = (double)	intLikes/(double)intTotal;
	   dPrioDislike = (double)	intDislikes/(double)intTotal;
	   
	}
	
	public NaiveBayes() {
		   //initiate counters'
		  
		   setTotal();
		   setLikes();
		   setDislikes();
		   int fileId = 0;
		   countLikes = getMap(0,1);
		   countDislikes = getMap(0,2);
		   dPrioLike = (double)	intLikes/(double)intTotal;
		   dPrioDislike = (double)	intDislikes/(double)intTotal;
		   
		}
	
	public static HashMap<String,Integer> getMapLikes() {
		return countLikes;		
	}
	
	public static Double getPrioDislike() {
		return dPrioDislike;
	}
	
	public static Double getPrioLike() {
		return dPrioLike;
	}
	
	
	public static HashMap<String,Integer> getMapDislikes() {
		return countDislikes;		
	}
	
 
	public static void setTotal() {
		try {
			String sql = "select count(*) count from " 
					+ "(SELECT ra.fileId, ra.reviewId, ra.reviewType from " 
					  + "ReviewFeatureAnalysis ra group by ra.fileId, ra.reviewId)  r " 
					+ "where r.reviewType = 1 or r.reviewType = 2";
			System.out.println(sql);
			intTotal = Database.getInteger(sql);
		     } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
	}
	
//	public static void setInitialParameters(int leaveOutFileId) {
//		try {
//			String sql = "select * from " 
//					+ "(SELECT ra.fileId, ra.reviewId, ra.reviewType from " 
//					  + "ReviewFeatureAnalysis ra group by ra.fileId, ra.reviewId)  r " 
//					+ "where fileId <> " + leaveOutFileId ;
//			System.out.println("Parsing fileid : " + leaveOutFileId);
//			//Connection conn = Database.getDBConnection();
//			java.sql.Statement st = conn.createStatement();
//
//			ResultSet rs = st.executeQuery(sql);
//			while (rs.next()) {
//				 intTotal = intTotal + 1;
//				 if (rs.getInt("reviewType") == 1)
//					 intLikes = intLikes + 1;
//				 else 
//					 intDislikes = intDislikes + 1;
//				 
//			  }
//			rs.close();
//			conn.close();
//			//intTotal = db.getInteger(sql);
//			System.out.println(sql);
//		     } catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		    }
//	}
	public static void setTotal(int leaveOutFileId) {
		try {
			String sql = "select count(*) count from " 
					+ "(SELECT ra.fileId, ra.reviewId, ra.reviewType from " 
					  + "ReviewFeatureAnalysis ra group by ra.fileId, ra.reviewId)  r " 
					+ "where r.reviewType = 1 or r.reviewType = 2 and fileId <> " + leaveOutFileId ;
			intTotal = Database.getInteger(sql);
			System.out.println(sql);
		     } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
	}
	
	public static void setLikes() {
		try {
			String sql = "select count(*) count from " 
					+ "(SELECT ra.fileId, ra.reviewId, ra.reviewType from " 
					  + "ReviewFeatureAnalysis ra group by ra.fileId, ra.reviewId)  r " 
					+ "where r.reviewType = 1";
			System.out.println(sql);
			intLikes = Database.getInteger(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void setLikes(int leaveOutFileId) {
		try {
			String sql = "select count(*) count from " 
					+ "(SELECT ra.fileId, ra.reviewId, ra.reviewType from " 
					  + "ReviewFeatureAnalysis ra group by ra.fileId, ra.reviewId)  r " 
					+ "where r.reviewType = 1 and fileId <> " + leaveOutFileId;
			System.out.println(sql);
			intLikes = Database.getInteger(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void setDislikes(int leaveOutFileId) {
		try {
			String sql = "select count(*) count from " 
					+ "(SELECT ra.fileId, ra.reviewId, ra.reviewType from " 
					  + "ReviewFeatureAnalysis ra group by ra.fileId, ra.reviewId)  r " 
					+ "where r.reviewType = 2 and fileId <> " + leaveOutFileId;
			intDislikes = Database.getInteger(sql);
			System.out.println(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void setDislikes() {
		try {
			String sql = "select count(*) count from " 
					+ "(SELECT ra.fileId, ra.reviewId, ra.reviewType from " 
					  + "ReviewFeatureAnalysis ra group by ra.fileId, ra.reviewId)  r " 
					+ "where r.reviewType = 2";
			System.out.println(sql);
			intDislikes = Database.getInteger(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static int getTotal() {
	  if (intTotal == 0 ) setTotal(); 
		return intTotal;
	}
	public static int getLikes() {
	  if (intLikes == 0 ) setLikes(); 
		return intLikes;
	}
	public static int getDislikes() {
		if (intDislikes == 0 ) setDislikes(); 
		return intDislikes;
	}
	
	public static Integer getLikes(String feature, String value) {
		Integer count = countLikes.get(feature + "_" + value);
		if (count == null) 
			count = 0;
		return count;
	}
	
	public static Integer getDislikes(String feature, String value) {
		Integer count = countDislikes.get(feature + "_" + value);
		if (count == null) 
			count = 0;
		return count;
	}
	public static String classify(HashMap<String,String> map) {	 

		ArrayList<Double> dLikes = new ArrayList<Double>();
		ArrayList<Double> dDislikes = new ArrayList<Double>();
		Double dProbLikeLog = (double) 0;
		Double dProbDislikeLog = (double) 0;
		
//		System.out.println("Map : " + map);
//		System.out.println("Countlikes : " + countLikes);
//		System.out.println("CountDislikes : " + countDislikes);
		
		Iterator<String> keySetIterator = map.keySet().iterator();
		Integer countLikes;
		Integer nrLikes;
	
		
        while(keySetIterator.hasNext()){
		   String key = keySetIterator.next();
		   countLikes = getLikes(key,map.get(key));
		   nrLikes = intLikes;
		   //System.out.println ("countLikes : " + countLikes + ", nrLikes : " + nrLikes);
		   
		   double d =   Math.log((double)getLikes(key,map.get(key))/(double)intLikes);
		   dLikes.add(d);	
		   dProbLikeLog += d;
		   d =   Math.log((double)getDislikes(key,map.get(key))/(double)intDislikes);
		   dDislikes.add(d) ;
		   dProbDislikeLog += d;		
//		   System.out.println("key: " + key + " value: " + map.get(key) 
//				   + " L: " + getLikes(key,map.get(key))
//				   + " D: " + getDislikes(key,map.get(key)));		   
		 }
		dProbLikeLog = Math.exp(dProbLikeLog);
		
        dProbLikeLog *= getPrioLike();
        dProbDislikeLog = Math.exp(dProbDislikeLog);
    
    	
        dProbDislikeLog *= getPrioDislike();
        Double prosLike = dProbLikeLog*100/(dProbLikeLog + dProbDislikeLog);
        prosLike = (double)Math.round(prosLike*100)/100;
        Double prosDisLike = dProbDislikeLog*100/(dProbLikeLog + dProbDislikeLog);
        prosDisLike = (double)Math.round(prosDisLike*100)/100;
        System.out.println("Like : " + dProbLikeLog + ", Dislike : " + dProbDislikeLog);
        if (dProbLikeLog >= dProbDislikeLog) {
        	System.out.println("This is a like, : pros : " + prosLike );
        	return "Like, " + prosLike + "%";
        }
        else {
        	System.out.println("This is a Dislike, : pros : " + prosDisLike);
        	return "DisLike," + prosDisLike + "%";
		}
		
		
	}
	
	public static AutomaticReviewResult classify(HashMap<String,String> map, String features) {	 
        AutomaticReviewResult automaticReviewResult ;
		ArrayList<Double> dLikes = new ArrayList<Double>();
		ArrayList<Double> dDislikes = new ArrayList<Double>();
		Double dProbLikeLog = (double) 0;
		Double dProbDislikeLog = (double) 0;
		
		System.out.println("Map : " + map);
		System.out.println("Countlikes : " + countLikes);
		System.out.println("CountDislikes : " + countDislikes);
		
		Iterator<String> keySetIterator = map.keySet().iterator();
		Integer countLikes;
		Integer nrLikes;
	
		
        while(keySetIterator.hasNext()){
		   String key = keySetIterator.next();
		   countLikes = getLikes(key,map.get(key));
		   nrLikes = intLikes;
		   //System.out.println ("countLikes : " + countLikes + ", nrLikes : " + nrLikes);
		   
		   double d =   Math.log((double)getLikes(key,map.get(key))/(double)intLikes);
		   dLikes.add(d);	
		   dProbLikeLog += d;
		   d =   Math.log((double)getDislikes(key,map.get(key))/(double)intDislikes);
		   dDislikes.add(d) ;
		   dProbDislikeLog += d;		
//		   System.out.println("key: " + key + " value: " + map.get(key) 
//				   + " L: " + getLikes(key,map.get(key))
//				   + " D: " + getDislikes(key,map.get(key)));		   
		 }
		dProbLikeLog = Math.exp(dProbLikeLog);
		
        dProbLikeLog *= getPrioLike();
        dProbDislikeLog = Math.exp(dProbDislikeLog);
    
    	
        dProbDislikeLog *= getPrioDislike();
        Double prosLike = dProbLikeLog*100/(dProbLikeLog + dProbDislikeLog);
        prosLike = (double)Math.round(prosLike*100)/100;
        Double prosDisLike = dProbDislikeLog*100/(dProbLikeLog + dProbDislikeLog);
        prosDisLike = (double)Math.round(prosDisLike*100)/100;
        System.out.println("Like : " + dProbLikeLog + ", Dislike : " + dProbDislikeLog);
        if (dProbLikeLog >= dProbDislikeLog) {
        	automaticReviewResult = new AutomaticReviewResult(features,1,prosLike);
        	System.out.println("This is a like, : pros : " + prosLike );
        	return automaticReviewResult;
        }
        else {
        	automaticReviewResult = new AutomaticReviewResult(features,2,prosDisLike);
        	System.out.println("This is a Dislike, : pros : " + prosDisLike);
        	return automaticReviewResult;
		}
		
		
	}
	
	//--------------------------------------------------
	private HashMap<String, Integer> getMap(int leaveOutFileId,int Type) {
	//---------------------------------------------------
		//nb this not include leave one out
		String sql = "Select reviewType, featureId, value, count(*) count  from ReviewFeatureAnalysis ";
			if (leaveOutFileId != 0) 
				sql= sql + " where fileId <> " + leaveOutFileId;
			
			sql = sql + " group by reviewType, featureId, value";
		//System.out.println("GetMap : Sql : " + sql);
		String key = "";
		Integer value ;
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		Connection conn = Database.getDBConnection();
		
			java.sql.Statement st;
	
	       
	        	try {
					st = conn.createStatement();
					ResultSet rs = st.executeQuery(sql);
		        	while (rs.next()) {
		        		int type = rs.getInt("reviewType");
		        		if (type == Type) {
		        		key = rs.getString("featureId") + "_" + rs.getString("value");
		        		value = rs.getInt("count");
		        		//System.out.println("Key " + key + " value : " + value);
		        		
		        		map.put(key,new Integer(value));
		        		}
		              
		            }
		        	
		        	st.close();
		        	return map;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return map;
				}
	}

	
}
