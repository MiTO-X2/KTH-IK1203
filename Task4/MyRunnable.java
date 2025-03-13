import java.net.*;
import java.io.*;
import java.util.Map;
import java.util.HashMap;
import tcpclient.TCPClient;

public class MyRunnable implements Runnable {
    private Socket clientSocket;

    public MyRunnable(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();
        ) {
            byte[] buffer = new byte[4096];
            int bytesRead = inputStream.read(buffer);

            if (bytesRead == -1) {
                sendErrorResponse(outputStream, 400, "Bad Request: Empty request body");
                return;
            }

            String request = new String(buffer, 0, bytesRead, "UTF-8");

            // Extract HTTP GET request
            String[] lines = request.split("\r\n");
            if (lines.length == 0 || !lines[0].startsWith("GET")) {
                sendErrorResponse(outputStream, 400, "Bad Request: Invalid request format");
                return;
            }

            String[] parts = lines[0].split(" ");
            if (parts.length < 2 || !parts[1].startsWith("/ask?")) {
                sendErrorResponse(outputStream, 404, "Not Found: URI should start with /ask?");
                return;
            }

            // Validate HTTP version
            if (parts.length < 3 || !parts[2].equals("HTTP/1.1")) {
                sendErrorResponse(outputStream, 400, "Bad Request: Invalid HTTP version");
                return;
            }
 
            Map<String, String> params = parseQuery(parts[1].substring(5));

            // Validate required parameters
            if (!params.containsKey("hostname") || !params.containsKey("port")) {
                sendErrorResponse(outputStream, 400, "Bad Request: Missing hostname or port");
                return;
            }

            handleTCPRequest(outputStream, params);

        } catch (IOException e) {
            System.err.println("Client handling error: " + e.getMessage());
        }
    }

    private static Map<String, String> parseQuery(String query) {
        Map<String, String> params = new HashMap<>();
        for (String param : query.split("&")) {
            String[] keyValue = param.split("=");
            if (keyValue.length == 2) {
                params.put(keyValue[0], keyValue[1]);
            }
        }
        return params;
    }

    private static void handleTCPRequest(OutputStream outputStream, Map<String, String> params) throws IOException {
        try {
            String hostname = params.get("hostname");
            int port = Integer.parseInt(params.get("port"));
            String data = params.getOrDefault("string", "");

            // Extract optional parameters
            boolean shutdown = Boolean.parseBoolean(params.getOrDefault("shutdown", "false"));
            Integer timeout = params.containsKey("timeout") ? Integer.parseInt(params.get("timeout")) : null;
            Integer limit = params.containsKey("limit") ? Integer.parseInt(params.get("limit")) : null;
    
            // byte[] toServerBytes = (data + "\n").getBytes("UTF-8"); // This ensures that the string ends with newline
            
            // If 'string' is provided, send it to the server
            byte[] toServerBytes = data.isEmpty() ? new byte[0] : data.getBytes("UTF-8");

            // Create TCPClient with optional parameters
            TCPClient tcpClient = new TCPClient(shutdown, timeout, limit);
            byte[] serverResponse = tcpClient.askServer(hostname, port, toServerBytes);

            sendResponse(outputStream, 200, new String(serverResponse, "UTF-8"));

        } catch (UnknownHostException e) {
            sendErrorResponse(outputStream, 400, "Bad Request: Could not contact the host: " + params.get("hostname") + "\nError: " + e.getMessage());
        } catch (NumberFormatException e) {
            sendErrorResponse(outputStream, 400, "Bad Request: Invalid numeric parameter");
        } catch (Exception e) {
            sendErrorResponse(outputStream, 500, "Internal Server Error: " + e.getMessage());
        }
    }

    private static void sendResponse(OutputStream outputStream, int statusCode, String message) throws IOException {
        String response = "HTTP/1.1 " + statusCode + " OK\r\n" +
                          "Content-Type: text/plain\r\n" +
                          "Content-Length: " + message.getBytes("UTF-8").length + "\r\n" +
                          "\r\n" + message;

        outputStream.write(response.getBytes("UTF-8"));
        outputStream.flush();
    }

    private static void sendErrorResponse(OutputStream outputStream, int statusCode, String message) {
        String statusMessage = getStatusMessage(statusCode);
        try {
            String response = "HTTP/1.1 " + statusCode + " " + statusMessage + "\r\n" +
                              "Content-Type: text/plain\r\n" +
                              "Content-Length: " + message.getBytes("UTF-8").length + "\r\n" +
                              "\r\n" + message;

            outputStream.write(response.getBytes("UTF-8"));
            outputStream.flush();
        } catch (IOException e) {
            System.err.println("Error sending response: " + e.getMessage());
        }
    }
        
    private static String getStatusMessage(int statusCode) {
        switch (statusCode) {
            case 400: return "Bad Request";
            case 404: return "Not Found";
            case 500: return "Internal Server Error";
            default: return "Error";
        }
    }    
}

