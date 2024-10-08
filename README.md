# Java TCP Socket 

# Overview
This project is a TCP-based chat server implemented in Java. The server establishes and manages connections with multiple clients, allowing them to send and receive messages. It supports several commands like connecting to specific ports, listing active connections, and message broadcasting. The project demonstrates the usage of TCP sockets and multi-threading in Java to handle concurrent client communication.

# Features
- Supports multiple client connections.
- Clients can send messages to each other via the server.
- Ability to connect to a specific server port.
- List active client connections.
- broadcasting messages to all connected clients.
- Command-line interface to manage connections and messages.

# Technologies
- Java SE
- TCP/IP Sockets
- Multi-threading
- Command-line Interface (CLI)


# Structure
```
java-tcp-chat-server/
│
├── src/
│   ├── server/
│   │   ├── ChatServer.java      # Server class handling connections and messaging
│   │   ├── ClientHandler.java   # Threaded class managing individual client
│   │
│   └── client/
│       ├── ChatClient.java      # Client class for sending/receiving messages
│
├── README.md                    # Project documentation
└── .gitignore                   # Git ignored files
```


 # ChatServer.java

	•	Initializes the server, listens on a specific port, and accepts connections.
	•	Handles multiple clients by creating separate threads for each.
	•	Relays messages between clients.

# ClientHandler.java

	•	Runs as a separate thread for each connected client.
	•	Listens for incoming messages and forwards them to other clients.

#  ChatClient.java

	•	Manages the connection between a client and the server.
	•	Sends user input to the server and displays incoming messages

