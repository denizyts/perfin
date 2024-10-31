import javax.swing.*;
import java.awt.*;

public class myGui extends JFrame {

    private JFrame frame = new JFrame("My First GUI");

    public myGui() {
        setTitle("Stock Dashboard");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(20, 24, 54));

        JPanel leftPanel = createLeftPanel();
        add(leftPanel);

        JPanel centerPanel = createCenterPanel();
        add(centerPanel);

        JPanel rightPanel = createRightPanel();
        add(rightPanel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(35, 39, 64));
        leftPanel.setBounds(20, 20, 300, 520);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
    
        String[] stocks = {"Select Stock", "THYAO", "TUPRS", "CATES", "PGSUS"}; //buraya database den hisseler gelmeli
        JComboBox<String> stockDropdown = new JComboBox<>(stocks);
        stockDropdown.setMaximumSize(new Dimension(100, 30)); 
        stockDropdown.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(stockDropdown);

        stockDropdown.addActionListener(e -> {
            String selectedStock = (String) stockDropdown.getSelectedItem();
            if (!"Select Stock".equals(selectedStock)) {
                openTradePage(selectedStock); // Seçilen hisseyi yeni sayfaya gönder
            }
        });

        //leftPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Dropdown ile diğer içerikler arasında boşluk
    
        leftPanel.add(Box.createRigidArea(new Dimension(0, 200))); // BIST 100 konumu 
    
        JPanel chartPanel = new JPanel();
        chartPanel.setPreferredSize(new Dimension(300, 200));
        chartPanel.setBackground(new Color(35, 39, 64));
        chartPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.WHITE), "BIST100", 2, 2, 
            new Font("Arial", Font.BOLD, 12), Color.WHITE));
        

        leftPanel.add(chartPanel);
    
        return leftPanel;
    }
    

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(35, 39, 64));
        centerPanel.setBounds(340, 20, 300, 520);
        centerPanel.setLayout(new BorderLayout());
    
        JLabel portfolioLabel = new JLabel("PORTFOLIO", JLabel.CENTER);
        portfolioLabel.setForeground(Color.WHITE);
        portfolioLabel.setFont(new Font("Arial", Font.BOLD, 18));
        centerPanel.add(portfolioLabel, BorderLayout.NORTH);
    
        JPanel pieChartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.ORANGE);
                g.fillArc(50, 30, 200, 200, 0, 180); // THYAO: 60%
                g.setColor(Color.GREEN);
                g.fillArc(50, 30, 200, 200, 180, 72); // NVDA: 20%
                g.setColor(Color.CYAN);
                g.fillArc(50, 30, 200, 200, 252, 108); // GOLD: 10%
            }
        };
        pieChartPanel.setPreferredSize(new Dimension(300, 200));
        pieChartPanel.setBackground(new Color(35, 39, 64));
        centerPanel.add(pieChartPanel, BorderLayout.CENTER);
    
        
        
        JPanel operationPanel = new JPanel();
        operationPanel.setBackground(new Color(35, 39, 64));
        operationPanel.setLayout(new BoxLayout(operationPanel, BoxLayout.Y_AXIS));
    
        JLabel operationLabel = new JLabel("        ---Last 5 Operations---", JLabel.CENTER);
        operationLabel.setForeground(Color.WHITE);
        operationLabel.setFont(new Font("Arial", Font.BOLD, 18));
        operationPanel.add(operationLabel);
        
    
        // Son 5 işlemi simüle etme
        for (int i = 1; i <= 5; i++) {
            JLabel operationItem = new JLabel( i + ") TUCLK(BUY) - 20 Shares - 130,30   ", JLabel.CENTER);
            operationItem.setForeground(Color.WHITE);
            operationItem.setFont(new Font("Arial", Font.PLAIN, 14));
            operationPanel.add(operationItem);
        }
    
        centerPanel.add(operationPanel, BorderLayout.SOUTH);
    
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(35, 39, 64)); 
        buttonPanel.setForeground(new Color(0, 31, 63));

        JButton openNewPageButton = new JButton("More Operations");
        openNewPageButton.setPreferredSize(new Dimension(75, 25)); 
        openNewPageButton.addActionListener(e -> openNewPage());
        buttonPanel.add(openNewPageButton);
    
        centerPanel.add(buttonPanel, BorderLayout.SOUTH); 
    
        JPanel mainOperationPanel = new JPanel();
        mainOperationPanel.setLayout(new BorderLayout());
        mainOperationPanel.add(operationPanel, BorderLayout.NORTH);
        mainOperationPanel.add(buttonPanel, BorderLayout.SOUTH);
        centerPanel.add(mainOperationPanel, BorderLayout.SOUTH);

        return centerPanel;
    }

    private void openNewPage() {
        new OperationPage(); 
        this.dispose(); 
    }

    private void openTradePage(String stock) {
        new TradePage(stock);
        this.dispose();
    }


    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(35, 39, 64));
        rightPanel.setBounds(660, 20, 300, 520);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        JLabel newsLabel = new JLabel("DAILY NEWS");
        newsLabel.setForeground(Color.WHITE);
        newsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        newsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(newsLabel);

        return rightPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(myGui::new);
    }
}

//********************************************************************OPERATION PAGE**************************************************** */
class OperationPage extends JFrame {
    public OperationPage() {
        setTitle("Operation Page");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(20, 24, 54));

        setLocationRelativeTo(null);
        setVisible(true);
    }

}


//*************************************************************TRADE PAGE************************************************************** */

class TradePage extends JFrame {
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


        return rightPanel;
    }

    private JPanel createRightDownPanel() {
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(35, 39, 64));
        rightPanel.setBounds(660, 280, 300, 260);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Boş alan ekleme
    
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