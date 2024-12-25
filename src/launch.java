

public class launch {
    
    public static void main(String[] args) throws Exception {

        try{
       
        controller AcontrollerObj = new client_controller();
        AcontrollerObj = new server_controller();
        myGui startGuiObj = new myGui(AcontrollerObj);
        startGuiObj.showGui();
    }
    catch (Exception e){
        System.out.println("An error occured: " + e.getMessage());
    }
    }
}

