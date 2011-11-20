
import java.rmi.*;
import java.rmi.registry.*;
import java.util.Iterator;
import java.util.Set;
import java.net.*;
import java.net.UnknownHostException;

public class RmiClient
{
    static public void main(String args[])
    {
       SearcherInterface rmiServer;
       Registry registry;
       String serverAddress = "127.0.0.1";
       String serverPort= "3232";
       try{
           registry=LocateRegistry.getRegistry(
               serverAddress,
               (new Integer(serverPort)).intValue()
           );
           // look up the remote object
           rmiServer=
              (SearcherInterface)(registry.lookup("rmiServer"));
           // call the remote method
           Concept c = new Concept("Baseball");
           
           Set<String> terms = rmiServer.getConceptsByRelationship(c, Relationship.ANY);
           Iterator<String> it = terms.iterator();
           while(it.hasNext())
           {
        	   System.out.println(it.next());
           }
           
       }
       catch(RemoteException e){
           e.printStackTrace();
       }
       catch(NotBoundException e){
           e.printStackTrace();
       }
    }
}
 
