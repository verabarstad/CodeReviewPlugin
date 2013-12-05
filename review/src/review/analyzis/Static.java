package review.analyzis;

import java.util.Random;

import review.staticanalysis.StaticCheck;

public class Static {
	
	public static String testStaticCheck(String input) {
		String result = StaticCheck.runStaticCheck(input);
		return result;
	}
	
	
	public static String getParameters(int ReviewId) {
		
		return "";
	}
	
	public static  String getParameters(String features, String inputText)
	{
		//eg. features are  "F1,F2,F3,F4,F5", generate one value for each feature
		//seperated by ,  nb. 2 decimals  eg. string returned "0.92,0.55,0.18,0.67,0.99,0.12"
       // System.out.println(inputText);
//        
        String testResult = testStaticCheck(inputText);
        System.out.println(testResult);
//	
		Random randomGenerator = new Random();
		String values = "" + (double)randomGenerator.nextInt(100)/100 + ","
				+ (double)randomGenerator.nextInt(100)/100 + ","
				+ (double)randomGenerator.nextInt(100)/100 + ","
				+ (double)randomGenerator.nextInt(100)/100 + ","
				+ (double)randomGenerator.nextInt(100)/100;
		//System.out.println("To analyse : " + values);
		return testResult;
		
	}

	
}


