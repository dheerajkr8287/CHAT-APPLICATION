# CHAT-APPLICATION


<h3>Project Overview</h3>
<p>This project is a basic client-server chat application built in Java. It allows two-way communication between a client and a server using sockets, with multi-threading to handle simultaneous reading and writing of messages.</p>

<h3>How It Works</h3>
<p>Server: Listens on a specified port, accepts incoming client connections, and facilitates message exchange.</p>
<p>Client: Connects to the server using its IP address and port number, allowing users to send and receive messages in real-time.</p>
<h3>Key Features</h3>
<p>Multi-threading: Separate threads are used for reading and writing, enabling simultaneous message exchange.</p>
<p>Error Handling: The client handles connection errors, unknown hosts, and I/O exceptions gracefully.</p>
<p>Simple Interface: Easy-to-use console input/output for sending and receiving messages.</p>
<p>Exit Mechanism: Typing "exit" closes the connection cleanly on both server and client sides.</p>

 
  <h2>How to Run</h2>
  <h3>Compile the Server and Client:</h3>
  <pre><code>javac Server.java
   javac Client.java</code></pre>
    <h3>Start the Server:</h3>
    <pre><code>java Server</code></pre>
    <p>The server will start listening on the specified port.</p>

  <h3>Run the Client:</h3>
    <pre><code>java Client</code></pre>
    <p>When prompted, enter the server's IP address and port number to connect.</p>

  <h2>Chat:</h2>
    <p>
        Messages sent from the client are received by the server and vice versa. Type "exit" to close the connection.
    </p>

   <h2>Error Handling</h2>
    <p>
        The client provides feedback if it cannot connect to the server or if the server closes the connection unexpectedly. Common errors such as "Connection refused" or "Unknown host" are handled gracefully with clear messages.
    </p>

  <h2>Technologies Used</h2>
    <ul>
        <li>Java Sockets for network communication.</li>
        <li>Multi-threading to manage reading and writing concurrently.</li>
        <li>Java I/O for input and output handling.</li>
    </ul>

   <h2>Future Improvements</h2>
    <ul>
        <li>Adding a GUI for a more user-friendly chat interface.</li>
        <li>Implementing authentication and encryption for secure communication.</li>
    </ul>

   <h1>Output</h1>

![Screenshot (36)](https://github.com/user-attachments/assets/e212211e-fdeb-4016-8079-786a366feffc)


![Screenshot (35)](https://github.com/user-attachments/assets/0df79626-f195-4fc2-a4d4-856707dd258b)
