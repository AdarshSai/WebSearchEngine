package SpiderPackage;
import java.io.*;
import java.util.*;
import java.util.regex.*;

class SuggestSpelling {

    private final HashMap<String, Integer> DataBaseWords = new HashMap<String, Integer>();

    /**
     * Method that reads the dictionary and checks for probability through word
     * occurrences
     */
    public SuggestSpelling(String file) throws IOException {
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            Pattern p = Pattern.compile("\\w+");
            // Reading the dictionary and updating the probabilistic values
            // accordingly
            for (String temp = ""; temp != null; temp = in.readLine()) {
                Matcher m = p.matcher(temp.toLowerCase());
                while (m.find()) {
                    // This will serve as an indicator to probability of a word
                    DataBaseWords.put((temp = m.group()),
                            DataBaseWords.containsKey(temp) ? DataBaseWords.get(temp) + 1 : 1);
                }
            }
            in.close();
        } catch (IOException e) {
            System.out.println("Uh-Oh Exception occured!");
            e.printStackTrace();
        }
    }

    /**
     * 
     * Method that returns an array containing all possible corrections to the
     * word passed.
     * 
     */
    private final ArrayList<String> edits(String word) {
        ArrayList<String> result = new ArrayList<String>();

        for (int i = 0; i < word.length(); ++i) {
            result.add(word.substring(0, i) + word.substring(i + 1));
        }
        for (int i = 0; i < word.length() - 1; ++i) {
            result.add(word.substring(0, i) + word.substring(i + 1, i + 2) + word.substring(i, i + 1)
                    + word.substring(i + 2));
        }
        for (int i = 0; i < word.length(); ++i) {
            for (char c = 'a'; c <= 'z'; ++c) {
                result.add(word.substring(0, i) + String.valueOf(c) + word.substring(i + 1));
            }
        }
        for (int i = 0; i <= word.length(); ++i) {
            for (char c = 'a'; c <= 'z'; ++c) {
                result.add(word.substring(0, i) + String.valueOf(c) + word.substring(i));
            }
        }
        return result;
    }

    /**
     * 
     * Method that compares input to dictionary words and returns words that are
     * correct while checking for corrections on the others
     * 
     */
    public final String correct(String word) {
        if (DataBaseWords.containsKey(word)) {
            return word; // this is a perfectly safe word.
        }
        ArrayList<String> list_edits = edits(word);
        HashMap<Integer, String> candidates = new HashMap<Integer, String>();

        for (String s : list_edits) // Iterating through the list of all
                                    // possible corrections to the word.
        {
            if (DataBaseWords.containsKey(s)) {
                candidates.put(DataBaseWords.get(s), s);
            }
        }
        // In the first stage of error correction, any of the possible
        // corrections from the list_edits are found in our word database
        // DataBaseWords
        // then we return the one verified correction with maximum probability.
        if (candidates.size() > 0) {
            return candidates.get(Collections.max(candidates.keySet()));
        }
        // In the second stage we apply the first stage method on the possible
        // collections of the list_edits.By the second stage statistics

        for (String s : list_edits) {
            for (String w : edits(s)) {
                if (DataBaseWords.containsKey(w)) {
                    candidates.put(DataBaseWords.get(w), w);
                }
            }
        }

        //return candidates.size() > 0 ? candidates.get(Collections.max(candidates.keySet()))
          //      : "Sorry but no possible corrections found!";
        return candidates.size() > 0 ? "The entered word is correct"
                    : "The entered word has spelling mistake";
    }

   
}