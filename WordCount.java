import java.util.List;
import java.util.Comparator;
import java.util.Collections;
import java.util.Scanner;
import java.util.Map;
import java.util.TreeMap;
import java.util.Iterator;
import java.io.File;
import java.io.FileNotFoundException;
/**
 * Reads text from standard input and outputs the most frequent 100
 * words.  "Words" are space-separated tokens converted to lower case
 * and stripped of non-alphabetic characters.  So "Don't" becomes the
 * word "dont".
 *
 * @author Jim Glenn
 * @version 0.1 2015-10-14 the catcher should be responsible for not
 * hitting anything on his throw back to the pitcher
 */

public class WordCount
{
    /**
     * Returns the alphabetic characters in the given string in the order in
     * which they appeared.
     *
     * @param s a string
     * @return a string containing the alphabetic characters from s
     */
    public static String stripNonalphabetic(String s)
    {
	StringBuilder buffer = new StringBuilder();
	for (int i = 0; i < s.length(); i++)
	    {
		if (Character.isLetter(s.charAt(i)))
		    {
			buffer.append(s.charAt(i));
		    }
	    }
	return buffer.toString();
    }

    public static void main(String[] args)
    {
	CountMap<String> counts;

	if (args.length == 0 || args[0].equals("list"))
	    {

	    System.out.println("COUNT");	
		counts = new ListOfCountMapsWithLRU<String>();
	    }
	else
	    {
		counts = new MapToCountMapAdapter<String>(new TreeMap<String, Integer>());
	    }
	
	File file = new File("invisman.txt");
	Scanner in = new Scanner(System.in);

	try {
		in = new Scanner(file);
	}
	catch (FileNotFoundException e) {
		System.out.println("File not found.");
	}

	while (in.hasNext())
	    {
		String word = in.next();
		word = word.toLowerCase();
		word = stripNonalphabetic(word);

		if (word.length() > 0)
		    {
			counts.add(word);
		    }
	    }
	//in.close();

	// get the (word, count) list
	List<Map.Entry<String, Integer>> countList = counts.entryList();

	Collections.sort(countList, new Comparator<Map.Entry<String, Integer>>()
			 {
			     public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2)
			     {
				 if (e1.getKey() != e2.getKey())
				     {
					 return e2.getValue() - e1.getValue();
				     }
				 else
				     {
					 return e1.getKey().compareTo(e2.getKey());
				     }
			     }
			 });
	
	// output the top 100 words
	System.out.println("=== TOP 100 FREQUENCY === ");
	for (Map.Entry<String, Integer> e : countList.subList(0, 100))
	    {
		System.out.println(e);
	    }
	//System.out.println(counts);
	//System.out.println(counts.size());
    }
}

			 