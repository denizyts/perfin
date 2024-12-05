package socket;
import java.io.*;
import java.net.*;
import java.util.*;
import contoller.server_controller;

public class server {
    
    public static void main(String[] args) {
        new server(3132);
    }

    public server(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Server başladi");

            while (true) {
                Socket clientSocket = serverSocket.accept();  
                System.out.println("Bağlanildi"+ clientSocket.getInetAddress());          
                

                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                ObjectOutputStream objectOutput = new ObjectOutputStream(clientSocket.getOutputStream());

                String methodName = input.readLine();
                int argCount = Integer.parseInt(input.readLine());   //how many arguments will be sent :))
                String[] args = new String[argCount];                //args created :))
                for (int i = 0; i < argCount; i++) {
                    args[i] = input.readLine();
                }

                System.out.println("REQUESTTTTTTT DENOCANSERVER");
                System.out.println("Method Name: " + methodName);
                System.out.println("Arguments: " + Arrays.toString(args));
                System.out.println("--------------------------------------");


                Object result = callController(methodName, args);
                objectOutput.writeObject(result);

            }
            
        } catch(Exception e) {
            System.out.println("Server Problem: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Object callController(String methodName, String[] args){
        server_controller controller = new server_controller();
        try {
            switch (methodName) {
                case "buyOperation":
                    return controller.buyOperation((String)args[0], Double.parseDouble(args[1]), Integer.parseInt(args[2]));
                case "sellOperation":
                    return controller.sellOperation((String)args[0], Double.parseDouble(args[1]), Integer.parseInt(args[2]));
                case "percentageCalculator":
                    return controller.percentageCalculator();
                case "getHistPriceData":
                    return controller.getHistPriceData((String)args[0]);
                case "getLastOperations":
                    return controller.getLastOperations();
                case "getPortfolio":
                    return controller.getPortfolio();
                case "getStocks":   
                    return controller.getStocks();
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
        
    }

    


    
}
