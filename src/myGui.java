import javax.swing.*;
import java.awt.*;

public class myGui extends JFrame {

    private JFrame frame = new JFrame("My First GUI");

    public myGui() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,800);
        JButton button = new JButton("Press");
        button.setBackground(Color.RED);
        frame.getContentPane().add(button); // Adds Button to content pane of frame
        frame.setVisible(true);
    }


    
}
