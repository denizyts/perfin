import java.sql.*;
import java.util.ArrayList;

class db_funcs{  
 
    insert_last_operations ilo_obj;

    public db_funcs(){
        this.ilo_obj = new insert_last_operations();
    }

        
    public int buy_stock(String symbol, Double price, int quantity)
    throws amountNegativeException, priceNegativeException, InvalidSymbolException{

      if (quantity <= 0){throw new amountNegativeException("Amount cannot be negative or zero.");}
      if (price <= 0){throw new priceNegativeException("Price cannot be negative or zero.");}

      try (
        Connection connection = DriverManager.getConnection("jdbc:sqlite:deniz.db");
      ){


      //if not valid symbol exception throwed at func.
      boolean exists_valid = validanceExistenceCheck(symbol, connection); 
      
     
     ResultSet rs = executeSpecialQueryReturnsRS("select * from portfolio" , connection);
      while(rs.next()){
          if(rs.getString("symbol").equals(symbol)){
            int amount = rs.getInt("amount");
            double avgPrice = rs.getDouble("avgPrice");
            int newAmount = amount + quantity;
            double newAvgPrice = ((avgPrice * amount) + (price * quantity)) / newAmount;
            String updateQuery = "update portfolio set amount = " + newAmount + ", avgPrice = " + newAvgPrice + " where symbol = '" + symbol + "'";
            executeSpecialQuery(updateQuery, connection);
          }
      }

      //if not in portfolio insert the symbol
      if (!exists_valid){  
        String insertQuery = "insert into portfolio (symbol, amount, avgPrice) values ('" + symbol + "', " + quantity + ", " + price + ")";
        executeSpecialQuery(insertQuery, connection); 
      }

      this.ilo_obj.setValues(symbol, quantity, price, "BUY");
      this.ilo_obj.start(); //start threading.
      return 1;

    } 
    catch(SQLException e)
    {
      e.printStackTrace();
    }
    return 0;
    }



    public int sell_stock(String symbol , Double price , int quantity)
      throws InvalidSymbolException, symbolNotSellable, amountNegativeException, 
      priceNegativeException, invalidQuantityException, SQLException, invalidSideException{

        if (quantity <= 0){throw new amountNegativeException("Amount cannot be negative or zero.");}
        if (price <= 0){throw new priceNegativeException("Price cannot be negative or zero.");}

        try (
        Connection connection = DriverManager.getConnection("jdbc:sqlite:deniz.db");
      ){

         //if not valid symbol exception throwed at func.
         //if in portfolio sellable else not sellable
         boolean exists_valid = validanceExistenceCheck(symbol, connection); 
         System.out.println(exists_valid);

         if(!exists_valid){
          throw new symbolNotSellable(symbol + " is not sellable - Not exsits in portfolio");
         }
      
         ResultSet rs = executeSpecialQueryReturnsRS("select * from portfolio" , connection);
         while(rs.next()){
          if(rs.getString("symbol").equals(symbol)){
            int amount = rs.getInt("amount");
            double avgPrice = rs.getDouble("avgPrice");
            
            if(amount < quantity){
              throw new invalidQuantityException("you have " + amount + " " + symbol + " but you want to sell " + quantity);
            }

            int newAmount = amount - quantity;
            if (newAmount == 0 ){
              String deleteQuery = "delete from portfolio where symbol = \"" + symbol + "\"";
              executeSpecialQuery(deleteQuery, connection);
            }
            else{
              String updateQuery = "update portfolio set amount = " + newAmount + ", avgPrice = " + avgPrice + " where symbol = '" + symbol + "'";
              executeSpecialQuery(updateQuery, connection);
            }
            
            this.ilo_obj.setValues(symbol, quantity, price, "SELL");
            this.ilo_obj.start(); //start threat 
            return 1;
          }
      }
      return 0;
    }
  }


    
  public ResultSet executeSpecialQueryReturnsRS(String query, Connection connection) throws SQLException {
    Statement statement = connection.createStatement();
    return statement.executeQuery(query);
  }
  


  public void executeSpecialQuery(String query, Connection connection) throws SQLException{  //for insert, update, delete queries. 
    Statement statement = connection.createStatement();
    statement.executeUpdate(query);   //executeUpdate if query does not return rs.
}


  //returns true if exists in portfolio, both sell buy funcs calls this. 
  public boolean validanceExistenceCheck(String symbol, Connection connection)throws InvalidSymbolException , SQLException{
    //Bu query zor bir query.
    String symbolsQuery = "select exists(select 1 symbol from symbols where symbol = \"" + symbol + "\")";
    ResultSet rs_symbols = executeSpecialQueryReturnsRS(symbolsQuery, connection);
    boolean symbolExists = false;

     while(rs_symbols.next()){    //sembol portfolioda var mı diye bakıyorum.
      if (rs_symbols.getInt(1) == 1){
        symbolExists = true;
      }
    }
    if(!symbolExists){
      throw new InvalidSymbolException(symbol + " is invalid");
    }

     String existenceCheckQuery = "select exists(select 1 from portfolio where symbol = \"" + symbol + "\")";
     //String a  = "select symbol from portfolio"; //deneme için 
     System.out.println(existenceCheckQuery); 

     //connection parametre olması lazım çünkü rs ile takılırken connection kapanmamalı.
     ResultSet rs_deniz = executeSpecialQueryReturnsRS(existenceCheckQuery , connection);  
     boolean exists = false;

     if (!rs_deniz.isBeforeFirst()) {  //chatgptden aldım rs boş mu diye bakmak için
      System.out.println("No rows in the result set!");
  }
  
      while(rs_deniz.next()){    //sembol portfolioda var mı diye bakıyorum.
        if (rs_deniz.getInt(1) == 1){
          exists = true;
        }
      }
      System.out.println(exists); 

    return exists;
  }

}

class getDataFromdb {
    
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
class insert_last_operations extends Thread{
    
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
        } catch (SQLException | invalidSideException e) { //çoklu istisna yakalama (multi-catch)
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

class candleField {
    
    private String date;
    private Double close_price;

    public candleField(String date, Double close_price){

        this.date = date; 
        this.close_price = close_price;
    }

    public String getDate(){
        return this.date;
    }

    public Double getClose_price(){
        return this.close_price;
    }
}