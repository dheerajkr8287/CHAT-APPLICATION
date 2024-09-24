import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    Socket socket;
    BufferedReader br;  //read 
    PrintWriter out;    //write

    public Client(String serverIP, int port) {
        try {
            System.out.println("Sending request to server at " + serverIP + ":" + port);
            socket = new Socket(serverIP, port);  // Server's IP address and port
            System.out.println("Connection done.");

            // Read the data from the server
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Write data to send to the server
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

        } catch (ConnectException e) {
            System.out.println("Connection failed: " + e.getMessage());
            System.out.println("Please check if the server is running, and that the IP and port are correct.");
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O error...: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startReading() {
        // Multi-threading for reading from the server
        //yaha par multithreading ka use isiliye hua hai kyoki bar bar data ko read aur write karna hai ek sath ek hi bar mai
        //thread ->read karke deta rakhega jo server se data ayega
        Runnable r1 = () -> {
            System.out.println("Reader started...");
            try {
                while (true) {
                    String msg = br.readLine();
                    if (msg == null ||msg.equals("exit")) {
                        System.out.println("Server terminated the chat");
                        socket.close();
                        break;
                    }
                    System.out.println("Server: " + msg);
                }
            } catch (Exception e) {
                System.out.println("Connection is closed...");
            }
        };
        new Thread(r1).start();
    }

    public void startWriting() {
        // Multi-threading for writing to the server
        //thread->data user se  lega  and usko send karega server tak baar baar

        Runnable r2 = () -> {
            System.out.println("Writer started...");
            try {
                while (!socket.isClosed()) {
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();

                    out.println(content);
                    out.flush();

                    if (content.equalsIgnoreCase("exit")) {
                        socket.close();
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        try{
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the server IP address:");
        String serverIP = sc.nextLine();

        System.out.println("Enter the server port number:");
        int port = sc.nextInt();
        

        System.out.println("This is the client...");
        new Client(serverIP, port);
    }catch(Exception e){
        System.out.println("Check your input entries type.");
    }

    }

}


