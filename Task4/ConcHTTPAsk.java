import java.net.*;
import java.io.*;

public class ConcHTTPAsk {
    public static void main( String[] args) {
        if (args.length != 1) {
            System.err.println("java ConcHTTPAsk <port>");
            return;
        }

        int port = Integer.parseInt(args[0]);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port); // Print server startup message
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from: " + clientSocket.getInetAddress());
                new Thread(new MyRunnable(clientSocket)).start(); // Create and start a new thread
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
}

