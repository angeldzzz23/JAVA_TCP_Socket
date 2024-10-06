package com.cs4470;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int port = 49152;

        
        commandLineInterface();

    }



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
                        System.out.println(parts[1]);
                        System.out.println(parts[2]);
//                        connect(parts[1], Integer.parseInt(parts[2]));
                    } else {
                        System.out.println("Usage: connect <destination> <port>");
                    }
                    break;
                case "list":
                     listConnections();
                    break;
                case "send":
                    if (parts.length == 3) {
//                        send(Integer.parseInt(parts[1]), parts[2]);
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


    private static void printHelp() {
        System.out.println("Available commands:");
        System.out.println("  help                           - Display this help message");
        System.out.println("  connect <destination> <port>   - Establish a new connection");
        System.out.println("  list                           - List all active connections");
        System.out.println("  send <connection id> <message> - Send a message to a specific connection");
        System.out.println("  exit                           - Exit the program");
    }



    // this will print the connections
    private static void listConnections() {


    }










}

