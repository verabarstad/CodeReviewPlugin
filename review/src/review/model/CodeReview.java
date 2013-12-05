package review.model;

import java.io.Serializable;
import java.util.Date;

public class CodeReview implements Serializable{
	private static final long serialVersionUID = -7246189775530050254L;
    private int fileId;
	private String fileName;
	private int offset;
	
  private int reviewId;
  private int length;
  private int type;
  private String comment;
  private String addedBy;
  private String date;
  private String selection;
  
  public CodeReview(int fileId,int offset, int length, int type, String comment, String addedBy, String date) {
	 this.fileId = fileId;
	 this.offset = offset;
	 this.length = length;
	 this.type = type;
	 this.comment = comment;
	 this.addedBy = addedBy;
	 this.date = date;
	 
	 
  }

  public void setReviewId(int val) {
	  this.reviewId = val;
  }
  
  public int getReviewId() {
	 return reviewId; 
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
  
  public int getType() {
	  return this.type; 
  }
  
  public void setType(int type){
	  this.type = type;
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




public String getSelection() {
	return selection;
}



public void setSelection(String selection) {
	this.selection = selection;
}
    
}
