package irgroupproject.tbn;

import java.util.Iterator;

/**
 * InvertedIndexList implementation.
 * <BR><BR>
 * This class is a customized version of a normal Java HashSet that stores {@link InvertedIndexItem} objects.  Our customizations incldue the ability to use the .contains method regardless of {@link Relationship} type and then to simply replace {@link Concept} items with new ones.
 * @author Kurtis Thompson
 *
 */
public class InvertedIndexList extends java.util.HashSet<InvertedIndexItem> {

	/**
	 * Serialization ID to ensure that ObjectStreams work correctly.
	 */
	private static final long serialVersionUID = 8983098514374607427L;

	

	/**
	 * Allows users to use the .contains method with any {@link Relationship} type.
	 * @author Kurtis Thompson
	 * @param arg0 {@link InvertedIndexItem} object to search our list for.
	 * @returns True if found, False otherwise.
	 * @Override
	 */
	public boolean contains(Object arg0) {
		InvertedIndexItem arg = (InvertedIndexItem)arg0;
		if(arg.getRelationshipType().equals(Relationship.ANY))
		{
			Iterator items = this.iterator();
			while(items.hasNext())
			{
				InvertedIndexItem curr = (InvertedIndexItem)items.next();
				if(curr.getConcept().getConcept().equals(arg.getConcept().getConcept()))
				{
					return true;
				}
			}
			return false;
			
		}
		else
			return super.contains(arg0);
	}

	/**
	 * Allows users to use the .remove method with any {@link Relationship} type.
	 * @author Kurtis Thompson
	 * @param arg0 {@link InvertedIndexItem} object to search our list for and then remove.
	 * @returns True if found and removed, False otherwise.
	 * @Override
	 */
	public boolean remove(Object arg0) {
		InvertedIndexItem arg = (InvertedIndexItem)arg0;
		if(arg.getRelationshipType().equals(Relationship.ANY))
		{
			Iterator items = this.iterator();
			while(items.hasNext())
			{
				InvertedIndexItem curr = (InvertedIndexItem)items.next();
				
				if(curr.getConcept().getConcept().equals(arg.getConcept().getConcept()))
				{
					super.remove(curr);
					return true;
				}
			}
			return false;
			
		}
		else
		return super.remove(arg0);
	}
	
	
	/**
	 * Replaces all {@link InvertedIndexItem} objects that contain a given {@link Concept} with a new {@link Concept} item.
	 * @param c1 {@link Concept} that we should replace.
	 * @param c2 {@link Concept} that we should replace with.
	 * @author Kurtis Thompson
	 */
	public void replaceConcept(Concept c1, Concept c2)
	{
		Iterator items = this.iterator();
		while(items.hasNext())
		{
			InvertedIndexItem curr = (InvertedIndexItem)items.next();
			
			if(curr.getConcept().getConcept().equals(c1.getConcept()))
			{
				curr.getConcept().setConcept(c2.getConcept());
			}
		}
	}
	
	

}
