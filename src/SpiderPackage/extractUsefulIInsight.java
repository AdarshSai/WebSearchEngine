package SpiderPackage;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class extractUsefulIInsight {
	public static ArrayList<String> emailIdandPhone = new ArrayList<String>();

	public static void findMobileNumberAndEmail(File file, Matcher m, Matcher e)
			throws NullPointerException, IOException, FileNotFoundException {
		// Read all files under the folder
		String targetFolder = file.getAbsolutePath();

		System.out.println("The number of files in this folder is:" + file.listFiles().length);
		ArrayList<String> rankedList = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {
		
			rankedList.add(webSearchAssistance.list.get(i).toString().substring(0,
					webSearchAssistance.list.get(i).toString().indexOf("=")));
		}
		for (final File fileEntry : file.listFiles()) {
			if (!fileEntry.isDirectory()) {
				File f = new File(fileEntry.getAbsolutePath());
				System.out.println(fileEntry.getName());
				
				BufferedReader br = new BufferedReader(new FileReader(targetFolder + "\\" + f.getName()));

				String line = null;
				System.out.println("----------------");
				System.out.println("File Name: " + f.getName());
				System.out.println("----------------");
				System.out.println();
				System.out.println("Extracting Phone Number and Email Address from " + f.getName());
				// Find phone number and email address from file
				while ((line = br.readLine()) != null) {
					m.reset(line);
					e.reset(line);
					while (m.find()) {
						System.out.println("Phone number: " + m.group());
						emailIdandPhone.add(m.group());
					}
					while (e.find()) {
						System.out.println("Email address: " + e.group());
						emailIdandPhone.add(e.group());
					}
				}
				System.out.println();
				br.close();
			}
			
		}

	
	}
}
