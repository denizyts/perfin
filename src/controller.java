import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

abstract class controller {
    
    public abstract void refreshGui(myGui myGui) throws Exception;
    public abstract void refreshOperationPage(OperationPage operationPage) throws Exception;
    public abstract int buyOperation(String symbol , Double price , int amount) throws Exception;
    public abstract int sellOperation(String symbol , Double price , int amount) throws Exception;
    public abstract Double[] getHistPriceData(String symbol) throws Exception;
    public abstract ArrayList<newsField> fetchNews(String keyword) throws Exception;
    public abstract ArrayList<String> getLastOperations() throws Exception;
    public abstract ArrayList<String> getPortfolio() throws Exception;
    public abstract String[] getStocks() throws Exception;
    public abstract HashMap<String, Double> percentageCalculator() throws Exception;

}

class client_controller extends controller {

    client clientObj;
    
    
    public client_controller() {
        clientObj = new client("localhost", 3132);
    }

    @Override
    public void refreshGui(myGui myGui) {
        try {
        
            myGui.setLastOperations(getLastOperations());
            myGui.setPortfolio(getPortfolio());
            myGui.setStocks(getStocks());
            myGui.setPercentageMap(percentageCalculator());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void refreshOperationPage(OperationPage operationPage) {
        try {
            operationPage.setLastOperations(getLastOperations());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public int buyOperation(String symbol, Double price, int amount) throws Exception {
        return (Integer) clientObj.connectGetObj("buyOperation", new String[] {symbol, price.toString(), Integer.toString(amount)});
    }
    
    @Override
    public int sellOperation(String symbol, Double price, int amount) throws Exception {
        Object response = clientObj.connectGetObj("sellOperation", new String[] {symbol, price.toString(), Integer.toString(amount)});
        return (Integer)response;
    }
    
    @Override
    @SuppressWarnings("unchecked")   //vscode put it automatically :))
    public HashMap<String , Double> percentageCalculator() {

        HashMap<String, Double> hm = (HashMap<String, Double>)clientObj.connectGetObj("percentageCalculator", new String[] {});
      /*   for (String key : hm.keySet()) {
            System.out.println(key + " " + hm.get(key));
        }   */ //for testing purposes.

        return hm;
    }
    
    @Override
    public Double[] getHistPriceData(String symbol) {

        Double[] response = (Double[])clientObj.connectGetObj("getHistPriceData", new String[] {symbol});

      /*  for (int i = 0; i < response.length; i++) {
            System.out.println(response[i]);
        }    */    //for testing purposes.
           
        return response;
        
    }
    
    @Override
    public ArrayList<newsField> fetchNews(String keyword) {
        newsFetcher fetcher = new newsFetcher();
        return fetcher.fetchNews(keyword);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<String> getLastOperations() {
        return (ArrayList<String>)clientObj.connectGetObj("getLastOperations", new String[] {});
    }

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<String> getPortfolio() {
        return (ArrayList<String>)clientObj.connectGetObj("getPortfolio", new String[] {});
    }

    @Override
    public String[] getStocks() {
        return (String[])clientObj.connectGetObj("getStocks", new String[] {});
    }

    public static void main(String[] args) {
        client_controller cc = new client_controller();
        //cc.getHistPriceData("doas");
        cc.percentageCalculator();
        cc.getPortfolio();
    }

}

class server_controller extends controller {


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
            percentageMap.put(symbol, percentage); 
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
