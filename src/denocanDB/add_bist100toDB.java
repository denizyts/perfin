package denocanDB;

import java.sql.*;

public class add_bist100toDB {
    public static void main(String[] args) throws SQLException {

        
        fileRead fr = new fileRead();
        String extension = "csv/";
        String file_name = "";

        file_name = extension+"doas"+".csv";

        String[] data = fr.read(file_name);

        for(String line : data){
            System.out.println(line);
        }

        Connection connection = DriverManager.getConnection("jdbc:sqlite:deniz.db");
        Statement statement = connection.createStatement();

        String dropTableQuery = "drop table if exists " + "bist100";

            statement.executeUpdate(dropTableQuery);

            String createTableQuery = "create table " + "bist100" + " (date text unique, close_price real)";
            statement.executeUpdate(createTableQuery);

            for (String value : data){
                
                if (value == null) { break; }
                if(value == data[0]){continue;}

                String date = value.split("," )[0];
                double close_price = Double.parseDouble(value.split(",")[8]);  //8 because its bist100 close price

                String insertQuery = "INSERT INTO " + "bist100" + " (date, close_price) VALUES ('" + date + "', " + close_price + ")";
                statement.executeUpdate(insertQuery);
            }
            System.out.println("bist100" + " added to db.");
        }


        
    }

  

    

