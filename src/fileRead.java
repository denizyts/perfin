import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class fileRead {
    

    public fileRead() {
        
    }

    public void read() {
      try{
        File file = new File("src/tuprs.csv");
        Scanner sc = new Scanner(new File("src/tuprs.csv")).useDelimiter(";");
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            System.out.println(line);
        }
        sc.close();
    }
    catch (FileNotFoundException e) {
        System.out.println("File not found");
    }
    catch (Exception e) {
        System.out.println("An error occurred");
    }
  }
    
}


