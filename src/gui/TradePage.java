package gui;

import javax.swing.*;
import java.awt.*;


public class TradePage extends JFrame{
    public TradePage (String stock) { //parametre olarak ana sayfadan seçilen hissenin ismi gelmeli ki dataları ona göre çekelim
        setTitle("Operation Page");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(20, 24, 54));

        JPanel leftPanel = createLeftPanel();
        add(leftPanel);
        
        JPanel rightUpPanel = createRightUpPanel();
        add(rightUpPanel);

        JPanel rightDownPanel = createRightDownPanel();
        add(rightDownPanel);

        setLocationRelativeTo(null);
        setVisible(true);
    }
    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(35, 39, 64));
        leftPanel.setBounds(20, 20, 620, 520);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // geri butonu
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(75, 25)); 
        backButton.addActionListener(e -> homePage());
        leftPanel.add(backButton);

        return leftPanel;
    }

    private JPanel createRightUpPanel() {
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(35, 39, 64));
        rightPanel.setBounds(660, 20, 300, 250); 
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        rightPanel.add(Box.createRigidArea(new Dimension(0, 20))); 

        JLabel addLabel = new JLabel("ADD BUY");
        addLabel.setForeground(Color.WHITE);
        addLabel.setFont(new Font("Arial", Font.BOLD, 18));
        addLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(addLabel);

        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // alış adet label 
        JLabel countLabel = new JLabel("Buy Quantity");
        countLabel.setForeground(Color.WHITE);
        countLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        countLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        rightPanel.add(countLabel);

        // alış adet sayaç
        JSpinner StockSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1)); 
        StockSpinner.setMaximumSize(new Dimension(250, 30)); 
        rightPanel.add(StockSpinner);

        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // alış fiyat label 
        JLabel salePriceLabel = new JLabel("Buy Price");
        salePriceLabel.setForeground(Color.WHITE);
        salePriceLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        salePriceLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        rightPanel.add(salePriceLabel);

        // alış adet sayaç
        JSpinner PriceSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 1)); 
        PriceSpinner.setMaximumSize(new Dimension(250, 30)); 
        rightPanel.add(PriceSpinner);
        
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));


        JButton submitButton = new JButton("BUY");
        submitButton.setPreferredSize(new Dimension(75, 25)); 
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT); 
        rightPanel.add(submitButton);

        return rightPanel;
    }

    private JPanel createRightDownPanel() {
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(35, 39, 64));
        rightPanel.setBounds(660, 280, 300, 260);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20))); 
    
        // ekleme label 
        JLabel addLabel = new JLabel("ADD SELL");
        addLabel.setForeground(Color.WHITE);
        addLabel.setFont(new Font("Arial", Font.BOLD, 18));
        addLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        rightPanel.add(addLabel);
    
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // satış adet label 
        JLabel countLabel = new JLabel("Sale Quantity");
        countLabel.setForeground(Color.WHITE);
        countLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        countLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        rightPanel.add(countLabel);

        // satış adet sayaç
        JSpinner StockSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1)); 
        StockSpinner.setMaximumSize(new Dimension(250, 30)); 
        rightPanel.add(StockSpinner);

        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // satış fiyat label 
        JLabel salePriceLabel = new JLabel("Sale Price");
        salePriceLabel.setForeground(Color.WHITE);
        salePriceLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        salePriceLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        rightPanel.add(salePriceLabel);

        // satış adet sayaç
        JSpinner PriceSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 1)); 
        PriceSpinner.setMaximumSize(new Dimension(250, 30)); 
        rightPanel.add(PriceSpinner);
        
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));


        JButton submitButton = new JButton("SELL");
        submitButton.setPreferredSize(new Dimension(75, 25)); 
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT); 
        rightPanel.add(submitButton);
        
        return rightPanel;
    }


    private void homePage() {
        new myGui();
        this.dispose();
    }
}
