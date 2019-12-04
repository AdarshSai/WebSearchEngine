package SpiderPackage;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.ScrollPane;
import java.awt.Window;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class webSearchMain {
	/**
	 * This is our test. It creates a Main class (which creates interface to crawl) and crawls
	 * the web.
	 * 
	 * @param args - not used
	 */

	public static String[] returnList;
//105181433_Adarsh: Main Program Starts here
	public static void main(String[] args) {
		webCrawlerUI window = new webCrawlerUI();

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// window = new webCrawlerUI();
					window.frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		try {

			TimeUnit.MINUTES.sleep(1);
			window.progressBar.setIndeterminate(true);

		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}


		if (window.FlagProceed) {
			returnList = window.returnListOfString;
			File fileLocation;
			webCrawler spider = new webCrawler();
			System.out.println("The html pages as a result are already stored in a folder.");
			System.out.println(
					"Do you want to Crawl once again?(Press yes to crawl and store the webpages once again or No to proceed with the word search with the already prevailing webpages)");
			Scanner scan = new Scanner(System.in);
			String acceptance = returnList[0];
	
			if (acceptance.equalsIgnoreCase("Y") || acceptance.equalsIgnoreCase("yes")) {
				System.out.println("Enter the string that has to be searched");
				String inputString = window.returnListOfString[2];
				try {
					ParseCleanCheck.SpellChecker(inputString);
					if (ParseCleanCheck.sameWord) {
						window.textField_2.setBackground(Color.GREEN);

					} else
						window.textField_2.setBackground(Color.RED);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				window.textField_4.setText(getSynonymsforTheWord(inputString));

				// getMoreInfoAboutString(inputString);
				long startTime = System.currentTimeMillis();
				spider.search(window.returnListOfString[1], inputString);
				File srcFile = new File("extractHtmlFromNewLink");

				extractTextFromHtmlPage("extractHtmlFromNewLink", "extractTextFromNewLink", srcFile);
				window.progressBar.setIndeterminate(false);
				System.out.println("The time taken for crawling and to search word is "
						+ (System.currentTimeMillis() - startTime) + " ms");
				
				window.textField_3.setText(0.001 * (System.currentTimeMillis() - startTime) + " s");
				window.progressBar.setValue(window.progressBar.getMaximum());
				window.progressBar.setIndeterminate(false);
				window.textArea.setText(webSearchAssistance.bestRankedWebPages);
				window.progressBar.setIndeterminate(false);

			
				long startTime1 = System.currentTimeMillis();
				File srcFile1 = new File("extractTextFromNewLink");
				extractUseFulInformationFromPage(window, srcFile1);
				window.textField_5.setText(0.001 * (System.currentTimeMillis() - startTime1) + " s");
				window.textField_6.setText(0.001 * (System.currentTimeMillis() - startTime1) + " s");
				EditDistance.suggestions(inputString, window);
				// buildTST(srcFile, inputString);
			} else if (acceptance.equalsIgnoreCase("N") || acceptance.equalsIgnoreCase("no")) {
				webSearchAssistance helpPage = new webSearchAssistance();
				System.out.println("Enter the string that has to be searched");
				String inputString = window.returnListOfString[2];
				
				// getSynonymsforTheWord(inputString);

				// ParseCleanCheck parse = new ParseCleanCheck();
				try {
				String out=	ParseCleanCheck.SpellChecker(inputString);
					if (out.equalsIgnoreCase(inputString)) {
						window.textField_2.setBackground(Color.GREEN);

					} else
						inputString=out;
					window.textField_2.setText(inputString);
					window.textField_2.setForeground(Color.GREEN);
						window.textField_2.setBackground(Color.RED);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				window.textField_4.setText(getSynonymsforTheWord(inputString));
				// getMoreInfoAboutString(inputString);
				fileLocation = new File("extractHtmlFromLink");
				extractTextFromHtmlPage("extractHtmlFromLink", "extractTextFromHtml", fileLocation);
				
				long stTime = System.currentTimeMillis();
				webSearchAssistance.searchWordFromGivenSetOfHtmlFiles(fileLocation, inputString);
				window.textField_3.setText(0.001 * (System.currentTimeMillis() - stTime) + " s");
				window.progressBar.setValue(window.progressBar.getMaximum());
				window.progressBar.setIndeterminate(false);
				System.out.println(webSearchAssistance.bestRankedWebPages);
				window.textArea.setText(webSearchAssistance.bestRankedWebPages);
				window.textArea.setLineWrap(true);
				
				window.progressBar_1.setIndeterminate(true);
				window.progressBar_2.setIndeterminate(true);
				long startTime1 = System.currentTimeMillis();
				File folder = new File("extractHtmlFromLink");
				extractUseFulInformationFromPage(window, folder);

				window.textField_5.setText(0.001 * (System.currentTimeMillis() - startTime1) + " s");
				window.textField_6.setText(0.001 * (System.currentTimeMillis() - startTime1) + " s");
				
				EditDistance.suggestions(inputString, window);
			}

			// out.close();
			try {
				System.setOut(new PrintStream(new FileOutputStream("output.txt")));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	public static void extractUseFulInformationFromPage(webCrawlerUI window, File folder) {
		PrintStream out = null;

		String emailId = "", phoneNumber = "";
		ArrayList<String> emaildIdandPhoneNumber = new ArrayList<String>();
		// = new File("extractHtmlFromLink");
		String mobileNumberFindPattern = "[\\(]?\\d{3}[\\)]?([-.]?)\\s*\\d{3}\\1\\s*\\d{4}";
		String emailFindPattern = "([a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+)";

		// Create a Pattern object
		Pattern mobilePattern = Pattern.compile(mobileNumberFindPattern);
		Pattern emailPattern = Pattern.compile(emailFindPattern);

		CharSequence line = "";
		// Now create matcher object.
		Matcher mobile = mobilePattern.matcher(line);
		Matcher email = emailPattern.matcher(line);
		try {
			extractUsefulIInsight.findMobileNumberAndEmail(folder, mobile, email);
			emaildIdandPhoneNumber = extractUsefulIInsight.emailIdandPhone;
			for (String s : emaildIdandPhoneNumber) {
				if (getCountofNumbersinAString(s) == 10) {
					phoneNumber += s + "\n";

				} else if (s.contains("@")) {
					emailId += s + "\n";

				}
			}

			window.textArea_1.setText(phoneNumber);
			window.textArea_1.setLineWrap(true);
			window.progressBar_1.setIndeterminate(true);
			window.progressBar_1.setValue(window.progressBar_1.getMaximum());
			window.progressBar_1.setIndeterminate(false);
			// window.textField_5.setLineWrap(true);

			window.textArea_2.setText(emailId);
			window.textArea_2.setLineWrap(true);
			window.progressBar_2.setIndeterminate(true);
			window.progressBar_2.setValue(window.progressBar_2.getMaximum());
			window.progressBar_2.setIndeterminate(false);

		} catch (NullPointerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static int getCountofNumbersinAString(String s) {
		int count = 0;
		for (int i = 0, len = s.length(); i < len; i++) {
			if (Character.isDigit(s.charAt(i))) {
				count++;
			}
		}
		return count++;
	}



	public static void extractTextFromHtmlPage(String originalName, String s, File folder) {
		System.out.println("Extracting Text from the crawled Html Pages");
		// = new File("extractHtmlFromLink");
		File[] listOfFiles = folder.listFiles();
//&& listOfFiles[i].getName().equalsIgnoreCase(webSearchAssistance.list.get(i).toString())
		String wholeString = "";
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				try {
					wholeString += "\n" + listOfFiles[i].getName();

					TextWriter.textFileWriter(originalName, listOfFiles[i].getName(), s);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		System.out.println("Text files generated.");

	}

	public static void MoveFiles() {
		Path temp = null, temp1 = null;
		boolean flag = false;
		File srcFolder = new File("extractHtmlFromLink");
		File dstFolder = new File("extractHtmlFromNewLink");
		File[] listOfNewFiles = srcFolder.listFiles();
		File[] listOfFiles = dstFolder.listFiles();
		int count = 0;
		Path result = null;
		System.out.println(srcFolder.getPath() + srcFolder.getAbsolutePath());
		for (File myFile : listOfFiles) {

			try {
				result = Files.move(Paths.get(myFile.getAbsolutePath()), Paths.get(srcFolder.getAbsolutePath()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (result != null) {
				count += 1;
			}

		}
		if (count == listOfFiles.length) {
			System.out.println("Files moved successfully");
		} else
			System.out.println("No. of files moved:" + count);



	}

	public static String getSynonymsforTheWord(String inputString) {

		return Synonyms.getTheWordForSynonym(inputString);
	}

}
