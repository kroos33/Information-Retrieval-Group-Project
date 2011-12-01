package irgroupproject.tbn;

import irgroupproject.shared.Constants;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * RMI interface provided for clients to query our TBN<BR><BR>
 * Allows remote objects to directly query our TBN for a given {@link Concept} and {@link Relationship} type.
 * We have chosen this method to create a distributed system and also, provide the framework within which brokering and partitioning could be implemented by simply passing on the request once it hits the interface.
 * <BR><BR>
 * See {@link irgroupproject.shared.Constants} for Server port values.
 * @author @author Kurtis Thompson
 *
 */
public class Searcher extends java.rmi.server.UnicastRemoteObject implements SearcherInterface {
	
	private InvertedIndex index = null;
	public int expansionLevel = 1;
	  int      thisPort;
	  String   thisAddress;
	  Registry registry;    // rmi registry for lookup the remote objects.
	
	  
	/**
	 * Default constructor for our Remote Object.  Sets up our listener and also acquires a reference to our in memory TBN to allow queries on it.
	 * @throws RemoteException - Thrown if the port is in use or for other types of communication failures.
	 * @author Kurtis Thompson
	 */
	protected Searcher() throws RemoteException {
		super();
		index = TBN.i;
		
		System.out.println("Expansion Level: " + this.expansionLevel);
		thisAddress = Constants.SERVER_ADDRESS;
        thisPort=Constants.SERVER_PORT;  // this port(registry’s port)
        System.out.println("this address="+thisAddress+",port="+thisPort);
        try{
        // create the registry and bind the name and object.
        registry = LocateRegistry.createRegistry( thisPort );
            registry.rebind(Constants.RMI_SERVER, this);
        }
        catch(RemoteException e){
        throw e;
        }
	}

	
	/**
	 * Actual search method.  Searches our TBN for expansion terms related to a {@link Concept} and having a {@link Relationship} type as specified.
	 *
	 *@return {@link java.util.Set} of matching expansion terms.
	 *@author Kurtis Thompson
	 *@param x {@link Concept} to find related terms for.
	 *@param y {@link Relationship} type of relationships to search for.
	 */
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
					if(item.getRelationshipType() == r || r == Relationship.ANY)
					{
						similar.add(item.getConcept().getConcept());
						if(expansionLevel > 1)
						{
							doRecursiveSearch(x, r, similar, item.getConcept(), r, 2);
						}
					}
				}
			}
		}
		return similar;
	}
	
	/**
	 * To prove our system was capable of handling both single level expansions as well as multi-level expansions, we added this recursive walk to expand to a user supplied depth.
	 * <BRR><BR>
	 * Depth can be configured by passing -Dtbn.expansionLevel=x to the JVM when starting the TBN.
	 * @param originalConcept Original concept that the user wanted to expand.
	 * @param originalRelationship Relationship (ie which way to walk our graph)
	 * @param currentList Currently located expansion terms.
	 * @param newConcept Current concept we are processing
	 * @param newRelationship Current relationship we are processing
	 * @param level - Current depth in our tree.
	 * 
	 * @author Kurtis Thompson
	 */
	private void doRecursiveSearch(Concept originalConcept, Relationship originalRelationship, java.util.Set<String> currentList, Concept newConcept, Relationship newRelationship, int level)
	{
		System.out.println("Expanding: " + newConcept.getConcept().toString());
		if(level > this.expansionLevel)
			return;
		else
		{
			if(!originalConcept.equals(newConcept))
			{
				InvertedIndexList docs = null;
				if(index != null)
				{
					docs = index.getConceptList(newConcept);
					if(docs != null)
					{
						Iterator<InvertedIndexItem> i = docs.iterator();
						while(i.hasNext())
						{
							InvertedIndexItem item = i.next();
							if(item.getRelationshipType() == newRelationship || newRelationship == Relationship.ANY)
							{
								if(!currentList.contains(item.getConcept().getConcept().toString()))
								{
									currentList.add(item.getConcept().getConcept());
									doRecursiveSearch(originalConcept, originalRelationship, currentList, item.getConcept(), newRelationship, level + 1);
								}
							}
						}
					}
				}
				return;
			}
		}
	}
	

}
