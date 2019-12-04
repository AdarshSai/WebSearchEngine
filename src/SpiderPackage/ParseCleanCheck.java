package SpiderPackage;

import java.io.*;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

public class ParseCleanCheck {

	static Hashtable<String, String> dictionary;// To store all the words of the
	// dictionary
	static boolean suggestWord;// To indicate whether the word is spelled
								// correctly or not.

	static Scanner urlInput = new Scanner(System.in);
	static Scanner stringInput = new Scanner(System.in);
	// public static String cleanString;
	public static String inputString;
	public static String url = "";
	public static boolean correct = true;
	public static boolean sameWord=false;

	/**
	 * SPELL CHECKER METHOD
	 */
	public static String SpellChecker(String cleanString) throws IOException {
		dictionary = new Hashtable<String, String>();
		System.out.println("Searching for spelling errors ... ");
		String outputWord = null;
		try {
			// Read and store the words of the dictionary
			BufferedReader dictReader = new BufferedReader(new FileReader("words.txt"));

			while (dictReader.ready()) {
				String dictInput = dictReader.readLine();
				String[] dict = dictInput.split("\\s"); // create an array of
														// dictionary words

				for (int i = 0; i < dict.length; i++) {
					// key and value are identical
					dictionary.put(dict[i], dict[i]);
				}
			}
			dictReader.close();
			String user_text = "";

			// Initializing a spelling suggestion object based on probability
			SuggestSpelling suggest = new SuggestSpelling("wordprobabilityDatabase.txt");

			// get user input for correction
			{

				user_text = cleanString;
				String[] words = user_text.split(" ");
				Set<String> wordSet = new HashSet<>();

				int error = 0;

				for (String word : words) {
					if (!wordSet.contains(word)) {
						checkWord(word);

						suggestWord = true;
						outputWord = checkWord(word);
						if (outputWord.equalsIgnoreCase(word))
							sameWord = true;
						if (suggestWord) {

							if (suggest.correct(cleanString).equalsIgnoreCase(cleanString)) {
								System.out.println("The input given word has no spelling mistakes");

							} else {
								System.out.println(
										"Suggestions for " + word + " are:  " + suggest.correct(outputWord) + "\n");
								error++;
							}
						}
					}

					wordSet.add(word);
				}

				if (error == 0) {
					System.out.println("No mistakes found");
				}
				return suggest.correct(outputWord);
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return outputWord;
	
	}

	/**
	 * METHOD TO SPELL CHECK THE WORDS IN A STRING. IS USED IN SPELL CHECKER METHOD
	 * THROUGH THE "WORD" STRING
	 */

	public static String checkWord(String wordToCheck) {
		String wordCheck, unpunctWord;
		String word = wordToCheck.toLowerCase();

		// if word is found in dictionary then it is spelled correctly, so
		// return as it is.

		if ((wordCheck = (String) dictionary.get(word)) != null) {
			suggestWord = false; // no need to ask for suggestion for a correct
									// word.
			return wordCheck;
		}

		// Removing punctuation at end of word and giving it a shot ("." or "."
		// or "?!")
		int length = word.length();

		// Checking for the beginning of quotes(example: "she )
		if (length > 1 && word.substring(0, 1).equals("\"")) {
			unpunctWord = word.substring(1, length);

			if ((wordCheck = (String) dictionary.get(unpunctWord)) != null) {
				suggestWord = false; // no need to ask for suggestion for a
										// correct word.
				return wordCheck;
			} else // not found
				return unpunctWord; // removing the punctuations and returning
		}

		// Checking if "." or ",",etc.. at the end is the problem(example: book.
		// when book is present in the dictionary).
		if (word.length() > 1) {
			if (word.substring(length - 1).equals(".") || word.substring(length - 1).equals(",")
					|| word.substring(length - 1).equals("!") || word.substring(length - 1).equals(";")
					|| word.substring(length - 1).equals(":")) {
				unpunctWord = word.substring(0, length - 1);

				if ((wordCheck = (String) dictionary.get(unpunctWord)) != null) {
					suggestWord = false; // no need to ask for suggestion for a
											// correct word.
					return wordCheck;
				} else {
					return unpunctWord; // removing the punctuation and returning it
										// clean
				}
			}
		}

		// Checking for (!,\,",etc) ... in the problem (example: watch!" when
		// watch is present in the dictionary)

		if (length > 2 && (word.substring(length - 2).equals(",\"") || word.substring(length - 2).equals(".\"")
				|| word.substring(length - 2).equals("?\"") || word.substring(length - 2).equals("!\""))) {
			unpunctWord = word.substring(0, length - 2);

			if ((wordCheck = (String) dictionary.get(unpunctWord)) != null) {
				suggestWord = false; // no need to ask for suggestion for a
										// correct word.
				return wordCheck;
			} else // not found
				return unpunctWord; // removing the inflections and returning
		}

		// If after all of these checks a word could not be corrected, return as
		// a misspelled word.
		return word;
	}
}