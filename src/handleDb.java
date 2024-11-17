import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;


public class handleDb {  

    String[] symbols;

    public handleDb(String[] symbols){
        this.symbols = symbols;
    }

    
    public void add_db( HashMap<String, String[]> hm ){

        try
    (
      Connection connection = DriverManager.getConnection("jdbc:sqlite:deniz.db");
      Statement statement = connection.createStatement();
    )
    {
      

        for (String symbol : symbols) {
            String[] values = hm.get(symbol);
            if (values == null) { break; }

            String dropTableQuery = "drop table if exists " + symbol;
            statement.executeUpdate(dropTableQuery);

            String createTableQuery = "create table " + symbol + " (date text unique, close_price real)";
            statement.executeUpdate(createTableQuery);

            for (String value : values){
                
                if (value == null) { break; }
                if(value == values[0]){continue;}

                String date = value.split("," )[0];
                double close_price = Double.parseDouble(value.split(",")[1]);

                String insertQuery = "INSERT INTO " + symbol + " (date, close_price) VALUES ('" + date + "', " + close_price + ")";
                statement.executeUpdate(insertQuery);
            }
        }

    }
    catch(SQLException e)
    {
      // if the error message is "out of memory",
      // it probably means no database file is found
      e.printStackTrace(System.err);
    }
    }
    
    
}
