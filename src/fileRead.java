import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class fileRead {
    
    String[] return_arr;

    public fileRead() {
      return_arr = new String[501];
    }

    public String[] read(String path_file) {
      try{
        int ctr = 0;
        Scanner sc = new Scanner(new File(path_file)).useDelimiter(";");
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            return_arr[ctr] = line; ctr++;
            //System.out.println(line);
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


