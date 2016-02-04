import java.util.List;
import java.util.ArrayList; 
import java.util.Map;
import java.util.AbstractMap; 
import java.lang.Math;

/**
 * A frequency list (or a map from keys to integer frequencies).  Keys
 * can be added or removed and their frequencies can be increased by one.
 *
 * @author Tomal Hossain
 * @version 0.1 2015-11-13
 */

public class ListOfCountMapsWithLRU<K extends Comparable<? super K>> implements CountMap <K> 
{

	ArrayList<BSTWithLRU<K>> countMapList = new ArrayList<BSTWithLRU<K>>(); 

    /**
     * Adds one to the given key's frequency.  If the key was not present
     * (if its frequency was 0) then the key is added with frequency 1.
     * @param key a key
     */

	public void cascade(K key)
	{
		if (countMapList.get(countMapList.size()-1).size < Math.pow(countMapList.get(countMapList.size()-2).size, 2))
		{
			for (int i = countMapList.size()-2; i>=0; i--)
			{
				Map.Entry<K, Integer> pair = countMapList.get(i).removeLRU();
				countMapList.get(i+1).add(pair.getKey(), pair.getValue()); 
			}
		countMapList.get(0).add(key, 1);
		}
		else
		{
			BSTWithLRU<K> map = new BSTWithLRU<K>(); 
			countMapList.add(map);

			for (int i = countMapList.size()-2; i>=0; i--)
			{
				Map.Entry<K, Integer> pair = countMapList.get(i).removeLRU();
				countMapList.get(i+1).add(pair.getKey(), pair.getValue()); 
			}
			countMapList.get(0).add(key, 1);	
		}
	}	

    public void cascade(K key, int i)
	{
	Map.Entry<K, Integer> tempEntry = countMapList.get(i).remove(countMapList.get(i).head.getKey());
		  
		for (int k = i-1; k>=0; k--)
		{
			Map.Entry<K, Integer> pair = countMapList.get(i).removeLRU();
			countMapList.get(i+1).add(pair.getKey(), pair.getValue()); 
		}
		countMapList.get(0).add(tempEntry.getKey(), tempEntry.getValue()); 
	}

    public void add(K key) 
    {
    	if (countMapList.size() == 0)
    	{
    		BSTWithLRU<K> map = new BSTWithLRU<K>(); 
    		countMapList.add(map);	
 			(countMapList.get(0)).add(key, 1);
    	}
    	else if (countMapList.size() == 1)
    	{
    		if (countMapList.get(0).size < 2)
    		{
    			(countMapList.get(0)).add(key, 1);
    		}
    		else
    		{
    		BSTWithLRU<K> map = new BSTWithLRU<K>(); 
    		countMapList.add(map);
    		Map.Entry<K, Integer> pair = countMapList.get(0).removeLRU();
    		countMapList.get(1).add(pair.getKey(), pair.getValue()); 
    		countMapList.get(0).add(key, 1);	
    		}
    	}
    	else if (countMapList.size() == 2)
    	{
			if ((countMapList.get(1)).size < 4)
			{
			Map.Entry<K, Integer> pair = countMapList.get(0).removeLRU();
    		countMapList.get(1).add(pair.getKey(), pair.getValue()); 
    		countMapList.get(0).add(key, 1);	
			}	
			else
			{
			BSTWithLRU<K> map = new BSTWithLRU<K>(); 
    		cascade (key); 		
			}
    	}
    	else 
    	{
    		cascade(key);
    	}
    }
    
    /**
     * Returns the frequency of the given key.  The frequency is 0 if the
     * key is not present.
     *
     * @param key a key
     */
    public int get(K key)
    {
    	for (int i = 0; i < countMapList.size(); i++)
    	{
    		int j = countMapList.get(i).get(key);
    		if (j != 0)
    		{ 
    		cascade(key, i); 
			}
    	}
    	return 0; 
    }

    /**
     * Returns a list of (item, frequency) entries
     */

    public List<Map.Entry<K, Integer>> entryList()
    {
    	ArrayList<Map.Entry<K, Integer>> entryList = new ArrayList<Map.Entry<K, Integer>>(); 
    	
    	for (int i = 0; i < countMapList.size(); i++)
    	{
    		countMapList.get(i).addInOrder(entryList, countMapList.get(i).root); 
    	}
    	return entryList; 
    }

    /**
     * Returns the number of items in this list.
     */
    public int size() 
    {
    	List<Map.Entry<K, Integer>> entryList = entryList();  
    	return entryList.size(); 
    }
} 
    

