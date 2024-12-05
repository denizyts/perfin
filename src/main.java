import contoller.controller;

import org.checkerframework.checker.units.qual.A;

import contoller.client_controller;
import gui.myGui;

public class main {
    
     public static void main(String[] args) throws Exception {

        try{
       
        controller AcontrollerObj = new client_controller();
        //controller = new controller();
        myGui bismillah = new myGui(AcontrollerObj);
        bismillah.showGui();
    }
    catch (Exception e){
        System.out.println("An error occured: " + e.getMessage());
    }
    }
}
