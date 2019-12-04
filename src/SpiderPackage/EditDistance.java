package SpiderPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditDistance {
	// get occurance and position of word
	static ArrayList<String> key = new ArrayList<String>();
	static Hashtable<String, Integer> numbers = new Hashtable<String, Integer>();
	static Scanner sc = new Scanner(System.in);
//	public static Hashtable<String, Integer> numbers = new Hashtable<String, Integer>();

	// Ranking of Web Pages using merge sort
	// Collections.sort by default uses merge sort

	/* using regex to find similar string to pattern */
	public static void suggestions(String pattern, webCrawlerUI window) {
		try {

			// String to be scanned to find the pattern.
			String line = " ";
			String reg = "[\\w]+[@$%^&*()!?{}\b\n\t]*";

			// Create a Pattern object
			Pattern pat = Pattern.compile(reg);
			// Now create matcher object.
			Matcher match = pat.matcher(line);
			int fileNum = 0;
			try {
				File directory = new File("extractTextFromHtml");
				File[] fileArray = directory.listFiles();
				for (File f : fileArray) {
					findWord(f, fileNum, match, pattern);
					fileNum++;
				}

				Set keys = new HashSet();
				Integer value = 1;
				Integer val = 0;
				int counter = 0;
				String suggestion = null;
				System.out.println("\n Suggestions for the given Words are:");
				System.out.println("---------------------");
				for (Map.Entry entry : numbers.entrySet()) {
					if (val == entry.getValue()) {

						break;

					} else {

						if (value == entry.getValue()) {

							if (counter == 0) {
								suggestion = entry.getKey().toString();
								counter++;
							}

							else {
								suggestion += entry.getKey().toString()+"\t";
								counter++;
							}

						}

					}
				}
				window.textField_7.setText(suggestion);
				System.out.println(suggestion);
			} catch (Exception e) {
				System.out.println("Exception:" + e);
			} finally {

			}

		} catch (Exception e) {

		}
	}

	// finds strings with similar pattern and calls edit distance() on those strings
	public static void findWord(File sourceFile, int fileNumber, Matcher match, String str)
			throws FileNotFoundException, ArrayIndexOutOfBoundsException {
		try {
			int i = 0;
			BufferedReader rederObject = new BufferedReader(new FileReader(sourceFile));
			String line = null;

			while ((line = rederObject.readLine()) != null) {
				match.reset(line);
				while (match.find()) {
					key.add(match.group());
				}
			}
			rederObject.close();
			for (int p = 0; p < key.size(); p++) {
				numbers.put(key.get(p), editDistance(str.toLowerCase(), key.get(p).toLowerCase()));
			}
		} catch (Exception e) {
			System.out.println("Exception:" + e);
		}

	}

	// Uses Edit distance to compare nearest distance between keyword and similar
	// patterns obtained from regex
	public static int editDistance(String str1, String str2) {
		int len1 = str1.length();
		int len2 = str2.length();

		int[][] array = new int[len1 + 1][len2 + 1];

		for (int i = 0; i <= len1; i++) {
			array[i][0] = i;
		}

		for (int j = 0; j <= len2; j++) {
			array[0][j] = j;
		}

		// iterate though, and check last char
		for (int i = 0; i < len1; i++) {
			char c1 = str1.charAt(i);
			for (int j = 0; j < len2; j++) {
				char c2 = str2.charAt(j);

				if (c1 == c2) {

					array[i + 1][j + 1] = array[i][j];
				} else {
					int replace = array[i][j] + 1;
					int insert = array[i][j + 1] + 1;
					int delete = array[i + 1][j] + 1;

					int min = replace > insert ? insert : replace;
					min = delete > min ? min : delete;
					array[i + 1][j + 1] = min;
				}
			}
		}

		return array[len1][len2];
	}

}
