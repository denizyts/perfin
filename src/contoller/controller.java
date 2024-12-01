package contoller;
import exceptions.*;
import gui.*;
import denocanDB.*;
import java.util.HashMap;
import java.sql.SQLException;
import java.util.ArrayList;

public class controller {
    
    public void refreshGui(myGui myGui) throws Exception {
        try{
            getDataFromdb db_getter = new getDataFromdb();
            myGui.setLastOperations(db_getter.get_last_operations());
            myGui.setPortfolio(db_getter.get_portfolio());
            myGui.setStocks(db_getter.get_symbols().toArray(new String[db_getter.get_symbols().size()])); 
            myGui.setPercentageMap(percentageCalculator());
            
        }
        catch (Exception e){
            throw new Exception("An error occured: " + e.getMessage());
        }

    }

    public HashMap<String, Double> percentageCalculator(){
        HashMap<String, Double> percentageMap = new HashMap<String, Double>();
        getDataFromdb db_getter = new getDataFromdb();

        try {
            ArrayList<String> arrayList = db_getter.get_portfolio();
            Double total = 0.0;
            for (String s : arrayList) {
                String[] arr = s.split(" ");
                int amount = Integer.parseInt(arr[1]);
                Double price = Double.parseDouble(arr[2]);
                total += amount * price;
            }
            for (String s : arrayList) {
                String[] arr = s.split(" ");
                String symbol = arr[0];
                int amount = Integer.parseInt(arr[1]);
                Double price = Double.parseDouble(arr[2]);
                Double percentage = (amount * price / total) * 100;
                percentageMap.put(symbol, percentage);    //.intValue() can be used to take int value of Double but need to change hm also.
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        

        return percentageMap;
    }
    
}
