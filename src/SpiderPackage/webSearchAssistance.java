package SpiderPackage;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Character.Subset;
import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Map.Entry;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

public class webSearchAssistance {
	// We'll use a fake USER_AGENT so the web server thinks the robot is a normal
	// web browser.
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
	private List<String> links = new LinkedList<String>();
	private Document htmlDocument;
	private static FileWriter fileWriter;
	private static HashMap<String, Integer> hmap = new HashMap<String, Integer>();
	private static HashMap<String, Integer> hmapForPAging = new HashMap<String, Integer>();
	private static String inputString = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static Dictionary dictionaryForWebSearch = new Hashtable();
	public static ArrayList<Map.Entry<?, Integer>> list;
	public static String bestRankedWebPages = "";

	/**
	 * This performs all the work. It makes an HTTP request, checks the response,
	 * and then gathers up all the links on the page. Perform a searchForWord after
	 * the successful crawl
	 * 
	 * @param url - The URL to visit
	 * @return whether or not the crawl was successful
	 */
	public boolean crawl(String url) {
		try {
			Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
			Document htmlDocument = connection.get();
			System.out.println("Check");
		
			this.htmlDocument = htmlDocument;

			if (connection.response().statusCode() == 200) // 200 is the HTTP OK status code
															// indicating that everything is great.
			{
			//	System.out.println("\n**Visiting** Received web page at " + url);
			}
			if (!connection.response().contentType().contains("text/html")) {
				System.out.println("**Failure** Retrieved something other than HTML");
				return false;
			}
			Elements linksOnPage = htmlDocument.select("a[href]");
			// System.out.println("Found (" + linksOnPage.size() + ") links");
			for (Element link : linksOnPage) {// System.out.println(link.toString());
				this.links.add(link.absUrl("href"));
			}
			saveToDesiredFolder(htmlDocument, url);

			return true;
		} catch (IOException ioe) {
			// We were not successful in our HTTP request
			return false;
		}
	}

	public static void searchWordFromGivenSetOfHtmlFiles(File FileLocation, String inputString) {
		final File folder = FileLocation;
//		System.out.println("Enter the string that u need to search");
//		Scanner in = new Scanner(System.in);
//		String searchString = in.nextLine();
		listFilesForFolder(folder, inputString);
		System.out.println("The size of HashMap is" + hmapForPAging.size());
//		for (String name : hmap.keySet()) {
//
//			int value = hmap.get(name);
//			System.out.println(name + " " + value);
//		}
		// readFromInputFiles(folder, "search");
		// System.out.println("The size of HashMap is" + hmapForPAging.size());
		//System.out.println("Frequency Analysis of the Search Word from the crawled webpages");
		for (String name : hmapForPAging.keySet()) {

			int value = hmapForPAging.get(name);
			//System.out.println(name + " " + value);
		}
		//System.out.println("------------------------------------");

		//System.out.println("Ranking the WebPages with the help of frequency ");
		list = new ArrayList(hmapForPAging.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<?, Integer>>() {

			public int compare(Map.Entry<?, Integer> obj1, Map.Entry<?, Integer> obj2) {
				return obj1.getValue().compareTo(obj2.getValue());
			}
		});

		Collections.reverse(list);

		System.out.println("\n------Best 5 Search Results-----\n");

		int num = 5;
		int j = 1;
		while (list.size() > j && num > 0) {
			bestRankedWebPages += "(" + j + ") " + list.get(j) + " times " + "\n";
			System.out.println("(" + j + ") " + list.get(j) + " times ");
			j++;
			num--;
		}


	}

	public static String getRAnkedWebPAge() {
		return bestRankedWebPages;
	}

	private static void listFilesForFolder(final File folder, String s) {
		String strLine;
		int count = 0;
		ArrayList<String> lines = new ArrayList<String>();
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry, s);


			} else {
				int counter = 0;
				String data = "";
				try {
					BufferedReader Object = new BufferedReader(new FileReader(fileEntry));
					String line = null;

					try {
						while ((line = Object.readLine()) != null) {

							data = data + line;

						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						Object.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} catch (NullPointerException e) {
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				String txt = data;
				BoyerMoore offset1a = new BoyerMoore(s);

				int offset = 0;

				for (int loc = 0; loc <= txt.length(); loc += offset + s.length()) {

					offset = offset1a.search(s, txt.substring(loc));
					// System.out.println("\n");
					if ((offset + loc) < txt.length()) {
						counter++;

					}
				}


				hmapForPAging.put(fileEntry.getName(), counter);



			}
		}
	}

	public static int searchWord(File filePath, String s1) throws IOException {
		int counter = 0;
		String data = "";
		try {
			BufferedReader Object = new BufferedReader(new FileReader(filePath));
			String line = null;

			while ((line = Object.readLine()) != null) {

				data = data + line;

			}
			Object.close();

		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		String txt = data;
		BoyerMoore offset1a = new BoyerMoore(s1);

		int offset = 0;

		for (int loc = 0; loc <= txt.length(); loc += offset + s1.length()) {

			offset = offset1a.search(s1, txt.substring(loc));
			// System.out.println("\n");
			if ((offset + loc) < txt.length()) {
				counter++;
				// System.out.println(s1 + " is at position " + (offset + loc)); // printing
				// position of word
			}
		}

		return counter;
	}

	// System.out.println(fileEntry.getName());

//			try {
//				FileReader reader = new FileReader(fileEntry);
//				BufferedReader buffReader = new BufferedReader(reader);
//				int x = 0;
//				String s;
//				while ((buffReader.readLine()) != null) {
//					s = buffReader.readLine();
//					
//					if ((!(s.isEmpty() || s == null))) {
//						lines.add(s);
//					}
//					x++;
//					// System.out.println("The value of x is" + x);
//
//				}
//				buffReader.close();

	private static void saveToDesiredFolder(Document htmlDocument, String s) {

		boolean flag;
		// C:\\windsor\\Advanced Computing conepts 8547\\e
		String fileLocation = "extractHtmlFromNewLink";
		
		String fileNameWithOutExtension = htmlDocument.baseUri().replaceAll("[^a-zA-Z0-9]", "");

		File stockFile = new File(fileLocation + "\\" + fileNameWithOutExtension + ".html");

		try {
			flag = stockFile.createNewFile();
			fileWriter = new FileWriter(stockFile);
			fileWriter.write(htmlDocument.toString());
			fileWriter.flush();
			fileWriter.close();
			// searchWordFromGivenSetOfHtmlFiles(fileLocation);
		} catch (IOException ioe) {
			System.out.println("Error while Creating File in Java" + ioe);
		}

	
	}

	/**
	 * Performs a search on the body of on the HTML document that is retrieved. This
	 * method should only be called after a successful crawl.
	 * 
	 * @param searchWord - The word or string to look for
	 * @return whether or not the word was found
	 */
	public static String html2text(String html) {
		return Jsoup.parse(html).text();
	}

	public boolean searchForWord(String searchWord) {
		// Defensive coding. This method should only be used after a successful crawl.

		if (this.htmlDocument == null) {
			System.out.println("ERROR! Call crawl() before performing analysis on the document");
			return false;
		}
		System.out.println("Searching for the word " + searchWord + "...");
		String bodyText = this.htmlDocument.body().text();
		System.out.println("Normal Name:" + this.htmlDocument.normalName());
		return bodyText.toLowerCase().contains(searchWord.toLowerCase());
	}

	public static String generateRandomString(int length) {
		SecureRandom random = new SecureRandom();
		final String inputString = "abcdefghijklmnopqrstuvwxyz";
		if (length < 1)
			throw new IllegalArgumentException();

		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {

			// 0-62 (exclusive), random returns 0-61
			int rndCharAt = random.nextInt(inputString.length());
			char rndChar = inputString.charAt(rndCharAt);

			

			sb.append(rndChar);

		}

		return sb.toString();

	}

	public List<String> getLinks() {
		return this.links;
	}

}