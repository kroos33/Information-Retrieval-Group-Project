import java.io.IOException;
import java.util.Set;



public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/* Define an index, this would be read from disk normally */
		InvertedIndex i = new InvertedIndex();
		
		/* Create some base sports related concepts */
		Concept Sport = new Concept("Sport");
		Concept Baseball = new Concept("Baseball");
		Concept Yankees = new Concept("Yankees");
		Concept Mets = new Concept("Mets");
		Concept Softball = new Concept("Softball");
		
		
		
		/* Add them to our Inverted Index as terms */
		i.addConcept(Sport);
		i.addConcept(Baseball);
		i.addConcept(Yankees);
		i.addConcept(Mets);
		i.addConcept(Softball);
		
		
		/* Now we need to set up relationships
		 * We do so by first defining a relationship and then adding it to the proper relationship list for the term.
		 *  
		 *  This is kind of ugly but allows easy two way relationships as is required.
		 *  
		 *  
		 */
		
		InvertedIndexItem Baseball_to_Sport = new InvertedIndexItem(Sport, Relationship.BROAD);
		i.addItem(Baseball, Baseball_to_Sport);
		
		InvertedIndexItem Sport_to_Baseball = new InvertedIndexItem(Baseball, Relationship.NARROW);
		i.addItem(Sport, Sport_to_Baseball);
		
		
		InvertedIndexItem Baseball_to_Softball = new InvertedIndexItem(Softball, Relationship.RELATED);
		i.addItem(Baseball, Baseball_to_Softball);
		
		InvertedIndexItem Softball_to_Baseball = new InvertedIndexItem(Baseball, Relationship.RELATED);
		i.addItem(Softball, Softball_to_Baseball);
		
		InvertedIndexItem Baseball_to_Yankees = new InvertedIndexItem(Yankees, Relationship.NARROW);
		i.addItem(Baseball, Baseball_to_Yankees);
		
		InvertedIndexItem Yankees_to_Baseball = new InvertedIndexItem(Baseball, Relationship.BROAD);
		i.addItem(Yankees, Yankees_to_Baseball);
		
		InvertedIndexItem Baseball_to_Mets = new InvertedIndexItem(Mets, Relationship.NARROW);
		i.addItem(Baseball, Baseball_to_Mets);
		
		InvertedIndexItem Mets_to_Baseball = new InvertedIndexItem(Baseball, Relationship.BROAD);
		i.addItem(Mets, Mets_to_Baseball);
		
		
		i.printIndex();
		
		
		/* Testing simple persistence mechanisms*/
		
		try {
			i.persistIndex("mytest");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			i.loadIndexFromFile("mytest");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
		
		
		Searcher searcher = new Searcher(i);
	
		/*Let's pretend we get a query like in the spec
		 * 
		 * So to satisfy Baseball[R] and series or october weneed to do the following:
		 * 
		 *  */
		Concept c = new Concept("Baseball");
		
		/*This returns a set so you can just loop through it on the QP side */
		Set<String> results = searcher.getConceptsByRelationship(c, Relationship.RELATED);
		System.out.println("Baseball[R] should become (Baseball or " + results.toString().replaceAll(",", " or ") + ")");
		
		
		
		
		
		
		

	}

}
