package review.model;

public class AutomaticReviewResult {
  private String features;
  private int type;
  private double probability;
  
  
  public AutomaticReviewResult(String features, int type, double probability) {
	  this.features = features;
	  this.type = type;
	  this.probability = probability;  
  }
  
  public String getFeatures() {
	  return features;
  }
  
  public int getType(){
	  return type;
  } 
  
  public double getProbability() {
	  return probability;
  }
}
