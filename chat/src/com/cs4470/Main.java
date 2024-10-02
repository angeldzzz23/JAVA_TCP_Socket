package com.cs4470;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args){
        // This is a test port
        int testPort = 0;
        myPort(testPort);
        myIP();
    }

    /**
     * myPort Listens for the incoming connection
     * Displays what port user is listening for and
     * the destination IP address (as a test)
     * @param port This value is to be set by the user
     */
    public static void myPort(int port){
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port: " + serverSocket.getLocalPort());

            while(true){
                serverSocket.accept();
                System.out.println("Accepted connection from " + serverSocket.getLocalSocketAddress());
            }

        }catch(IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    /**
     * myIP displays the IP address of the current machine
     */
    public static void myIP(){
        try{
           InetAddress address = InetAddress.getLocalHost();
           System.out.println(address.getHostAddress());
        }catch(IOException exception){
            System.out.println(exception.getMessage());
        }
    }
}
