package tcpclient;
import java.net.*;
import java.io.*;

public class TCPClient {
    
    private boolean shutdown;
    private Integer timeout;
    private Integer limit;

    public TCPClient(boolean shutdown, Integer timeout, Integer limit) {
        this.shutdown = shutdown;
        this.timeout = timeout;
        this.limit = limit;
    }

    public byte[] askServer(String hostname, int port, byte [] toServerBytes) throws IOException {
        System.out.println("Connecting to " + hostname + " on port " + port);
        
        // Create a socket to connect to the server
        Socket socket = new Socket();

        // Set timeout for the connection
        if (timeout != null){
            socket.connect(new InetSocketAddress(hostname, port), timeout); // This sets the connection timeout
        } else {
            socket.connect(new InetSocketAddress(hostname, port)); // This is the default behavior without a timeout
        }

        // Get OutputStream to send data to the server
        OutputStream outputStream = socket.getOutputStream();
        System.out.println("Sending data to server: " + new String(toServerBytes, "UTF-8"));
        
        if (toServerBytes.length > 0) {
            outputStream.write(toServerBytes);
            outputStream.flush(); // To ensure that the data is sent immediately    
        }

        if (shutdown) {
            socket.shutdownOutput(); // This closes only the writing part
        }

        if (timeout != null) {
            socket.setSoTimeout(timeout); // This sets the timeout for reading data
        }

        InputStream inputStream = socket.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); // This is to store the received data
        byte[] buffer = new byte[1024];

        int totalBytesRead = 0;
        int bytesRead;

        try {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                if (limit != null && (totalBytesRead + bytesRead) > limit) {
                    // To ensure we don't exceed the limit
                    int allowedBytes = limit - totalBytesRead;
                    byteArrayOutputStream.write(buffer, 0, allowedBytes);
                    break; // Stop reading after reaching the limit
                } else {
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
                    totalBytesRead += bytesRead;
                }
            }
        } catch (SocketTimeoutException e) {
            System.out.println("Socket Timeout: Returning received data so far.");
            return byteArrayOutputStream.toByteArray(); // This returns all received data before timeout
        } finally {
            socket.close(); // Close the connection
            }
        
        return byteArrayOutputStream.toByteArray(); // Convert the stored data to byte array
    }
}
