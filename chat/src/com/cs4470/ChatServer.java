package com.cs4470;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChatServer {
    private static final int MAX_CONNECTIONS = 10;
    private static ServerSocket serverSocket;
//    private static List<Connection> connections = new ArrayList<>();
    private static int nextConnectionId = 1;


    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java ChatServer <port>");
            return;
        }





    }







}
