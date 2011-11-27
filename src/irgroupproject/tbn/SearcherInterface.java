package irgroupproject.tbn;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * RMI interface provided for clients to query our TBN<BR><BR>
 * Allows remote objects to directly query our TBN for a given {@link Concept} and {@link Relationship} type.
 * We have chosen this method to create a distributed system and also, provide the framework within which brokering and partitioning could be implemented by simply passing on the request once it hits the interface.
 * <BR><BR>
 * See {@link irgroupproject.shared.Constants} for Server port values.
 * @author kurtisthompson
 *
 */
public interface SearcherInterface extends Remote {
	
	/**
	 * Actual search method.  Searches our TBN for expansion terms related to a {@link Concept} and having a {@link Relationship} type as specified.
	 *
	 *@return {@link java.util.Set} of matching expansion terms.
	 *@author kurtisthompson
	 *@param x {@link Concept} to find related terms for.
	 *@param y {@link Relationship} type of relationships to search for.
	 */
	public java.util.Set<String> getConceptsByRelationship(Concept x, Relationship r) throws RemoteException;
	

}
