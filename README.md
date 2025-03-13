# KTH-IK1203
Network and Communication

# TCP & HTTP Server Project

## Project Overview
This project involves building a series of networked applications in Java, progressively increasing in complexity. It consists of four tasks, starting from a basic TCP client to a concurrent HTTP server. The goal is to understand socket programming, HTTP request parsing, and concurrency using Java threads.

## Tasks Breakdown

### Task 1: TCP Client (`TCPClient`)
**Objective:**
- Implement a TCP client in Java that can connect to a server, send requests, and receive responses.

**What I Learned:**
- How to establish a TCP connection using Java’s `Socket` class.
- Sending and receiving data using byte streams (`InputStream` and `OutputStream`).
- Handling explicit encoding and decoding of data without using wrapper classes.

### Task 2: TCP Command-Line Application (`TCPAsk`)
**Objective:**
- Build a command-line tool that interacts with `TCPClient`, allowing users to specify parameters (hostname, port, message, etc.).

**What I Learned:**
- Parsing command-line arguments in Java.
- Handling optional parameters such as timeout and limit.
- Designing a simple CLI application to interact with network services.

### Task 3: HTTP Server (`HTTPAsk`)
**Objective:**
- Implement a single-threaded HTTP server that receives HTTP GET requests, extracts query parameters, and forwards them to `TCPClient`.

**What I Learned:**
- How to create a basic HTTP server using Java’s `ServerSocket`.
- Parsing HTTP GET requests and extracting query parameters.
- Constructing and sending valid HTTP responses with correct headers.
- Handling HTTP request persistence and detecting the end of requests.

### Task 4: Concurrent HTTP Server (`ConcHTTPAsk`)
**Objective:**
- Modify `HTTPAsk` to handle multiple clients concurrently using Java threads.

**What I Learned:**
- Creating and managing threads in Java.
- Implementing the `Runnable` interface to handle client connections concurrently.
- Improving server efficiency by allowing multiple clients to be served simultaneously.

## Summary
This project provided hands-on experience with Java networking, covering TCP clients, HTTP servers, and concurrency. It demonstrated the fundamental concepts of socket communication, HTTP request handling, and multi-threading in Java, laying the groundwork for building scalable network applications.

## Usage
To run any part of this project, use the following commands:

1. **Start TCPAsk:**
   ```sh
   java TCPAsk <hostname> <port> [optional parameters]
   ```

2. **Start HTTPAsk Server:**
   ```sh
   java HTTPAsk <port>
   ```

3. **Start Concurrent HTTPAsk Server:**
   ```sh
   java ConcHTTPAsk <port>
   ```

## Technologies Used
- Java
- Sockets (TCP/IP)
- HTTP Protocol
- Multithreading (Java Threads)

## Author
[Your Name]

## License
This project is open-source and available under the MIT License.

