package review.staticanalysis;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class StaticCheck {

//	public static void main(String[] args) throws IOException {
//		InputStream is = null;
//		BufferedReader br = null;
//		String code = "";
//		try {
//
//			is = System.in;
//			br = new BufferedReader(new InputStreamReader(is));
//
//			String line = null;
//
//			while ((line = br.readLine()) != null) {
//				if (line.equalsIgnoreCase("quit")
//						|| line.equalsIgnoreCase("}quit")) {
//					// System.out.println("code : " + code);
//					runTestStaticCheck(code);
//					for (String s : StaticAnalysis.staticRating) {
//						System.out.print(s + ",");
//					}
//					break;
//				}
//				code += line + "\n";
//				// System.out.println("Line entered : " + line);
//			}
//
//		} catch (IOException ioe) {
//			System.out.println("Exception while reading input " + ioe);
//		} finally {
//			// close the streams using close method
//			try {
//				if (br != null) {
//					br.close();
//				}
//			} catch (IOException ioe) {
//				System.out.println("Error while closing stream: " + ioe);
//			}
//
//		}
//
//	}

	private static void runTestStaticCheck(String code) {
		CassesWhenFileNotFoundException.checkCode(code);
		CheckConstantsUpperCaseNaming.checkCode(code);
		MaxAndMinNumberOfLines.checkCode(code);
		MaxLineLength.checkCode(code);
		MethodNameCorrect.runCheck(code);
	}

	public static String runStaticCheck(String code)
	{
		CassesWhenFileNotFoundException.checkCode(code);
		CheckConstantsUpperCaseNaming.checkCode(code);
		MaxAndMinNumberOfLines.checkCode(code);
		MaxLineLength.checkCode(code);
		MethodNameCorrect.runCheck(code);
		String resultArray[] = StaticAnalysis.staticRating;
		String result = "";
		for (int i = 0; i < resultArray.length; i++) {
			result += resultArray[i];
			if (i != resultArray.length - 1) {
				result += ",";
			}
		}
		return result;
	}
}
