package denocanDB;
import java.util.ArrayList;
import java.sql.*;

public class getDataFromdb {
    
    public getDataFromdb(){}


    public ArrayList<String> get_last_operations() throws SQLException{

        ArrayList<String> arrayList = new ArrayList<String>(); 

        try(
        Connection connection = DriverManager.getConnection("jdbc:sqlite:deniz.db");
        Statement statement = connection.createStatement();
        )
        {
            String getQuery = "select * from last_operations";
            ResultSet rs = statement.executeQuery(getQuery);

            while(rs.next()){
                String total = "";
                String symbol = rs.getString(1);
                String date = rs.getString(2);
                Integer amount = rs.getInt(3);
                Double price = rs.getDouble(4);
                String side = rs.getString(5);

                total = symbol + " || " + date + " || " + amount + " || " + price + " || " + side;
                arrayList.add(total);
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }
    
    public ArrayList<String> get_portfolio() throws SQLException{

        ArrayList<String> arrayList = new ArrayList<String>(); 

        try(
        Connection connection = DriverManager.getConnection("jdbc:sqlite:deniz.db");
        Statement statement = connection.createStatement();
        )
        {
            String getQuery = "select * from portfolio";
            ResultSet rs = statement.executeQuery(getQuery);

            while(rs.next()){
                String total = "";
                String symbol = rs.getString(1);
                Integer amount = rs.getInt(2);
                Double price = rs.getDouble(3);
                
                total = symbol + " " + amount + " " + price;
                arrayList.add(total);
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }
    
    public ArrayList<String> get_symbols() throws SQLException{

        ArrayList<String> arrayList = new ArrayList<String>(); 

        try(
        Connection connection = DriverManager.getConnection("jdbc:sqlite:deniz.db");
        Statement statement = connection.createStatement();
        )
        {
            String getQuery = "select * from symbols";
            ResultSet rs = statement.executeQuery(getQuery);

            while(rs.next()){
                
                String symbol = rs.getString(1);
                
                String total = symbol; 
                arrayList.add(total);
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }

    public ArrayList<candleField> getHistoricalPriceData(String symbol)throws SQLException{

        ArrayList<candleField> arrayList = new ArrayList<candleField>(); 

        try(
        Connection connection = DriverManager.getConnection("jdbc:sqlite:deniz.db");
        Statement statement = connection.createStatement();
        )
        {
            String getQuery = "select * from " + symbol;
            ResultSet rs = statement.executeQuery(getQuery);

            while(rs.next()){
                
                String date = rs.getString(1);
                Double close_price = rs.getDouble(2);

                arrayList.add(new candleField(date, close_price));
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return arrayList;

    }

}
