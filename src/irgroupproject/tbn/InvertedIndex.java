
package irgroupproject.tbn;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;


/**
 * Inverted index class. This is a hybrid of the inverted index we discussed for document indexing and your basic
 * adjacency list representation of a graph. For each node, we store all nodes connected to that node with a directed edge
 * and we also store an edge type of the set {BROAD, NARROW, RELATED}.
 * <BR>
 * <BR>
 * For actually storing the index, we use a Hashtable that has String keys and a HashSet (or {@link InvertedIndexList}) of values. The HashSet stores {@link InvertedIndexItem} objects which consist of the
 * term name and the relationship existing between the two terms.
 * <BR>
 * <BR>
 * Given our requirements of 20-25 terms and only searching one level deep, this representation was both efficient in storage and speed and
 * also presented the easiest method of construction.  Though searching the entire list is O(n), our knowledge that n is so small makes this of little concern.
 * 
 * <BR><BR>
 * Designed to support multi-threaded access.
 * 
 * 	@author kurtisthompson
 *
 */
public class InvertedIndex {

	/**
	 * serialiVersionUID - We need to define this for serialization to work every time.
	 * @author kurtisthompson
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Actual index/adjacency list representation.  
	 * <BR>
	 * <BR>
	 * Keys are the terms and the values are the related {@link Concept}+{@link Relationship} lists.
	 */
	private java.util.Hashtable<String, HashSet<InvertedIndexItem>> table;
	
	/**
	 * Default Constructor
	 * <BR><BR>
	 * Creates a new empty TBN index.
	 * @author kurtisthompson
	 * @param 
	 */
	public InvertedIndex()
	{
		table = new java.util.Hashtable<String, HashSet<InvertedIndexItem>>();
	}
	
	/**
	 * Checks to see if a passed in {@link Concept} exists in our index currently or not.
	 * Method is synchronized to facilitate multi-threaded access.
	 * 
	 * @param c {@link Concept} to check our index for.
	 * @return True if {@link Concept} does not exist in our index.  False otherwise.
	 */
	public boolean isNewConcept(Concept c)
	{
		//System.out.println("Looking for concept: " + c.getConcept().trim() + " " + table.containsKey(c.getConcept()));
		if(!table.containsKey(c.getConcept().trim()))
			return true;
		else
			return false;
	}
	
	/**
	 * Adds a {@link Concept} to our TBN index assuming it does not already exist.  If it does, we do nothing.
	 * Method is synchronized to facilitate multi-threaded access.
	 * 
	 * @param c {@link Concept} to add to our index.
	 */
	public synchronized void addConcept(Concept c)
	{
		if(!table.containsKey(c.getConcept()))
			table.put(c.getConcept(), new InvertedIndexList());
	}
	
	/**
	 * Updates a {@link Concept}.  Replaced one concept with another in the index while maintaining relationships.
	 * Method is synchronized to facilitate multi-threaded access.
	 * 
	 * @param old_concept {@link Concept} to replace
	 * @param new_concept {@link Concept} to put in the place of old_concept.
	 * @return True if concept does not exist in our index.  False otherwise.
	 */
	public synchronized void updateConcept(Concept old_concept, Concept new_concept)
	{
		String word = old_concept.getConcept();
		InvertedIndexItem any_relation = new InvertedIndexItem(old_concept, Relationship.ANY);
		InvertedIndexList entry = null;
		Enumeration entries = table.keys();
		String temp_element = null;
		while(entries.hasMoreElements())
		{
			temp_element = (String)entries.nextElement();
			if(temp_element.equals(word))
			{
				InvertedIndexList to_move = (InvertedIndexList)table.get(word);
				table.remove(word);
				table.put(new_concept.getConcept(), to_move);
			}
			else
			{
				entry = (InvertedIndexList)table.get(temp_element);
				if(entry != null)
				{
					if(entry.contains(any_relation))
					{
						entry.replaceConcept(old_concept, new_concept);
					}
				}
			}
		}
	}
	
	/**
	 * Added a new item to our InvertedIndexList for a given Concept.
	 * Method is synchronized to facilitate thread safe access.
	 * 
	 * @param c {@link Concept} whose list we will add the InvertedIndexItem to.
	 * @param i {@link InvertedIndexItem} to add to the list corresponding to the Concept c.
	 */
	public synchronized void addItem(Concept c, InvertedIndexItem i)
	{
		InvertedIndexList relatedDocuments = null;
		if(table.get(c.getConcept()) != null)
		{
			relatedDocuments = (InvertedIndexList)table.get(c.getConcept());
		}
		
		if(relatedDocuments == null)
		{
			relatedDocuments = new InvertedIndexList();
			table.put(c.getConcept(), relatedDocuments);
		}
		relatedDocuments.add(i);
	}
	
	
	/**
	 * Returns the entire InvertedIndexList for a given concept.
	 * Method is synchronized to facilitate multi-threaded access.
	 * 
	 * @param c {@link Concept} whose list we should return.
	 * @return {@link InvertedIndexList} for the concept or null, if one does not exist.
	 */
	public synchronized InvertedIndexList  getConceptList(Concept c)
	{
		return (InvertedIndexList)table.get(c.getConcept());
	}
	
	
	
	/**
	 * Removes a concept and all of it's relationships from our index.
	 * Reciprocal relationships are also removed to maintain consistency.
	 * Method is synchronized to facilitate multi-threaded access.
	 * 
	 * @param c {@link Concept} to remove from our index.
	 */
	public synchronized void removeConcept(Concept c)
	{
		String word = c.getConcept();
		InvertedIndexItem any_relation = new InvertedIndexItem(c, Relationship.ANY);
		InvertedIndexList entry = null;
		Enumeration entries = table.keys();
		String temp_element = null;
		while(entries.hasMoreElements())
		{
			temp_element = (String)entries.nextElement();
			if(temp_element.equals(word))
			{
				table.remove(word);
			}
			else
			{
				entry = (InvertedIndexList)table.get(temp_element);
				if(entry != null)
				{
					System.out.println("Checking: " + entry);
					if(entry.contains(any_relation))
					{
						entry.remove(any_relation);
					}
				}
			}
		}
	}
	
	
	/* Dump out our current Inverted Index to stdout */
	public void printIndex()
	{
		if(table != null)
		{
			java.util.Set  entry = null;
			Enumeration entries = table.keys();
			String temp_element = null;
			while(entries.hasMoreElements())
			{
				temp_element = (String)entries.nextElement();
				System.out.print(temp_element + ": ");
				
					entry = (java.util.Set)table.get(temp_element);
					Iterator i = entry.iterator();
					while(i.hasNext())
					{
						System.out.print(i.next()+ " -> ");
					}
					System.out.println("");
			}
			System.out.println("");
		}
	}
	
	/**
	 * Returns the underling HashTable implementation of our index.  Should generally not be used for anything other than complete index traversal.
	 * <BR><BR>
	 * Method is NOT Thread-Safe.
	 * 
	 * @return Pointer to our index hashtable.
	 */
	public java.util.Hashtable<String, HashSet<InvertedIndexItem>> getIndex()
	{
		return this.table;
	}
	
	/**
	 * Save our index to disk at the given path.  Method uses simple java Object streams to persist the object.  Method is thread-safe.
	 * 
	 * @param path - Path to save our index to.
	 * @throws IOException - Thrown if path is not accessible.
	 */
	public synchronized void persistIndex(String path) throws IOException
	{
		FileOutputStream f_out = new FileOutputStream(path);
		ObjectOutputStream obj_out = new ObjectOutputStream (f_out);
		obj_out.writeObject (table);
		 
	}
	
	/**
	 * Try and load our persisted index from the given path.
	 * @param path - Path where our persisted index is saved.
	 * @throws IOException - Path to index is not found.
	 * @throws ClassNotFoundException - Is thrown if a serialization error occurs.
	 */
	public synchronized void loadIndexFromFile(String path) throws IOException, ClassNotFoundException
	{
		try{
		FileInputStream f_out = new FileInputStream(path);
		ObjectInputStream obj_in = new ObjectInputStream (f_out);
			this.table = (java.util.Hashtable<String, HashSet<InvertedIndexItem>>) obj_in.readObject();
		}catch(Exception ex)
		{
			this.table = new java.util.Hashtable<String, HashSet<InvertedIndexItem>>();
		}
		
	}
	
	


}
