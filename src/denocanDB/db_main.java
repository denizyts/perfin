package denocanDB;
import java.util.ArrayList;

public class db_main {
    public static void main(String[] args) throws Exception {
        

        String[] symbols = {"doas" , "garan" , "isctr" , "kchol" , "pgsus" , "sahol" , "thyao"};

        db_funcs myDb_funcs = new db_funcs();

        //myDb_funcs.buy_stock("sahol", 22.0, 68);
        //myDb_funcs.sell_stock("isctr", 31.0, 4);

        /* 
        insert_last_operations ulo = new insert_last_operations();
        ulo.setValues("doas", 3, 3.1, "BUY");
        //ulo.insert_function("doas", 3, 3.1, "BUY");
        ulo.start(); */

       /*  getDataFromdb getDbObj = new getDataFromdb();
        ArrayList<String> arrList = getDbObj.get_last_operations();
        arrList = getDbObj.get_symbols();
        arrList = getDbObj.get_portfolio();
        ArrayList<candleField> hist_data_arrList = getDbObj.getHistoricalPriceData("isctr");

        for(int i = 0 ; i < arrList.size() ; i++){
            System.out.println(arrList.get(i));
        }

        for(int i = 0 ; i < hist_data_arrList.size() ; i++){
            System.out.print(hist_data_arrList.get(i).getDate() + " ");
            System.out.print(hist_data_arrList.get(i).getClose_price());
            System.out.println();   */



            myDb_funcs.sell_stock("doas", 1.0, 555555555);
        }
        
    }



