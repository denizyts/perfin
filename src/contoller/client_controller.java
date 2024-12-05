package contoller;

import socket.*;
import gui.*;
import newsApi.*;
import java.util.ArrayList;
import java.util.HashMap;


public class client_controller extends controller {

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
        @SuppressWarnings("unchecked")   //bunu vscode koydu anlamadım :))
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
