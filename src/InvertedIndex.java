

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


/* Basic inverted index.  Add concepts, add related documents to those concepts, print the list, remove items, etc) */
public class InvertedIndex {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private java.util.Hashtable<String, HashSet<InvertedIndexItem>> table;
	
	public InvertedIndex()
	{
		table = new java.util.Hashtable<String, HashSet<InvertedIndexItem>>();
	}
	
	/* Might be usefull?  Doubt it. */
	public boolean isNewConcept(Concept c)
	{
		if(!table.containsKey(c.getConcept()))
			return false;
		else
			return true;
	}
	
	/* It is safe to call this if the concept already exists, we check first */
	public synchronized void addConcept(Concept c)
	{
		if(!table.containsKey(c.getConcept()))
			table.put(c.getConcept(), new InvertedIndexList());
	}
	
	/* Add a InvertedIndxItem to our list of documents for Concept c */
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
	
	
	/* Return the inverted index document list for a given concept (word) */
	public synchronized InvertedIndexList  getConceptList(Concept c)
	{
		return (InvertedIndexList)table.get(c.getConcept());
	}
	
	/* This is kind of ugly but in the real world, we assume deletions to be minimal.  Also, real pointers would help */
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
	
	public java.util.Hashtable<String, HashSet<InvertedIndexItem>> getIndex()
	{
		return this.table;
	}
	
	public synchronized void persistIndex(String path) throws IOException
	{
		FileOutputStream f_out = new FileOutputStream(path);
		ObjectOutputStream obj_out = new ObjectOutputStream (f_out);
		obj_out.writeObject (table);
		 
	}
	
	public synchronized void loadIndexFromFile(String path) throws IOException, ClassNotFoundException
	{
		FileInputStream f_out = new FileInputStream(path);
		ObjectInputStream obj_in = new ObjectInputStream (f_out);
		this.table = (java.util.Hashtable<String, HashSet<InvertedIndexItem>>) obj_in.readObject();
		
	}
	
	


}
