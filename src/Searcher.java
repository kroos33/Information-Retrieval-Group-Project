

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Searcher extends java.rmi.server.UnicastRemoteObject implements SearcherInterface {
	
	private InvertedIndex index = null;
	  int      thisPort;
	  String   thisAddress;
	  Registry registry;    // rmi registry for lookup the remote objects.
	
	protected Searcher() throws RemoteException {
		super();
		index = TBN.i;
		
		thisAddress = "127.0.0.1";
        thisPort=3232;  // this port(registry’s port)
        System.out.println("this address="+thisAddress+",port="+thisPort);
        try{
        // create the registry and bind the name and object.
        registry = LocateRegistry.createRegistry( thisPort );
            registry.rebind("rmiServer", this);
        }
        catch(RemoteException e){
        throw e;
        }
		
		// TODO Auto-generated constructor stub
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
		if(index == null)
		{
			index = new InvertedIndex();
			try {
				index.loadIndexFromFile("backup");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
					if(item.getRelationshipType() == r || r == Relationship.ANY)
					{
						similar.add(item.getConcept().getConcept());
					}
				}
			}
		}
		return similar;
	}
	

}
