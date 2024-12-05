package contoller;
import java.util.ArrayList;
import java.util.HashMap;
import newsApi.*;
import gui.*;

public abstract class controller {
    
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
