
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    private final String serverAddress;
    private final int serverPort;
    private String username;

    public Client(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void start() {
        try (Socket socket = new Socket(serverAddress, serverPort); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); Scanner scanner = new Scanner(System.in)) {

            System.out.print("Enter your username: ");
            username = scanner.nextLine();
            out.println("/setname " + username);

            System.out.println("Connected to the chat server as " + username + ".");
            System.out.println(in.readLine()); // Welcome message from server

            Thread listenerThread = new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println(serverMessage);
                    }
                } catch (IOException e) {
                    System.err.println("Connection lost: " + e.getMessage());
                }
            });
            listenerThread.setDaemon(true);
            listenerThread.start();

            while (true) {
                System.out.print(username + ": ");
                String userInput = scanner.nextLine();

                if (userInput.startsWith("/")) {
                    switch (userInput.toLowerCase()) {
                        case "/history":
                            out.println("/history");
                            break;
                        case "/users":
                            out.println("/users");
                            break;
                        case "/help":
                            System.out.println("Available commands:");
                            System.out.println("  /history - View chat history");
                            System.out.println("  /users - View connected users");
                            System.out.println("  /help - Show this help menu");
                            System.out.println("  exit - Leave the chat");
                            break;
                        default:
                            if (userInput.startsWith("/setname ")) {
                                out.println(userInput);
                            } else {
                                System.out.println("Unknown command. Type /help for a list of commands.");
                            }
                            break;
                    }
                } else if ("exit".equalsIgnoreCase(userInput)) {
                    out.println("EXIT");
                    System.out.println("Disconnected from the server.");
                    break;
                } else {
                    out.println(userInput);
                }
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 12345;

        Client client = new Client(serverAddress, serverPort);
        client.start();
    }
}
