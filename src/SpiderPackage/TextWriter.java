package SpiderPackage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.jsoup.Jsoup;

public class TextWriter {
	public static void textFileWriter(String originalName,String fileName, String destination) throws IOException {
		// Read htm file using jsoup
		File myfile = new File(originalName+"\\" + fileName);
		// parsing the html file to a string object
		org.jsoup.nodes.Document doc = Jsoup.parse(myfile, "UTF-8");
		String text = doc.text();

		// Save the content of htm file into the Folder named Data Text in .txt format
		String fileNameWithOutExtension = fileName.replaceFirst("[.][^.]+$", "");
		PrintWriter writeOut = new PrintWriter(destination + "\\" + fileNameWithOutExtension + ".txt");
		writeOut.println(text);
		writeOut.close();
	}

}
