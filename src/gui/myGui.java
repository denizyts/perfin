package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class myGui extends JFrame {

    ArrayList<String> arr_list_last_operations;

    public myGui() {
        arr_list_last_operations = new ArrayList<String>();
    }

    public void showGui() {
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

    public void set(ArrayList<String> param_last_operations){
        this.arr_list_last_operations = param_last_operations;
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
                g.fillArc(50, 30, 200, 200, 0, 180); // THYAO: 50%
                g.setColor(Color.GREEN);
                g.fillArc(50, 30, 200, 200, 180, 72); // NVDA: 20%
                g.setColor(Color.CYAN);
                g.fillArc(50, 30, 200, 200, 252, 108); // GOLD: 10%
            }
        };
        pieChartPanel.setPreferredSize(new Dimension(300, 200));
        pieChartPanel.setBackground(new Color(35, 39, 64));
        JButton RefreshButton = new JButton("Refresh");
        RefreshButton.setPreferredSize(new Dimension(200, 25)); 
        RefreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RefreshGui();
            }
        });
        pieChartPanel.add(RefreshButton , BorderLayout.SOUTH);
        centerPanel.add(pieChartPanel, BorderLayout.CENTER);
    
        
        
        JPanel operationPanel = new JPanel();
        operationPanel.setBackground(new Color(35, 39, 64));
        operationPanel.setLayout(new BoxLayout(operationPanel, BoxLayout.Y_AXIS));
    
        JLabel operationLabel = new JLabel("        ---Last 5 Operations---", JLabel.CENTER);
        operationLabel.setForeground(Color.WHITE);
        operationLabel.setFont(new Font("Arial", Font.BOLD, 22));
        operationPanel.add(operationLabel);
        
    
        // Son 5 işlemi simüle etme
        for (int i = 0; i < 5 ; i++) {
            if(i > arr_list_last_operations.size() - 1) 
            {
                 break;
            }
            JLabel operationItem = new JLabel( i+1 + ". "+ arr_list_last_operations.get(i) , JLabel.CENTER);
            operationItem.setForeground(Color.WHITE);
            operationItem.setFont(new Font("Arial", Font.PLAIN, 14));
            operationPanel.add(operationItem);
        }
    
        centerPanel.add(operationPanel, BorderLayout.SOUTH);
    
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(35, 39, 64)); 
        buttonPanel.setForeground(new Color(0, 31, 63));

        JButton openNewPageButton = new JButton("More Operations");
        openNewPageButton.setPreferredSize(new Dimension(200, 25)); 
        openNewPageButton.addActionListener(e -> openNewOperationPage(new ArrayList<String>()));
        buttonPanel.add(openNewPageButton);
    
        centerPanel.add(buttonPanel, BorderLayout.SOUTH); 
    
        JPanel mainOperationPanel = new JPanel();
        mainOperationPanel.setLayout(new BorderLayout());
        mainOperationPanel.add(operationPanel, BorderLayout.NORTH);
        mainOperationPanel.add(buttonPanel, BorderLayout.SOUTH);
        centerPanel.add(mainOperationPanel, BorderLayout.SOUTH);

        return centerPanel;
    }

    private void openNewOperationPage(ArrayList<String> arrayList) {
        new OperationPage(arrayList); 
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

    private void RefreshGui() {
    arr_list_last_operations.add("New Operation"); 
    
    getContentPane().removeAll();

    JPanel leftPanel = createLeftPanel();
    add(leftPanel);

    JPanel centerPanel = createCenterPanel();
    add(centerPanel);

    JPanel rightPanel = createRightPanel();
    add(rightPanel);

    getContentPane().revalidate();
    getContentPane().repaint();
}

    }

