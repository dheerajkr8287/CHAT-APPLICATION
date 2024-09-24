
import java.net.*;
import java.io.*;
import java.util.*;
public class Server {
    ServerSocket server;
    Socket socket;
    BufferedReader br;  //read 
    PrintWriter out;    //write
    public Server(int port){
        try {
            // Create a server socket listening on port 7777
            server =new ServerSocket(port);
            System.out.println("Server is ready to accept connection");
            System.out.println("Waiting....");
            //accepting an incoming client connection
            socket=server.accept();
            System.out.println("Connection established with client: " + socket.getRemoteSocketAddress());


            //read the data from client 
            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //write data for send the client
            out= new PrintWriter(socket.getOutputStream());



            startReading();
            startWriting();

            

        } catch (Exception e) {
            System.out.println("Error:"+e.getMessage());
        }

    }

    public void startReading(){
        //yaha par multithreading ka use isiliye hua hai kyoki bar bar data ko read aur write karna hai ek sath ek hi bar mai
        //thread ->read karke deta rakhega jo client se data ayega

        Runnable r1=()->{
            
            System.out.println("reader started ..");
            try{
            while (true) {
                
                String msg=br.readLine();
                if(msg.equals("exit")){
                    System.out.println("Client terminated to chat");
                    socket.close();
                    break;
                }
                System.out.println("Client:"+msg);
            
           
                
            }
        }
        catch(Exception e){
            System.out.println("Connection is closed..");

        }
        };
        new Thread(r1).start();

    }

    public void startWriting(){
        //thread->data user se  lega  and usko send karega client tak baar baar
        Runnable r2=()->{
            System.out.println("writer started....");
            try{
            while (!socket.isClosed()) {
                
                    BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
                    String content=br1.readLine();
                
                    out.println(content);
                    out.flush();

                    if(content.equals("exit")){
                        socket.close();
                        break;
                    }


                    
               
                
            }
        }catch(Exception e){
                        System.out.println("Connection is closed..");

        }

            
        };
        new Thread(r2).start();  //for start thread

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Enter the port number for the server to listen on:");
        int port = sc.nextInt();
        System.out.println("This is server .. going to start server");
        new Server(port);
        } catch (Exception e) {
            System.out.println("check your input entries type.");
        }
        

       
        
    }
    
}
