
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface LibraryManager extends Remote {

    // Register a new client with a unique ID and name
    void registerClient(String clientId, String clientName) throws RemoteException;

    // Register client callback for notifications
    void registerCallback(String clientId, ClientCallback callback) throws RemoteException;

    // Add a new book to the library
    void addBook(String title, String author) throws RemoteException;

    // Borrow a book from the library
    String borrowBook(String title) throws RemoteException;

    // Return a book to the library
    String returnBook(String title) throws RemoteException;

    // Get a list of available books
    List<String> getAvailableBooks() throws RemoteException;

    // Get a list of all books
    List<String> getAllBooks() throws RemoteException;
}
