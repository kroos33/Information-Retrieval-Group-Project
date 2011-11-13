

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Searcher {
	
	private InvertedIndex index = null;
	
	public Searcher(InvertedIndex i)
	{
		index = i;
	}
	
	/* 
	 * 
	 * Keeping this around in case they are needed.  Probably won't be given the QP does the merges
	 * 
	public java.util.Set doUnion(java.util.Set document_list_a, java.util.Set  document_list_b)
	{
		Set union = new HashSet(document_list_a);
		union.addAll(document_list_b);
		return union;
	}
	
	
	public java.util.Set doIntersection(java.util.Set  document_list_a, java.util.Set  document_list_b)
	{
		Set intersection = new HashSet(document_list_a);
		intersection.retainAll(document_list_b);
		return intersection;
	}
	*/
	
	/* Return all concepts related to c, where the relationship type is r*/
	public java.util.Set<String> getConceptsByRelationship(Concept x, Relationship r)
	{
		Set<String> similar = new HashSet<String>();
		InvertedIndexList docs = null;
		if(index != null)
		{
			docs = index.getConceptList(x);
			if(docs != null)
			{
				Iterator<InvertedIndexItem> i = docs.iterator();
				while(i.hasNext())
				{
					InvertedIndexItem item = i.next();
					//System.out.println("Comparing " + item.getConcept().getConcept() + " and " + c.getConcept());
					if(item.getRelationshipType() == r)
					{
						similar.add(item.getConcept().getConcept());
					}
				}
			}
		}
		return similar;
	}
	

}
