package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class OperationPage extends JFrame{

    ArrayList<String> last_operations_arr_list;

    public OperationPage(ArrayList<String> param_lo_arr_list) {
        last_operations_arr_list = param_lo_arr_list;
        setTitle("Operation Page");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(20, 24, 54));

        JPanel panel = createCenterPanel();
        add(panel);
        add(createLeftPanel());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(35, 39, 64));
        centerPanel.setBounds(280, 20, 400, 520);
        centerPanel.setLayout(new BorderLayout());
        
        // etiket
        JLabel portfolioLabel = new JLabel("PORTFOLIO", JLabel.CENTER);
        portfolioLabel.setForeground(Color.WHITE);
        portfolioLabel.setFont(new Font("Arial", Font.BOLD, 18));
        centerPanel.add(portfolioLabel, BorderLayout.NORTH);
        
        // İşlemler paneli
        JPanel showOperationPanel = new JPanel();
        showOperationPanel.setBackground(new Color(35, 39, 64));
        showOperationPanel.setLayout(new BorderLayout());
    
        // Tüm işlemler için JTextArea
        JTextArea operationItem = new JTextArea();
        operationItem.setForeground(Color.WHITE);
        operationItem.setFont(new Font("Arial", Font.ITALIC, 14));
        operationItem.setBackground(new Color(35, 39, 64));
        operationItem.setEditable(false);  
        operationItem.setLineWrap(true);   // Satır kaydırma özelliğini açma
        operationItem.setWrapStyleWord(true);
    
        // Son 50 işlemi JTextArea içine ekleme //buraya yapılan işlemin tarihi detaylaı gösterilmeli yakından uzaga sort edilmeli 
        for (int i = 1; i <= last_operations_arr_list.size(); i++) {
            operationItem.append(i + " " + last_operations_arr_list.get(i) + "\n");
        }
    
        //  JScrollPane ekleme
        JScrollPane scrollPane = new JScrollPane(operationItem);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null); 
        showOperationPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(showOperationPanel, BorderLayout.CENTER);
        operationItem.setCaretPosition(0); //scroll bunu koymazsan sonda kalıyor 

        return centerPanel;
    }

    private JPanel createLeftPanel () {
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(35, 39, 64));
        leftPanel.setBounds(20, 20, 250, 320);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(75, 25)); 
        backButton.addActionListener(e -> {
            try {
                homePage();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        leftPanel.add(backButton);


        return leftPanel;
    }

    private void homePage() throws Exception {
        myGui gui = new myGui();
        gui.showGui();
        this.dispose();
    }
    
    
}

