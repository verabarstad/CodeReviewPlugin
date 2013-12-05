package review.model;

import java.util.ArrayList;

public class AnalysisResults {
	
	String features = "";
	private int TruePositives;
	private int FalseNegatives;
	private int TrueNegatives;
	private int FalsePositives;
	private ArrayList<FeatureResult> aFeatureResult = null;
	
	public AnalysisResults (String features) {
		this.features = features;
		this.TruePositives = 0;
		this.TrueNegatives = 0;
		this.FalseNegatives = 0;
		this.FalsePositives = 0;
		//aFeatureResult = new ArrayList<FeatureResult>();
		//this.humanReviewType = humanReviewType;
	}
	
	int countMethods;
	int correctClassified;
	
	public double getCorrectness() {
		return (double)correctClassified/(double)countMethods;
	}
	
	public void setFeatures(String features) {
		this.features = features;
	}
	
	public String getFeatures() {
		return features;
	}
	
	public void addFeatureResult(FeatureResult featureResult) {
	  aFeatureResult.add(featureResult);
	}
	
	public int getCountMethods () {
		return countMethods;
	}
	
	public void incCountMethods() {
		this.countMethods += 1;
	}
	
	public void incCorrectClassified() {
		this.correctClassified += 1;
	}
	
	public int getCorrectClassified() {
		return correctClassified;
	}
	
	public int getTruePositives() {
		return TruePositives;
	}
	
	public int getFalseNegatives() {
		return FalseNegatives;
	}
	
	public int getTrueNegatives() {
		return TrueNegatives;
	}
	
	public int getFalsePositives() {
		return FalsePositives;
	}
	
	public void incTruePositives() {
		this.TruePositives += 1;
	}
	
	public void setTruePositives(int val) {
		this.TruePositives = val;
	}
	
	public void incFalseNegatives() {
		this.FalseNegatives += 1;
	}
	
	public void setFalseNegatives(int val) {
		this.FalseNegatives = val;
	}
	
	public void incTrueNegatives() {
		this.TrueNegatives += 1;
	}
	
	public void setTrueNegatives(int val) {
		this.TrueNegatives = val;
	}
	
	public void incFalsePositives() {
		this.FalsePositives += 1;
	}
	
	public void setFalsePositives(int val) {
		this.FalsePositives = val;
	}

}
