package review.staticanalysis;
public class MethodNameCorrect {
	private static boolean isNameCorrecly = true;

	public static void runCheck(String code) {
		String[] lines = code.split("\n");
		String[] firstLine = lines[0].split(" ");
		String methodName = "";
		for (int i = 0; i < firstLine.length; i++) {
			if (firstLine[i].contains("(")) {
				methodName = firstLine[i].substring(0,
						firstLine[i].indexOf("("));
				break;
			}
		}
		System.out.println(methodName);
		if (!methodName.equalsIgnoreCase("main")) {
			isNameCorrecly = methodName.matches("^[a-z][a-zA-Z0-9]*");
		} else {
			StaticAnalysis.staticRating[3] = "1.0";
			return;
		}
		if (isNameCorrecly) {
			StaticAnalysis.staticRating[3] = "1.0";
			return;
		}
		StaticAnalysis.staticRating[3] = (isNameCorrecly == true) ? "1.0"
				: "0.0";
		System.out.println("Method hasn't correct name");
	}
}
