package review.staticanalysis;

public class MaxLineLength {
	private static double numberOfLines = 0.0;
	private static double numberOfCorrectLines = 0.0;

	private static final int MAX_LINE_LENGTH = 80;

	public static void checkCode(String code) {
		numberOfLines = 0.0;
		numberOfCorrectLines = 0.0;
		String[] lines = code.split("\r\n|\r|\n");
		numberOfLines = lines.length;
		for (String line : lines) {
			if (line.length() < MAX_LINE_LENGTH) {
				numberOfCorrectLines++;
			} else {
				System.out.println(line.length() + " " + line);
			}
		}
		double result = numberOfCorrectLines / numberOfLines;
		if (result == 1) {
			StaticAnalysis.staticRating[4] = "1.0";
			return;
		}
		StaticAnalysis.staticRating[4] = String.valueOf(result);
		System.out.println("Method has lines with innapropriate big length");
	}
}
