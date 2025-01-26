
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibraryServer extends UnicastRemoteObject implements LibraryManager {

    private static class Book {

        private final String title;
        private final String author;
        private boolean isAvailable;

        public Book(String title, String author) {
            this.title = title;
            this.author = author;
            this.isAvailable = true;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public boolean isAvailable() {
            return isAvailable;
        }

        public void setAvailable(boolean available) {
            isAvailable = available;
        }

        @Override
        public String toString() {
            return title + " by " + author + (isAvailable ? " (Available)" : " (Borrowed)");
        }
    }

    private final List<Book> books;
    private final Map<String, String> clients; // Map<ClientID, ClientName>
    private final Map<String, ClientCallback> callbacks; // Map<ClientID, Callback>

    public LibraryServer() throws RemoteException {
        books = new ArrayList<>();
        clients = new HashMap<>();
        callbacks = new HashMap<>();
    }

    @Override
    public synchronized void registerClient(String clientId, String clientName) throws RemoteException {
        if (clients.containsKey(clientId)) {
            throw new RemoteException("Client ID already registered: " + clientId);
        }
        clients.put(clientId, clientName);
        System.out.println("Client registered: " + clientName + " (ID: " + clientId + ")");
    }

    @Override
    public synchronized void registerCallback(String clientId, ClientCallback callback) throws RemoteException {
        if (!clients.containsKey(clientId)) {
            throw new RemoteException("Client not registered: " + clientId);
        }
        callbacks.put(clientId, callback);
        System.out.println("Callback registered for client: " + clients.get(clientId));
    }

    @Override
    public synchronized void addBook(String title, String author) throws RemoteException {
        books.add(new Book(title, author));
        System.out.println("Book added: " + title + " by " + author);
        notifyClients("Book added: " + title);
    }

    @Override
    public synchronized String borrowBook(String title) throws RemoteException {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title) && book.isAvailable()) {
                book.setAvailable(false);
                System.out.println("Book borrowed: " + title);
                notifyClients("Book borrowed: " + title);
                return "You borrowed: " + title;
            }
        }
        return "Book not available: " + title;
    }

    @Override
    public synchronized String returnBook(String title) throws RemoteException {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title) && !book.isAvailable()) {
                book.setAvailable(true);
                System.out.println("Book returned: " + title);
                notifyClients("Book returned: " + title);
                return "You returned: " + title;
            }
        }
        return "Book not found or already available: " + title;
    }

    @Override
    public synchronized List<String> getAvailableBooks() throws RemoteException {
        List<String> availableBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.isAvailable()) {
                availableBooks.add(book.toString());
            }
        }
        return availableBooks;
    }

    @Override
    public synchronized List<String> getAllBooks() throws RemoteException {
        List<String> allBooks = new ArrayList<>();
        for (Book book : books) {
            allBooks.add(book.toString());
        }
        return allBooks;
    }

    private synchronized void notifyClients(String message) {
        for (Map.Entry<String, ClientCallback> entry : callbacks.entrySet()) {
            try {
                entry.getValue().notifyClient(message);
            } catch (RemoteException e) {
                System.err.println("Failed to notify client: " + entry.getKey());
            }
        }
    }

    public static void main(String[] args) {
        try {
            // Create RMI Registry
            java.rmi.registry.LocateRegistry.createRegistry(1099);

            // Create and bind the server
            LibraryServer server = new LibraryServer();
            java.rmi.Naming.rebind("LibraryManager", server);

            System.out.println("Library Server is running...");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
