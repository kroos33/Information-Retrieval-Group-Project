

import java.util.Iterator;

public class InvertedIndexList extends java.util.HashSet<InvertedIndexItem> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8983098514374607427L;

	
	@Override
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

	@Override
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
