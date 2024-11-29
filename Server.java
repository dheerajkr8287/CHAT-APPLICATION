import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Server {
    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    public Server(int port) {
        try {
            server = new ServerSocket(port);
            System.out.println("Server is ready to accept connections...");
            System.out.println("Waiting...");
            socket = server.accept();
            System.out.println("Connection established with client: " + socket.getRemoteSocketAddress());

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void startReading() {
        Runnable r1 = () -> {
            System.out.println("Reader started...");
            try {
                while (true) {
                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("Client terminated the chat.");
                        socket.close();
                        break;
                    }
                    System.out.println("Client: " + msg);
                }
            } catch (Exception e) {
                System.out.println("Connection is closed...");
            }
        };
        new Thread(r1).start();
    }

    public void startWriting() {
        Runnable r2 = () -> {
            System.out.println("Writer started...");
            try {
                while (!socket.isClosed()) {
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();

                    if (content.equals("exit")) {
                        socket.close();
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Connection is closed...");
            }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Enter the port number for the server to listen on:");
            int port = sc.nextInt();
            System.out.println("This is the server... Starting server...");
            new Server(port);
        } catch (Exception e) {
            System.out.println("Check your input entries.");
        }
    }
}
