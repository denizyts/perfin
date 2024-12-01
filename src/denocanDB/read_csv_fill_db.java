package denocanDB;
import java.util.HashMap;

public class read_csv_fill_db {
    public static void main(String[] args) {

        String[] symbols = {"doas" , "garan" , "isctr" , "kchol" , "pgsus" , "sahol" , "thyao"};

        HashMap<String , String[]> hm = new HashMap<String , String[]>();
        
        fileRead fr = new fileRead();
        String extension = "csv/";
        String file_name = "";

        for (String symbol : symbols){
            file_name = extension+symbol+".csv";
            System.out.println(file_name);
            hm.put(symbol, fr.read(file_name));
        }

        int ctr = 0;
        for (String symbol : symbols){
            System.out.println(symbol + " check:");
        for (String line : hm.get(symbol)){
            if(line == null){break;}
            System.out.println(line);
            ctr++;
            if(ctr > 2){break;}
        }
        ctr=0;
    }

        db_util_stock_data myHandler = new db_util_stock_data(symbols);

        myHandler.add_db(hm);
        

    }
}


