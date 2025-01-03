package contoller;
import gui.*;
import denocanDB.*;
import newsApi.*;
import java.util.HashMap;
import java.sql.SQLException;
import java.util.ArrayList;

//this class is only for read from csv bist100 values and write them to bist100 table in db

public class server_controller extends controller {

    
    @Override
    public void refreshGui(myGui myGui) throws Exception {
        try{
            
            myGui.setLastOperations(getLastOperations());
            myGui.setPortfolio(getPortfolio());
            myGui.setStocks(getStocks()); 
            myGui.setPercentageMap(percentageCalculator());
            
        }
        catch (Exception e){
            throw new Exception("error bip bip: " + e.getMessage());
        }

    }

    @Override
    public void refreshOperationPage(OperationPage operationPage) throws Exception {
        try{
        operationPage.setLastOperations(getLastOperations()); 
        }
        catch (Exception e){
            throw new Exception("error bip bip: " + e.getMessage());
        }
    }

    @Override
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

    @Override
    public int buyOperation(String symbol , Double price , int quantity) throws Exception {
        int res;
        try{
            db_funcs db_funcsObj = new db_funcs();
            res = db_funcsObj.buy_stock(symbol, price , quantity);
        }
        catch (Exception e){
            //throw new Exception("error bip bip: " + e.getMessage());
            System.out.println("error bip bip: " );
            e.printStackTrace();
            return 0;
        }
        return res;
       
    }

    
    @Override
    public int sellOperation(String symbol , Double price , int quantity) throws Exception {
        int res;
        try{
            db_funcs db_funcsObj = new db_funcs();
            res = db_funcsObj.sell_stock(symbol, price , quantity);
        }
        catch (Exception e){
            //throw new Exception("error bip bip: " + e.getMessage());
            System.out.println("error bip bip: " );
            e.printStackTrace();
            return 0;

        }
        return res;
        
    }

    @Override
    public Double[] getHistPriceData(String symbol) throws Exception {

        
        getDataFromdb db_funcsObj = new getDataFromdb();
        ArrayList<candleField> arrayList = db_funcsObj.getHistoricalPriceData(symbol);
        Double[] data = new Double[arrayList.size()];

        for(int i = 0 ; i < arrayList.size() ; i++){
            data[i] = arrayList.get(i).getClose_price();
        }

        return data;
    }

    @Override
    public ArrayList<newsField> fetchNews(String keyword) throws Exception {
        newsFetcher fetcher = new newsFetcher();
        return fetcher.fetchNews(keyword);
    }

    @Override
    public ArrayList<String> getLastOperations() throws Exception {
        getDataFromdb db_funcsObj = new getDataFromdb();
        return db_funcsObj.get_last_operations();
    }

    @Override
    public ArrayList<String> getPortfolio() throws Exception {
        getDataFromdb db_funcsObj = new getDataFromdb();
        return db_funcsObj.get_portfolio();
    }

    @Override
    public String[] getStocks() throws Exception {
        getDataFromdb db_funcsObj = new getDataFromdb();
        return db_funcsObj.get_symbols().toArray(new String[db_funcsObj.get_symbols().size()]);
    }
    
}
