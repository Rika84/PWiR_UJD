
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientCallback extends Remote {

    void notifyClient(String message) throws RemoteException;
}
