import java.io.Serializable;
import java.util.Comparator;


class ConceptComparator implements Comparator<Concept>, Serializable {

	@Override
	public int compare(Concept arg0, Concept arg1) {
		
		return arg0.getConcept().toLowerCase().compareTo(arg1.getConcept().toLowerCase());
	}
	
		  


}
