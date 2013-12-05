package review.staticanalysis;

public class CheckConstantsUpperCaseNaming {
	private static double NUMBER_OF_CONSTANTS = 0.0;
	private static double NUMBER_OF_CORRECTLY_NAMED_CONSTANTS = 0.0;

	public static void checkCode(String code) {
		NUMBER_OF_CONSTANTS = 0.0;
		NUMBER_OF_CORRECTLY_NAMED_CONSTANTS = 0.0;
		int index = 0;
		String[] words = code.split(" ");
		String[] lines = code.split("\n");
		String firstLine = lines[0];
		String[] wordsFirstLine = firstLine.split(" ");
		while (index < words.length) {
			if (words[index].equalsIgnoreCase("static")
					&& (index > wordsFirstLine.length)) {
				NUMBER_OF_CONSTANTS++;
				if (words[index + 1].equalsIgnoreCase("final")) {
					index += 3;
				} else {
					index += 2;
				}
				System.out.println(words[index]);
				if (!words[index].contains("main")) {
					if (words[index].equals(words[index].toUpperCase())) {
						NUMBER_OF_CORRECTLY_NAMED_CONSTANTS++;
					}
				} else {
					NUMBER_OF_CONSTANTS--;
				}
			}
			index++;
		}

		if (NUMBER_OF_CONSTANTS == 0) {
			StaticAnalysis.staticRating[2] = "1.0";
		} else {
			double result = NUMBER_OF_CORRECTLY_NAMED_CONSTANTS
					/ NUMBER_OF_CONSTANTS;
			System.out.println("result:" + result);
			StaticAnalysis.staticRating[2] = String.valueOf(result);
			if (result == 1.0) {
				return;
			} else {
				System.out
						.println("' has not all constants declared in upper case");
			}
		}

	}
}
