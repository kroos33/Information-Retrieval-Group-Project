package irgroupproject.qp.client;

import static irgroupproject.shared.Constants.RMI_SERVER;
import static irgroupproject.shared.Constants.SERVER_ADDRESS;
import static irgroupproject.shared.Constants.SERVER_PORT;
import irgroupproject.tbn.Concept;
import irgroupproject.tbn.Relationship;
import irgroupproject.tbn.SearcherInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Set;

/**
 * Client for RMI-based communication with our TBN.  <BR><BR>
 * Allows us to directly query the TBN in a scalable, platform independent way.
 * Constants such as server IP/port can be found in {@link irgroupproject.shared.Constants}
 * 
 * @author Ben Tse
 *
 */
public class RmiClient {

	private SearcherInterface rmiServer;

	/**
	 * Default Constructor for our RMI Client.
	 * @author Ben Tse
	 * @throws RmiClientException Thrown when some sort of RMI related communication error occurs.  See {@link irgroupproject.shared.Constants.java} for server settings.
	 */
	public RmiClient() throws RmiClientException {
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry(SERVER_ADDRESS, (new Integer(
					SERVER_PORT)).intValue());
			rmiServer = (SearcherInterface) (registry.lookup(RMI_SERVER));
		} catch (RemoteException e) {
			throw new RmiClientException(e);
		} catch (NotBoundException e) {
			throw new RmiClientException(e);
		}
	}

	/**
	 * Main communication method with our TBN.<BR><BR>
	 * Uses the {@link irgroupproject.tbn.SearcherInterface} class to directly invoke the TBN search method.
	 * @param x Concept we want to expand
	 * @param r Relationships we are will to allow expansion canidates to have.
	 * @return Set of terms that match the search criteria
	 * @throws RmiClientException
	 * @author Ben Tse
	 */
	public Set<String> getConceptsByRelationship(Concept x, Relationship r)
			throws RmiClientException {
		try {
			return rmiServer.getConceptsByRelationship(x, r);
		} catch (RemoteException e) {
			throw new RmiClientException(e);
		}
	}
}
