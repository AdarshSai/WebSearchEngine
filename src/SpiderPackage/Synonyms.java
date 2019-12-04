package SpiderPackage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Synonyms {

	private final static String USER_AGENT = "Mozilla/5.0";
	public static String synonyms = "";

	public static String getTheWordForSynonym(String wordToSearch) {

		String urlForDictionary = "https://api.datamuse.com/words?rel_syn=" + wordToSearch;
		HttpURLConnection con = null;
		try {
			URL obj = new URL(urlForDictionary);
			con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", USER_AGENT);

			int responseCode = con.getResponseCode();
			// For Testing purpose to check whether we send request and receive Response
			// Code:
			// System.out.println("\nSending request to: " + urlForDictionary);
			// System.out.println("JSON Response: " + responseCode + "\n");
		} catch (Exception e) {
			e.getMessage();
		}
		// ordering the response
		StringBuilder response = null;
		try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
			String inputLine;
			response = new StringBuilder();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		try {
			// converting JSON array to ArrayList of words
			// The response words from JSON array are converted into ArrayList of Words
			ArrayList<Word> words = mapper.readValue(response.toString(),
					mapper.getTypeFactory().constructCollectionType(ArrayList.class, Word.class));

			System.out.println("Synonym word of '" + wordToSearch + "':");
			if (words.size() > 0) {
				for (Word word : words) {
					synonyms += (words.indexOf(word) + 1) + ". " + word.getWord() + "";

					System.out.println(synonyms);
				}
			} else {
				System.out.println("none synonym word!");
			}
		} catch (IOException e) {
			e.getMessage();
		}
		return synonyms;

	}

// word and score attributes are from DataMuse API
	static class Word {
		private String word;
		private int score;

		public String getWord() {
			return this.word;
		}

		public int getScore() {
			return this.score;
		}
	}

}
