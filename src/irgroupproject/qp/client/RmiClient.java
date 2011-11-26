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

public class RmiClient {

	private SearcherInterface rmiServer;

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

	public Set<String> getConceptsByRelationship(Concept x, Relationship r)
			throws RmiClientException {
		try {
			return rmiServer.getConceptsByRelationship(x, r);
		} catch (RemoteException e) {
			throw new RmiClientException(e);
		}
	}
}
