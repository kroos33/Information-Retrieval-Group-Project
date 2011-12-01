package irgroupproject.tbn;
import java.io.Serializable;
import java.util.Comparator;

/**
 * 
 * 	Concept comparator.  Used to evaluate the equality of concept objects in the TBN.
 * 	Was used only for TreeSet operations but provided for external operations.
 * 	@author Kurtis Thompson
 *
 */

class ConceptComparator implements Comparator<Concept>, Serializable {

	/**
	 * Compares two given Concepts.
	 * @author Kurtis Thompson
	 * @return 1 if arg0 is greater.  0 if equal.  -1 otherwise.
	 * @param arg0 Concept object to compare
	 * @param arg1 Concept object to compare.
	 * 
	 */
	public int compare(Concept arg0, Concept arg1) {
		
		return arg0.getConcept().toLowerCase().compareTo(arg1.getConcept().toLowerCase());
	}
	
		  


}
