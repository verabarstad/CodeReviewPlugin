package review.staticanalysis;
public class MaxAndMinNumberOfLines {
	private static final int MAX_NUMBER_OF_LINES = 50;
	private static final int MIN_NUMBER_OF_LINES = 5;

	public static void checkCode(String code) {
		String[] lines = code.split("\r\n|\r|\n");
		int count = lines.length;
		System.out.println("Count" + count);
		if (count < MIN_NUMBER_OF_LINES || count > MAX_NUMBER_OF_LINES) {
			StaticAnalysis.staticRating[1] = "0.0";
			System.out.println("Either more or less lines than allowed");
		} else {
			StaticAnalysis.staticRating[1] = "1.0";
		}
	}
}
