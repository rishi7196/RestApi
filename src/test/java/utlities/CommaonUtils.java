package utlities;

import java.time.LocalDate;
import java.util.Random;

public class CommaonUtils {

	private static final Random random = new Random();

	public static String getRandomName() {
		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		StringBuilder sb = new StringBuilder(5);

		for (int i = 0; i < 5; i++) {
			int index = random.nextInt(letters.length());
			sb.append(letters.charAt(index));
		}

		return sb.toString();
	}

	public static String getJobName() {
		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		StringBuilder sb = new StringBuilder(5);

		for (int i = 0; i < 2; i++) {
			int index = random.nextInt(letters.length());
			sb.append(letters.charAt(index));
		}

		return sb.toString();
	}

	// generate random number
	public static String getRandomNumber() {
		return String.valueOf(100000000 + random.nextInt(900000000));
	}

	// generate current date and future date
	public static String getCurrentDate() {
		return LocalDate.now().toString();
	}

}
