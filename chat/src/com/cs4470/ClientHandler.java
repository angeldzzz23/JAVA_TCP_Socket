package com.cs4470;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // Set up streams to read from and write to the client
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String message;
            // Continuously listen for messages from the client
            while ((message = in.readLine()) != null) {
                // Print received message to server console
                System.out.println("Received: " + message);

                // Broadcast the message to all other clients
//                ChatServer.broadcast(message, this);
            }
        } catch (IOException e) {
            System.out.println("Error handling client: " + e.getMessage());
        } finally {
            // If client disconnects, remove them from the server and close resources
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            ChatServer.removeClient(this);
        }
    }

    // Method to send message to the client
    public void sendMessage(String message) {
        out.println(message);  // Send message to the client
    }
}
