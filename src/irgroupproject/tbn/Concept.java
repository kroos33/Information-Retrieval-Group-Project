
package irgroupproject.tbn;
import java.io.Serializable;

/**
 * 
 * TBN concept class. Simply wraps the string representation of the term.  
 * <BR>
 * <BR>
 * Our {@link InvertedIndexItem} class stores both Concepts and a {@link Relationship} value.
 * 
 * 	@author Kurtis Thompson
 *
 */
public class Concept implements Serializable {
	
	/**
	 * serialiVersionUID - We need to define this for serialization to work everytime.
	 * @author Kurtis Thompson
	 * 
	 */
	private static final long serialVersionUID = 5204763425082126533L;
	private String word;
	
	/**
	 * Default Concept Constructor
	 * @author Kurtis Thompson
	 * @param None
	 * @return None
	 */
	public Concept()
	{
		word = new String();
	}
	
	/**
	 * Alternate Concept Constructor
	 * @author Kurtis Thompson
	 * @param s - String representation of the term you want to generate a concept object for.
	 */
	public Concept(String s)
	{
		word = s;
	}
	
	/**
	 * Returns the string representation of the concept.
	 * 
	 * @author Kurtis Thompson
	 * @param None
	 * @return String representation of the concept.
	 */
	public String getConcept()
	{
		return word;
	}
	
	
	/**
	 * Sets the current concept value to the given string value.
	 * @author Kurtis Thompson
	 * @param s Representation of the term.
	 * @return None
	 */
	public void setConcept(String s)
	{
		word = s;
	}
	
	
	
	/**
	 * Concept equality function.  Will evaluate the equality of a given concept to another passed in.
	 * @author Kurtis Thompson
	 * @param obj Concept to compare against.
	 * @return True if equal, False otherwise.
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		if(this.getConcept().equals(((Concept)obj).getConcept()))
		{
			return true;
		}
		return false;
	}
	
	
	
	
	

}
