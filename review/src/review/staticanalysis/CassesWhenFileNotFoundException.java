package review.staticanalysis;
public class CassesWhenFileNotFoundException {

	public static void checkCode(String code) {
		if (code.contains("FileInputStream")
				|| code.contains("FileOutputStream")
				|| code.contains("RandomAccessFile") || code.contains("File")) {
			if (!code.contains("FileNotFoundException")) {
				// Add exception to array of exceptions
				StaticAnalysis.staticRating[0] = "0.0";
				System.out
						.println("Trying to reads from file, but don't have FileNotFoundException");
			} else {
				StaticAnalysis.staticRating[0] = "1.0";
			}
		} else {
			StaticAnalysis.staticRating[0] = "1.0";
		}
	}
}