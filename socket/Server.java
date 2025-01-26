
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

class Server {

    private final int port;
    private final ExecutorService threadPool;
    private final List<ClientHandler> clients;
    private final List<String> messageHistory;

    public Server(int port, int maxClients) {
        this.port = port;
        this.threadPool = Executors.newFixedThreadPool(maxClients);
        this.clients = Collections.synchronizedList(new ArrayList<>());
        this.messageHistory = Collections.synchronizedList(new ArrayList<>());
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clients.add(clientHandler);
                threadPool.execute(clientHandler);
            }
        } catch (IOException e) {
            System.err.println("Error in server: " + e.getMessage());
        } finally {
            threadPool.shutdown();
        }
    }

    public void broadcastMessage(String message, ClientHandler sender) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                if (client != sender) {
                    client.sendMessage(message);
                }
            }
        }
        synchronized (messageHistory) {
            messageHistory.add(message);
        }
    }

    public void sendHistory(ClientHandler client) {
        synchronized (messageHistory) {
            for (String message : messageHistory) {
                client.sendMessage("[History] " + message);
            }
        }
    }

    public void sendUserList(ClientHandler client) {
        synchronized (clients) {
            StringBuilder userList = new StringBuilder("Connected users:\n");
            for (ClientHandler c : clients) {
                userList.append("- ").append(c.getUsername()).append("\n");
            }
            client.sendMessage(userList.toString());
        }
    }

    public void removeClient(ClientHandler clientHandler) {
        synchronized (clients) {
            clients.remove(clientHandler);
        }
        broadcastMessage(clientHandler.getUsername() + " has left the chat.", null);
    }

    private static class ClientHandler implements Runnable {

        private final Socket clientSocket;
        private final Server server;
        private PrintWriter out;
        private String username = "Anonymous";

        public ClientHandler(Socket clientSocket, Server server) {
            this.clientSocket = clientSocket;
            this.server = server;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                this.out = out;
                out.println("Welcome to the chat server! Type '/help' for a list of commands.");

                String clientMessage;
                while ((clientMessage = in.readLine()) != null) {
                    synchronized (this) {
                        if (clientMessage.startsWith("/setname ")) {
                            String newName = clientMessage.substring(9).trim();
                            if (!newName.isEmpty()) {
                                String oldName = username;
                                username = newName;
                                server.broadcastMessage(oldName + " is now known as " + username + ".", this);
                            } else {
                                sendMessage("Error: Username cannot be empty.");
                            }
                        } else if ("EXIT".equalsIgnoreCase(clientMessage)) {
                            out.println("Goodbye!");
                            break;
                        } else if ("/history".equalsIgnoreCase(clientMessage)) {
                            server.sendHistory(this);
                        } else if ("/users".equalsIgnoreCase(clientMessage)) {
                            server.sendUserList(this);
                        } else if ("/help".equalsIgnoreCase(clientMessage)) {
                            sendMessage("Available commands:\n"
                                    + "  /history - View chat history\n"
                                    + "  /users - View connected users\n"
                                    + "  /help - Show this help menu\n"
                                    + "  exit - Leave the chat");
                        } else {
                            server.broadcastMessage(username + ": " + clientMessage, this);
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Error handling client: " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.err.println("Error closing client socket: " + e.getMessage());
                }
                server.removeClient(this);
                System.out.println("Client disconnected.");
            }
        }

        public synchronized void sendMessage(String message) {
            if (out != null) {
                out.println(message);
            }
        }

        public synchronized String getUsername() {
            return username;
        }
    }

    public static void main(String[] args) {
        int port = 12345;
        int maxClients = 10;
        Server server = new Server(port, maxClients);
        server.start();
    }
}
