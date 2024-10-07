package com.cs4470;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.*;

public class Main {

    // this keeps track of all connections
    private static final List<Connection> connections = new ArrayList<>();
    private static int connectionIdCounter = 1;
    private static int listeningPort;

    // run this first
    public static void main(String[] args) throws UnknownHostException {
	    // write your code here
        int port = 49153;
        // this will pick the port
//        int port = args.length > 0 ? Integer.parseInt(args[0]) : 49152;
        listeningPort = port;

        // start the server on a new thread
        new Thread(() -> startServer(port)).start();

        commandLineInterface();

    }

    private static void startServer(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);

            while (true) {
                // Accept incoming connections
                Socket clientSocket = serverSocket.accept();
                Connection connection = new Connection(clientSocket, connectionIdCounter++);
                connections.add(connection);
                new Thread(connection).start();
                System.out.println("New connection from " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
            }
        } catch (IOException e) {
            System.out.println("Error starting server: " + e.getMessage());
        }
    }

    // sending
    private static void send(int connectionId, String message) {
        Connection connection = connections.stream()
                .filter(conn -> conn.getId() == connectionId)
                .findFirst()
                .orElse(null);

        if (connection != null) {
            connection.sendMessage(message);
            System.out.println("Message sent to Connection ID: " + connectionId);
        } else {
            System.out.println("Error: Connection ID not found.");
        }
    }

    // TODO:
    private static void commandLineInterface() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String command = scanner.nextLine().trim();
            String[] parts = command.split("\\s+", 3);

            switch (parts[0].toLowerCase()) {
                case "help":
                    printHelp();
                    break;
                case "connect":
                    if (parts.length == 3) {
                        connect(parts[1], Integer.parseInt(parts[2]));
                    } else {
                        System.out.println("Usage: connect <destination> <port>");
                    }
                    break;
                case "list":
                     listConnections();
                    break;
                case "myip":
                    printMyIp();
                    break;
                case "myport":
                    printMyPort();
                    break;
                case "send":
                    if (parts.length == 3) {
                        send(Integer.parseInt(parts[1]), parts[2]);
                    } else {
                        System.out.println("Usage: send <connection id> <message>");
                    }
                    break;
                case "exit":
                    System.exit(0);
                default:
                    System.out.println("Unknown command. Type 'help' for available commands.");
            }
        }
    }

    private static void printMyPort() {
        System.out.println("Listening on port: " + listeningPort);
    }

    private static void printHelp() {
        System.out.println("Available commands:");
        System.out.println("  help                           - Display this help message");
        System.out.println("  connect <destination> <port>   - Establish a new connection");
        System.out.println("  list                           - List all active connections");
        System.out.println("  send <connection id> <message> - Send a message to a specific connection");
        System.out.println("  exit                           - Exit the program");
    }

    // this is the connecting
    private static void connect(String destination, int port) {
        try {
            // Avoid self-connection
            String localAddress = InetAddress.getLocalHost().getHostAddress();
            if (destination.equals(localAddress)) {
                System.out.println("Error: Cannot connect to self.");
                return;
            }

            // Check for duplicate connections
            for (Connection connection : connections) {
                if (connection.getAddress().equals(destination) && connection.getPort() == port) {
                    System.out.println("Error: Duplicate connection to " + destination + ":" + port);
                    return;
                }
            }

            // Attempt to connect to the destination
            Socket socket = new Socket(destination, port);
            Connection connection = new Connection(socket, connectionIdCounter++);
            connections.add(connection);
            new Thread(connection).start();
            System.out.println("Connected to " + destination + ":" + port);
        } catch (UnknownHostException e) {
            System.out.println("Error: Invalid destination IP address.");
        } catch (IOException e) {
            System.out.println("Error: Could not connect to " + destination + ":" + port);
        }
    }


    private static void printMyIp() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                // Skip loopback and non-active interfaces
                if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                    continue;
                }

                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (address instanceof Inet4Address && !address.isLoopbackAddress()) {
                        System.out.println("My IP address: " + address.getHostAddress());
                        return;
                    }
                }
            }
            System.out.println("Could not determine external IP address.");
        } catch (SocketException e) {
            System.out.println("Error retrieving IP address: " + e.getMessage());
        }
    }


    // this will print the connections
    private static void listConnections() {
        if (connections.isEmpty()) {
            System.out.println("No active connections.");
            return;
        }

        System.out.println("Active connections:");
        for (Connection connection : connections) {
            System.out.println("Connection ID: " + connection.getId() +
                    ", Address: " + connection.getAddress() +
                    ", Port: " + connection.getPort());
        }
    }


    // Inner class to handle individual connections
    // this is the inner class that handle s
    private static class Connection implements Runnable {
        private final Socket socket;
        private final int id; // this will the id associated with a specific connection (first id is 1)
        private final String address; // ip address
        private final int port; // port num
        // for writing

        private BufferedReader in;
        private PrintWriter out;


        public Connection(Socket socket, int id) {
            this.socket = socket;
            this.id = id;
            this.address = socket.getInetAddress().toString();
            this.port = socket.getPort();

            try {
                this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                this.out = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                System.out.println("Error initializing connection: " + e.getMessage());
            }
        }

        // these are all setters
        public int getId() {
            return id;
        }

        public String getAddress() {
            return address;
        }

        public int getPort() {
            return port;
        }

        public void sendMessage(String message) {
            out.println(message);
        }


        @Override
        public void run() {
            try {
                String receivedMessage;
                while ((receivedMessage = in.readLine()) != null) {
                    System.out.println("Message received from " + address + ":" + port + " - " + receivedMessage);
                }
            } catch (IOException e) {
                System.out.println("Connection error with " + address + ":" + port);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }



}

