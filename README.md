# KTH-IK1203
Network and Communication

# HTTP Concurrent Server Project

## Overview
This project involves building a simple yet functional concurrent web server in Java, using TCP sockets and multithreading. The project is divided into four tasks:
1. Implementing a basic TCP client (`TCPClient`).
2. Developing a command-line TCP client application (`TCPAsk`).
3. Creating an HTTP-based TCP server (`HTTPAsk`).
4. Extending `HTTPAsk` into a concurrent server (`ConcHTTPAsk`).

Through this project, key concepts such as socket programming, HTTP request handling, and Java multithreading are explored.

---

## Task 1: TCP Client (`TCPClient`)
### Objective
Implement a TCP client that connects to a given hostname and port, sends an optional data string, and retrieves the server's response.

### Key Features
- Establishes a TCP connection to a server.
- Sends an optional data string to the server.
- Reads and returns the response from the server.
- Uses raw byte streams (no wrappers like `BufferedReader`).

### Usage
```java
TCPClient client = new TCPClient(false, 5000, 1000);
byte[] response = client.askServer("time.nist.gov", 13, "");
```

---

## Task 2: Command-Line TCP Client (`TCPAsk`)
### Objective
Create a command-line application that takes user input, connects to a specified server using `TCPClient`, and prints the response.

### Key Features
- Parses command-line arguments for hostname, port, and optional data string.
- Calls `TCPClient.askServer()` to communicate with the server.
- Prints the server response to standard output.

### Usage
```sh
java TCPAsk time.nist.gov 13
```

---

## Task 3: HTTP-Based TCP Server (`HTTPAsk`)
### Objective
Develop an HTTP server that listens for HTTP requests, extracts query parameters, and uses `TCPClient` to send and receive data from remote servers.

### Key Features
- Parses HTTP GET requests.
- Extracts query parameters (`hostname`, `port`, `data`, `limit`, etc.).
- Calls `TCPClient.askServer()` with the extracted parameters.
- Returns an HTTP response with the server's reply.

### Example HTTP Request
```
GET /ask?hostname=time.nist.gov&port=13 HTTP/1.1
```

### Example HTTP Response
```
HTTP/1.1 200 OK
Content-Type: text/plain

[server response]
```

### Usage
```sh
java HTTPAsk 8888
```
Then, open a browser and visit:
```
http://localhost:8888/ask?hostname=time.nist.gov&port=13
```

---

## Task 4: Concurrent HTTP Server (`ConcHTTPAsk`)
### Objective
Enhance `HTTPAsk` to handle multiple client requests simultaneously using Java threads.

### Key Features
- Uses `ServerSocket` to listen for connections.
- Spawns a new thread for each client request.
- Each thread handles a single HTTP request independently.
- Supports concurrent HTTP request processing.

### Usage
```sh
java ConcHTTPAsk 8888
```

### How It Works
- When a client connects, a new thread is created to handle the request.
- The thread processes the request and sends the response while the main server continues listening for new connections.
- This improves performance and allows multiple clients to interact with the server at the same time.

---

## Technologies Used
- Java (Sockets, Multithreading, HTTP Parsing)
- TCP/IP Protocol
- HTTP Protocol

---

## How to Run the Project
1. Compile the Java files:
   ```sh
   javac *.java
   ```
2. Run the concurrent HTTP server:
   ```sh
   java ConcHTTPAsk 8888
   ```
3. Send a request using a browser or command-line tool:
   ```sh
   curl "http://localhost:8888/ask?hostname=time.nist.gov&port=13"
   ```

---

## Author
[Your Name]

## License
This project is open-source and available under the MIT License.

