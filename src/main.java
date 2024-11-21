public class main {
    public static void main(String[] args) throws Exception {
        

        String[] symbols = {"doas" , "garan" , "isctr" , "kchol" , "pgsus" , "sahol" , "thyao"};

        db_funcs myDb_funcs = new db_funcs(symbols);

        myDb_funcs.buy_stock("thyao", 100.0, 10);



    }
}


