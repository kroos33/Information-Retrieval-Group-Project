package irgroupproject.tbn;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface SearcherInterface extends Remote {
	
	public java.util.Set<String> getConceptsByRelationship(Concept x, Relationship r) throws RemoteException;
	

}
