/*
 * HEB Developer I Programming Challenge
 * by Lloyd Parker
 * 
 * Input: A text file
 * 
 * Output: A text file listing the words sorted from most occurrences to least with a number of equal signs
 * proportional to the number of occurrences in text and the number of occurrences itself.
*/
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;


public class CountUniqueWords 
{
	
	static void countUniqueWords(String fileName, Map<String, Integer> words) throws FileNotFoundException
	{
		Scanner file = new Scanner (new File(fileName));
		while (file.hasNext()) 
		{
			String word = file.next();
			// this removes special characters and digits, then sets word to lower case to ensure true count
			word = word.replaceAll("\\p{Punct}|\\d", "");
			word = word.toLowerCase();
			Integer count = words.get(word);
			if (count != null)
				count++;
			else
				count = 1;
			words.put(word,count);
		}
		
		file.close();
	}
		
	private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order)
    {

        List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<String, Integer>>()
        {
            public int compare(Entry<String, Integer> o1,
                    Entry<String, Integer> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Entry<String, Integer> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
	
	
	public static void main(String[] args) throws FileNotFoundException 
	{
		//Creates new Map with string and integer to hold word and count respectively.
		Map<String, Integer> words = new HashMap<String, Integer>();
		//Calls method to populate map with text file
		try 
		{
			countUniqueWords("C:\\Users\\Lloyd\\Desktop\\input.txt", words);
		}
		catch (Exception e)
		{
			System.out.println("Error with text file. Please check file type.");
		}
		
		//Sorts the map in descending order
		Map<String, Integer> sortedMapDesc = sortByComparator(words,false);
		String outputString = "";
		//Iterates through each map entry and writes them to string according to parameters
		for (Map.Entry<String, Integer> entry : sortedMapDesc.entrySet()) 
		{
			String equalSigns = "";
			int i = entry.getValue();
			while (i > 0) 
			{
				equalSigns += "=";
				i--;
			}
			outputString += entry.getKey() + " | " + equalSigns + " (" + entry.getValue() + ")" + "\r\n";
		}
		//Writes string to file
		BufferedWriter writer = null;
		try 
		{
			writer = new BufferedWriter( new FileWriter( "output.txt"));
			writer.write(outputString);
		}
		catch (IOException e)
		{
		}
		finally
		{
			try
			{
				if (writer != null)
					writer.close();
			}
			catch ( IOException e)
			{
			}
		}
	}
}