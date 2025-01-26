
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class LibraryClient {

    public static class ClientCallbackImpl extends UnicastRemoteObject implements ClientCallback {

        public ClientCallbackImpl() throws RemoteException {
            super();
        }

        @Override
        public void notifyClient(String message) throws RemoteException {
            System.out.println("[Notification]: " + message);
        }
    }

    public static void main(String[] args) {
        try {
            // Connect to the remote object
            LibraryManager library = (LibraryManager) Naming.lookup("rmi://localhost/LibraryManager");
            Scanner scanner = new Scanner(System.in);

            // Register client
            String clientId = UUID.randomUUID().toString();
            System.out.print("Enter your name: ");
            String clientName = scanner.nextLine();
            library.registerClient(clientId, clientName);
            System.out.println("Welcome, " + clientName + "! Your ID: " + clientId);

            // Register callback for notifications
            ClientCallback callback = new ClientCallbackImpl();
            library.registerCallback(clientId, callback);
            System.out.println("You are now subscribed to notifications.");

            String command;

            while (true) {
                System.out.println("\nAvailable commands:");
                System.out.println("1. Add a book");
                System.out.println("2. Borrow a book");
                System.out.println("3. Return a book");
                System.out.println("4. View available books");
                System.out.println("5. View all books");
                System.out.println("6. Exit");
                System.out.print("Enter your command: ");
                command = scanner.nextLine();

                switch (command) {
                    case "1":
                        System.out.print("Enter the book title: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter the book author: ");
                        String author = scanner.nextLine();
                        library.addBook(title, author);
                        System.out.println("Book added successfully.");
                        break;

                    case "2":
                        System.out.print("Enter the title of the book to borrow: ");
                        title = scanner.nextLine();
                        String borrowResult = library.borrowBook(title);
                        System.out.println(borrowResult);
                        break;

                    case "3":
                        System.out.print("Enter the title of the book to return: ");
                        title = scanner.nextLine();
                        String returnResult = library.returnBook(title);
                        System.out.println(returnResult);
                        break;

                    case "4":
                        List<String> availableBooks = library.getAvailableBooks();
                        System.out.println("Available books:");
                        if (availableBooks.isEmpty()) {
                            System.out.println("No books are currently available.");
                        } else {
                            for (String book : availableBooks) {
                                System.out.println("- " + book);
                            }
                        }
                        break;

                    case "5":
                        List<String> allBooks = library.getAllBooks();
                        System.out.println("All books:");
                        for (String book : allBooks) {
                            System.out.println("- " + book);
                        }
                        break;

                    case "6":
                        System.out.println("Exiting client. Goodbye, " + clientName + "!");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid command. Please try again.");
                }
            }
        } catch (Exception e) {
            System.err.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
