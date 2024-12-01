package denocanDB;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class fileRead {
    

    public fileRead() {
      
    }

    public String[] read(String path_file) {
      String[] return_arr = new String[501];
      try{
        int ctr = 0;
        Scanner sc = new Scanner(new File(path_file)).useDelimiter(";");
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            return_arr[ctr] = line; ctr++;
            if(ctr==50){
              System.out.println(line);
            }
            
        }
        sc.close(); 
    }
    catch (FileNotFoundException e) {
        System.out.println("File not found");
    }
    catch (Exception e) {
        System.out.println("An error occurred");
        e.printStackTrace();
    }
    return return_arr;
  }
    
}


