package socket;

import java.io.*;
import java.net.*;

public class client {

    String host;
    int port;

    public client(String host, int port) {   //default value 
        host = "localhost";
        port = 3132; 
    }

    public Object connectGetObj(String methodName, String[] args) {

        Object response = null;

        String host = "localhost";
        int port = 3132;

        try (Socket socket = new Socket(host, port)) {

            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());

            /* 
            output.println("buyOperation");
            output.println(3); // Number of arguments
            output.println("doas"); // Argument 1
            output.println("150.0"); // Argument 2
            output.println("10"); // Argument 3      */

           /* 
            output.println("percentageCalculator");
            output.println(0); // Number of arguments  */

            //THIS PART IS VITAL :))
            output.println(methodName);
            output.println(args.length); 
            for (String arg : args) {
                output.println(arg);
            }
        
            response = objectInput.readObject();
            System.out.println("Response from server: " + response);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
        
    }

}
