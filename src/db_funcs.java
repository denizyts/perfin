import java.lang.Thread.State;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import exceptions.*;


public class db_funcs{  

    String[] symbols;

    public db_funcs(String[] symbols){
        this.symbols = symbols;
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
              return 1;
          }
      }

      if (!exists_valid){
        String insertQuery = "insert into portfolio (symbol, amount, avgPrice) values ('" + symbol + "', " + quantity + ", " + price + ")";
        executeSpecialQuery(insertQuery, connection);
        return 1;
      }

    } 
    catch(SQLException e)
    {
      e.printStackTrace();
    }
    return 0;
    }

    public int sell_stock(String symbol , Double price , int quantity){



      return 0;
    }


    
    public ResultSet executeSpecialQueryReturnsRS(String query, Connection connection) throws SQLException {
      Statement statement = connection.createStatement();
      return statement.executeQuery(query);
  }
  


  public void executeSpecialQuery(String query, Connection connection) throws SQLException{  //for insert, update, delete queries. 
    Statement statement = connection.createStatement();
    statement.executeUpdate(query);
}


  public boolean validanceExistenceCheck(String symbol, Connection connection)throws InvalidSymbolException , SQLException{
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
     //String deno = "select symbol from portfolio"; //deneme için 
     System.out.println(existenceCheckQuery);  //denizTesting

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
      System.out.println(exists);  //denizTesting

    return exists;
  }

}