import java.util.HashMap;

public class main {
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

        for (String line : hm.get("doas")){
            if(line == null){break;}
            //System.out.println(line);
        }

        handleDb myHandler = new handleDb(symbols);

        myHandler.add_db(hm);
        

    }
}


