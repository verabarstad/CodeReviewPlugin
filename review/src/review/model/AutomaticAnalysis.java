package review.model;

import java.util.ArrayList;

public class AutomaticAnalysis {

    private int analysisId;
	private ArrayList<AutomaticReview>  automaticReviews = new ArrayList<AutomaticReview>();
	
	
  private int fileId;
  private String comment;
  private String addedBy;
  private String date;
  private Double result;
  ArrayList<AnalysisResults> analysisResults = new ArrayList<AnalysisResults>();
  
  public AutomaticAnalysis(int fileId, String comment, String addedBy, String date) {
	 this.fileId = fileId;
	 this.comment = comment;
	 this.addedBy = addedBy;
	 this.date = date;	 
  }
  
  public void addResult(AnalysisResults aResult) {
	  analysisResults.add(aResult);
  }
  
  public ArrayList<AnalysisResults> getAnalysisResults() {
	  return analysisResults;
  }
  
  public AnalysisResults getResult (String features) {
	  for (int i = 0; i < analysisResults.size(); i++){
		  if (analysisResults.get(i).getFeatures().equals(features)) {
			  return analysisResults.get(i);
		  }
	  }
	  
	  return null;
  }
  
  public int getFileId() {
	  return this.fileId;
  }
  
  public void setFileId(int fileId) {
	  this.fileId = fileId;
  }
  
  public String getComment() {
	  return this.comment;
  }
  
  public void setComment(String comment) {
	  this.comment = comment;
  }
  
  
  public String getAddedBy() {
	  return this.addedBy;
  }
  
  public void setAddedBy(String addedBy) {
	  this.addedBy = addedBy;
  }

public String getDate() {
	return date;
}

public void setDate(String date) {
	this.date = date;
}

public Double getResult() {
	return result;
}

public void setResult(Double result) {
	this.result = result;
}

public void addAutomaticReview(AutomaticReview aReview) {
	automaticReviews.add(aReview);
}


    

}
