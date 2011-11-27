package irgroupproject.tbn;

import java.io.Serializable;

/** Inverted Index Items contain two things:<BR><BR>
 * 1. A pointer to the related document (or in our case, related concept)<BR><BR>
 * 2. The relationship type that exists from the current term to the other term.<BR><BR>
 * 
 * Relationship types will be of the set of types defined in {@link Relationship}. We store these items in our InvertedIndexList to represent our graph in the style of adjacency lists.
 * 
 * @author kurtisthompson
 */



public class InvertedIndexItem implements Serializable {
	
	private static final long serialVersionUID = 4918869774684718392L;
	private Concept c;
	private Relationship type;
	
	/**
	 * Default Constructor.  Takes the related {@link Concept} pointer and the {@link Relationship} type.
	 * 
	 * @param con {@link Concept} that is related to our current item.
	 * @param relationship Relationship type provided by {@link Relationship}
	 * 
	 * @author kurtisthompson
	 */
	public InvertedIndexItem(Concept con, Relationship relationship)
	{
		c = con;
		type = relationship;
	}
	
	/**
	 * Returns the {@link Concept} contained in this item.
	 * @return {@link Concept} represented by this object.
	 */
	public Concept getConcept()
	{
		return c;
	}
	
	
	/**
	 * Returns the {@link Relationship} contained in this item.
	 * @return {@link Relationship} object represented by this object.
	 */
	public Relationship getRelationshipType()
	{
		return type;
	}

	public String toString() {
		return "[" + c.getConcept() + "," + type + "]";
	}
	
	
	
	

}
