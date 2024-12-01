import denocanDB.getDataFromdb;
import gui.myGui;

public class main {
    
     public static void main(String[] args) throws Exception {

        try{
        getDataFromdb db_getter = new getDataFromdb();
        myGui bismillah = new myGui();
        //bismillah.setLastOperations(db_getter.get_last_operations());
        System.out.println(db_getter.get_portfolio().get(0));
        bismillah.showGui();
    }
    catch (Exception e){
        System.out.println("An error occured: " + e.getMessage());
    }
    }
}
