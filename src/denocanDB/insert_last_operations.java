package denocanDB;
import java.util.*;
import exceptions.*;
import exceptions.invalidSideException;
import java.sql.*;


public class insert_last_operations extends Thread{
    
    private String symbol;
    private Integer amount;
    private Double price;
    private String side;

    public insert_last_operations() {
        System.out.println("Insert last operations helper object instanciated");
    }

    public void setValues(String symbol, Integer amount, Double price, String side){
        this.symbol = symbol;
        this.amount = amount;
        this.price = price;
        this.side = side;
    }


    @Override
    public void run(){
        try {
            insert_function(symbol, amount, price, side);
        } catch (SQLException | invalidSideException e) {
            System.err.println("Error in thread " + symbol + ": " + e.getMessage());
        }
    }

    
    public int insert_function(String symbol, Integer amount, Double price, String side) 
    throws SQLException, invalidSideException {

        if(side != "BUY" && side != "SELL"){
            throw new invalidSideException(side + " invalid Side must be either BUY OR SELL !");
        }

        try(
        Connection connection = DriverManager.getConnection("jdbc:sqlite:deniz.db"); 
        Statement statement = connection.createStatement();
        ) 
        {
            
            String insertQuery = "insert into last_operations (symbol, date, amount, price, side) values ( ";
            insertQuery += "\"" + symbol + "\", \"" + getCurrentDateTime() + "\", \"" + amount + "\", \"" + price + "\", \"" + side + "\")";
            System.out.println(insertQuery);

            statement.executeUpdate(insertQuery);
            
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return 0;
    }


    public String getCurrentDateTime() {
        //time .now function, directly taken from ai.
        String str = java.time.LocalDateTime.now().toString();
        str = str.substring(0, 10) + " Hour: " + str.substring(11, 16);
        return str;
    }

}
