package SpiderPackage;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class webCrawler {
	private HashSet<String> links;
	private static final int MAX_PAGES_TO_SEARCH = 50;
	private Set<String> pagesVisited = new HashSet<String>();
	private List<String> pagesToVisit = new LinkedList<String>();

	/**
	 * Our main launching point for the Spider's functionality. Internally it
	 * creates spider legs that make an HTTP request and parse the response (the web
	 * page).
	 * 
	 * @param url        - The starting point of the spider
	 * @param searchWord - The word or string that you are searching for
	 */
	public void search(String url, String searchWord) {
		while (this.pagesVisited.size() < MAX_PAGES_TO_SEARCH) {
			String currentUrl;
			webSearchAssistance leg = new webSearchAssistance();
			if (this.pagesToVisit.isEmpty()) {
				currentUrl = url;
				this.pagesVisited.add(url);
			} else {
				currentUrl = this.nextUrl();
			}
			System.out.println("Current URL:" + currentUrl);
			leg.crawl(currentUrl); 
			this.pagesToVisit.addAll(leg.getLinks());
		}
		File fileLocation = new File("extractHtmlFromLink");
		webSearchAssistance.searchWordFromGivenSetOfHtmlFiles(fileLocation, searchWord);
	//	System.out.println("\n**Done** Visited " + this.pagesVisited.size() + " web page(s)");
	}

	/**
	 * Returns the next URL to visit (in the order that they were found). We also do
	 * a check to make sure this method doesn't return a URL that has already been
	 * visited.
	 * 
	 * @return
	 */
	private String nextUrl() {
		String nextUrl;
		do {
			nextUrl = this.pagesToVisit.remove(0);
		} while (this.pagesVisited.contains(nextUrl));
		this.pagesVisited.add(nextUrl);
		return nextUrl;
	}
}