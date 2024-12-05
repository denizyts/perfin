package socket;
import java.net.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.*;

public class server_test {

  
    public server_test(int port) {

        try (ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Server başladi");

            while (true) {
                Socket clientSocket = serverSocket.accept();  
                System.out.println("Bağlanildi"+ clientSocket.getInetAddress());          
                
    
                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

                clientSocket.close();

            }
            
        } catch(Exception e) {
            System.out.println("Server sorunu");
            e.printStackTrace();
        }
       
    }

    public static void main(String[] args) {
        new server_test(3131);
    }
    
        
    

}
