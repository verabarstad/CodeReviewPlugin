package review.model;

import java.util.ArrayList;

public class AutomaticReview {

    private int fileId;
    int offset;
	

  private int length;
 // private int automaticReviewType;
  private int humanReviewType;   //NB, human review type for this method, 0 is no review in HR
  private String comment;
  private String addedBy;
  private String date;
  private String selection;
  private ArrayList<AutomaticReviewResult> automaticReviewResult = new ArrayList<AutomaticReviewResult>();
  
  public AutomaticReview(int fileId,int offset, int length) {
	 this.fileId = fileId;
	 this.offset = offset;
	 this.length = length;
	// this.automaticReviewType = type;
//	 this.comment = comment;
//	 this.addedBy = addedBy;
//	 this.date = date;	 
  }
  
  public ArrayList<AutomaticReviewResult> getAutomaticReviewResults() {
	  return automaticReviewResult;
  }
  
  public void addAutomaticReviewResult(AutomaticReviewResult autoResult) {
	  automaticReviewResult.add(autoResult);
  }
  
public int getHumanReviewType() {
	return humanReviewType;
}

public void setHumanReviewType(int type) {
	this.humanReviewType = type;
}

public int getOffset(){
    return this.offset;
  }
  public void setOffset(int offset) {
	  this.offset = offset;
  }
  
  public int getLength(){
	    return this.length;
 }
  
 public void setLength(int length) {
   this.length = length;
 }
  
//  public int getAutomaticReviewType() {
//	  return this.automaticReviewType; 
//  }
//  
//  public void setAutomaticREviewType(int type){
//	  this.automaticReviewType = type;
//  }
  
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

public String getSelection() {
	return selection;
}

public void setSelection(String selection) {
	this.selection = selection;
}
    
}
