package review.model;

public class FeatureResult {
		String features;
		int countMetods;
		int countCorrect;
		
		Double result;
		int type;
		
		public FeatureResult (String features, Double result, int type) {
			this.features = features;
			;
			
			this.result = result;
			this.type = type;
		}
		
		public int getType(){
			return type;
		}
		public String getFeatures() {
			return features;
		}
		
		public void setFeatures(String str) {
			this.features = str;
		}
		
		public Double getResult() {
			return result;
		}
		
		public void setResult(Double val){
			this.result = val;
		}
		
		
		
		
	}