import exceptions.*;
import gui.*;
import denocanDB.*;

public class controller {
    
    public static void main(String[] args) throws Exception {

        try{
        getDataFromdb db_getter = new getDataFromdb();
        myGui bismillah = new myGui();
        bismillah.set(db_getter.get_last_operations());
        bismillah.showGui();
    }
    catch (Exception e){
        System.out.println("An error occured: " + e.getMessage());
    }
    }

    public void refresh() {
        
    }
    
}
