import java.io.Serializable;



/* Inverted Index Items contain two things:
 * 1. A pointer to the related document (or in our case, related concept)
 * 2. The relationship type that exists from the current term to the other term.
 */



public class InvertedIndexItem implements Serializable {
	
	private Concept c;
	private Relationship type;
	
	public InvertedIndexItem(Concept con, Relationship relationship)
	{
		c = con;
		type = relationship;
	}
	
	public Concept getConcept()
	{
		return c;
	}
	
	public Relationship getRelationshipType()
	{
		return type;
	}

	public String toString() {
		return "[" + c.getConcept() + ", " + type + "]";
	}
	
	
	
	

}
