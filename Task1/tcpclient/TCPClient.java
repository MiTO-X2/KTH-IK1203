package tcpclient;

import java.io.IOException;
import java.net.Socket;
import java.io.*;

public class TCPClient {
    
    public TCPClient() {
    }

    public byte[] askServer(String hostname, int port, byte [] toServerBytes) throws IOException {
        // Create a socket to connect to the server
        Socket socket = new Socket(hostname, port);
        
        // Get OutputStream to send data to the server
        OutputStream outputStream = socket.getOutputStream();

        // Write the data to the OutputStream and send it to the server
        outputStream.write(toServerBytes);
        outputStream.flush(); // This ensures that the data is sent immediately

        // Get InputStream to read the server's response
        InputStream inputStream = socket.getInputStream();

        // Collect the response from the server
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Read the server's response into the ByteArrayOutputStream
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outputStream.close();
        socket.close();

        return byteArrayOutputStream.toByteArray();
    }

    public byte[] askServer(String hostname, int port) throws IOException {
        // Create a socket to connect to the server
        Socket socket = new Socket(hostname, port);

        // Get InputStream to read the server's response
        InputStream inputStream = socket.getInputStream();

        // Collect the response from the server
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Read the server's response into the ByteArrayOutputStream
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        socket.close();

        return byteArrayOutputStream.toByteArray();
    }
}