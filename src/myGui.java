import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.event.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import java.awt.*;
import java.awt.image.BufferedImage;


class myGui extends JFrame {

    ArrayList<String> arr_list_last_operations;
    ArrayList<String> arr_list_portfolio;
    HashMap<String, Double> percentageMap;
    String[] stocks;
    controller controllerObj;
    ArrayList<newsField> newsList;

    public myGui(controller param_controller) throws Exception {
        controllerObj = param_controller; 
        //controllerObj = new controller();
        controllerObj.refreshGui(this);
    }

    public void showGui() throws Exception {
        setTitle("Stock Dashboard");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(20, 24, 54));

        try{

        JPanel leftPanel = createLeftPanel();
        add(leftPanel);

        JPanel centerPanel = createCenterPanel();
        add(centerPanel);

        JPanel rightPanel = createRightPanel();
        add(rightPanel);

        setLocationRelativeTo(null); // screen center
        setVisible(true);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }


    // to refresh 
    public void setLastOperations(ArrayList<String> param_last_operations){
        this.arr_list_last_operations = param_last_operations;
    }
    public void setPortfolio(ArrayList<String> param_portfolio){
        this.arr_list_portfolio = param_portfolio;
    }
    public void setStocks(String[] param_stocks){
        this.stocks = param_stocks;
    }
    public void setPercentageMap(HashMap<String, Double> param_percentageMap){
        this.percentageMap = param_percentageMap;
    }
    public void setNewsList(ArrayList<newsField> param_newsList){
        this.newsList = param_newsList;
    }

    private JPanel createLeftPanel() throws Exception {
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(35, 39, 64));
        leftPanel.setBounds(20, 20, 300, 520);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        
        JLabel stockLabel = new JLabel("Select Stock");
        stockLabel.setForeground(Color.WHITE); 
        stockLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(stockLabel); 
        
        JComboBox<String> stockDropdown = new JComboBox<>(stocks);
        stockDropdown.setMaximumSize(new Dimension(100, 30)); 
        stockDropdown.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(stockDropdown);

        stockDropdown.addActionListener(e -> {
            String selectedStock = (String) stockDropdown.getSelectedItem();
            if (!"Select Stock".equals(selectedStock)) {
                try {
                    openTradePage(selectedStock);
                } catch (Exception e1) {
                    e1.printStackTrace();
                } 
            }
        });

        //leftPanel.add(Box.createRigidArea(new Dimension(0, 20))); 
    
        leftPanel.add(Box.createRigidArea(new Dimension(0, 200))); // to create space between dropdown and other content
    
        JPanel chartPanel = new JPanel();
        chartPanel.setPreferredSize(new Dimension(300, 200));
        chartPanel.setBackground(new Color(35, 39, 64));
        chartPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.WHITE), "BIST100", 2, 2, 
            new Font("Arial", Font.BOLD, 12), Color.WHITE));

        BufferedImage chartImage = new chartGenerator("bist100").createChart();
        JLabel chartLabel = new JLabel();
        chartLabel.setIcon(new ImageIcon(chartImage));
        chartLabel.setBounds(20, 20, 270, 180);
        chartPanel.add(chartLabel);
        

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
            
            // I looked from google and I needed to be overridden for the pie chart.
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int startAngle = 0;
                int segmentCount = percentageMap.size();
                ArrayList<Color> colorList = generateColors(segmentCount);

                //when i used stocks array it gave null pointer exception because some symbols are not in portfolio :))
                String[] portfolio_stocks = percentageMap.keySet().toArray(new String[0]);
                for (int i = 0; i < segmentCount; i++) {

                    Double value = percentageMap.get(portfolio_stocks[i]);
                    String name = portfolio_stocks[i];
                    int angle = (int) Math.round((double) value / 100 * 360);

                    g.setColor(colorList.get(i));
                    g.fillArc(50, 30, 200, 200, startAngle, angle);

                    startAngle += angle;

                    g.setColor(colorList.get(i)); //not necessary

                    DecimalFormat df = new DecimalFormat("#.##");  // 2 decimal from gpt

                    int index = 0;

                    for(int j = 0 ; j < stocks.length ; j++){   //i found the index of the stock in the portfolio array
                        if(name.equals(arr_list_portfolio.get(j).split(" ")[0])){
                            index = j;
                            break;
                        }
                    }

                    int quantity = Integer.parseInt(arr_list_portfolio.get(index).split(" ")[1]);
                    double price = Double.parseDouble(arr_list_portfolio.get(index).split(" ")[2]);
                    String value_for_draw = name.toUpperCase() + " %" + df.format(value) + " You have:"+ quantity +" Average:"+ df.format(price);
                    g.drawString(value_for_draw, 35, 250 + i * 15);
                    
                }
            }
        };
        pieChartPanel.setPreferredSize(new Dimension(300, 390));
        pieChartPanel.setBackground(new Color(35, 39, 64));
        centerPanel.add(pieChartPanel, BorderLayout.CENTER);
    

        JButton RefreshButton = new JButton("Refresh");
        RefreshButton.setPreferredSize(new Dimension(200, 25)); 
        RefreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    RefreshGui();
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            }
        });
        pieChartPanel.add(RefreshButton , BorderLayout.SOUTH);
        centerPanel.add(pieChartPanel, BorderLayout.NORTH);
    
        
        
        JPanel operationPanel = new JPanel();
        operationPanel.setBackground(new Color(35, 39, 64));
        operationPanel.setLayout(new BoxLayout(operationPanel, BoxLayout.Y_AXIS));
    
        JLabel operationLabel = new JLabel("     ---Last 5 Operations---", JLabel.CENTER);
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
            operationItem.setFont(new Font("Arial", Font.PLAIN, 10));
            operationPanel.add(operationItem);
        }
    
        centerPanel.add(operationPanel, BorderLayout.CENTER);
    
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(35, 39, 64)); 
        buttonPanel.setForeground(new Color(0, 31, 63));

        JButton openNewPageButton = new JButton("More Operations");
        openNewPageButton.setPreferredSize(new Dimension(200, 25)); 
        openNewPageButton.addActionListener(e -> {
            try {
                openNewOperationPage();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        buttonPanel.add(openNewPageButton);
    
        centerPanel.add(buttonPanel, BorderLayout.SOUTH); 
    
        JPanel mainOperationPanel = new JPanel();
        mainOperationPanel.setLayout(new BorderLayout());
        mainOperationPanel.add(operationPanel, BorderLayout.NORTH);
        mainOperationPanel.add(buttonPanel, BorderLayout.SOUTH);
        centerPanel.add(mainOperationPanel, BorderLayout.SOUTH);

        return centerPanel;
    }

    private void openNewOperationPage() throws Exception {
        new OperationPage(this.controllerObj); 
        this.dispose(); // to close existing page 
    }

    private void openTradePage(String stock) throws Exception {
        new TradePage(stock , this.controllerObj);
        this.dispose();
    }


    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(35, 39, 64));
        rightPanel.setBounds(660, 20, 300, 520);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        JLabel newsLabel = new JLabel("NEWS");
        newsLabel.setForeground(Color.WHITE);
        newsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        newsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(newsLabel);

        // Input area for topic
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(35, 39, 64));
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
        inputPanel.setMaximumSize(new Dimension(280, 30)); // Adjust to fit within the panel

        JTextField topicField = new JTextField();
        topicField.setMaximumSize(new Dimension(200, 30));
        topicField.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton submitButton = new JButton("Search");
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setBackground(new Color(255, 87, 34));
        submitButton.setForeground(Color.black);

        inputPanel.add(topicField);
        inputPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Add spacing between components
        inputPanel.add(submitButton);

        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(inputPanel);

        // News container setup
        JPanel newsContainer = new JPanel();
        newsContainer.setBackground(new Color(35, 39, 64));
        newsContainer.setLayout(new BoxLayout(newsContainer, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(newsContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(280, 200));
        scrollPane.setBounds(660, 80, 300, 520);
        rightPanel.add(scrollPane);

        // Add action listener for the button
        submitButton.addActionListener(e -> {
            String topic = topicField.getText();
            if (!topic.isEmpty()) {
                try {
                    newsList = controllerObj.fetchNews(topic);
                    if(newsList.size() == 0){
                        errorPopup(new Exception("No news found for the topic"));
                    }
                    newsContainer.removeAll();
                    for(int i = 0 ; i < newsList.size() ; i++){
                        //System.out.println(newsList.get(i).getTitle());
                        addNewsItem(newsContainer, newsList.get(i).getTitle(), newsList.get(i).getUrl_str());
                    }
                   
                } catch (Exception e1) {
                    e1.printStackTrace();
                    errorPopup(e1);
                }
            } else {
                errorPopup(new emptyStringException("Please enter a topic"));

            }
        });

      
        return rightPanel;
    }

    private void addNewsItem(JPanel container, String title, String url) {
        JPanel newsItem = new JPanel();
        newsItem.setBackground(new Color(35, 39, 64));
        newsItem.setLayout(new BoxLayout(newsItem, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        newsItem.add(titleLabel);

        //makes url clickable
        //https://stackoverflow.com/questions/527719/how-to-add-hyperlink-in-jlabel
        JLabel urlLabel = new JLabel("<html><a href='" + url + "'>" + url + "</a></html>");
        urlLabel.setForeground(Color.CYAN);
        urlLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        urlLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        //opens the url in the default browser :))
        urlLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    Desktop.getDesktop().browse(new java.net.URI(url));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        newsItem.add(urlLabel);

        newsItem.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        container.add(newsItem);
        container.revalidate();
        container.repaint();
    }

    private void RefreshGui() throws Exception {
        controllerObj.refreshGui(this);
        
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

private ArrayList<Color> generateColors(int segmentCount) {
    ArrayList<Color> colors = new ArrayList<>();
    
    Color[] predefinedColors = {Color.GREEN, Color.RED, Color.BLUE, 
        Color.MAGENTA, Color.ORANGE, Color.GRAY , Color.LIGHT_GRAY , Color.DARK_GRAY  , Color.CYAN , Color.WHITE}; 
    
    for (int i = 0; i < segmentCount; i++) {
        colors.add(predefinedColors[i % predefinedColors.length]); //if segment is greater than colors
    }
    return colors;
}

private void errorPopup(Exception e) {
    JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}
    }

class OperationPage extends JFrame{

        ArrayList<String> last_operations_arr_list;
        controller controllerObj;
    
        public OperationPage(controller controller_param) throws Exception {
            
            this.controllerObj = controller_param;
            //controller controllerObj = new controller();
            controllerObj.refreshOperationPage(this);
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
    
        public void setLastOperations(ArrayList<String> param_last_operations){
            this.last_operations_arr_list = param_last_operations;
        }
    
        private JPanel createCenterPanel() {
            JPanel centerPanel = new JPanel();
            centerPanel.setBackground(new Color(35, 39, 64));
            centerPanel.setBounds(280, 20, 400, 520);
            centerPanel.setLayout(new BorderLayout());
            
            JLabel portfolioLabel = new JLabel("Last Operations Log", JLabel.CENTER);
            portfolioLabel.setForeground(Color.WHITE);
            portfolioLabel.setFont(new Font("Arial", Font.BOLD, 18));
            centerPanel.add(portfolioLabel, BorderLayout.NORTH);
            
            JPanel showOperationPanel = new JPanel();
            showOperationPanel.setBackground(new Color(35, 39, 64));
            showOperationPanel.setLayout(new BorderLayout());
        
            // creating text area for last operations
            JTextArea operationItem = new JTextArea();
            operationItem.setForeground(Color.WHITE);
            operationItem.setFont(new Font("Arial", Font.ITALIC, 14));
            operationItem.setBackground(new Color(35, 39, 64));
            operationItem.setEditable(false);  
            operationItem.setLineWrap(true);   // Satır kaydırma özelliğini açma
            operationItem.setWrapStyleWord(true);
        
            for (int i = 0; i < last_operations_arr_list.size(); i++) {
                operationItem.append(i+1 + " " + last_operations_arr_list.get(i) + "\n");
            }
        
            
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
            myGui gui = new myGui(controllerObj);
            gui.showGui();
            this.dispose();
        }
        
        
    }    



    class TradePage extends JFrame{

        String symbol;
        controller controllerObj;
    
    
        public TradePage (String stock , controller controller_param) throws Exception { 
            this.controllerObj = controller_param;
            //controllerObj = new controller();
            this.symbol = stock;

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
        private JPanel createLeftPanel() throws Exception {
            JPanel leftPanel = new JPanel();
            leftPanel.setBackground(new Color(35, 39, 64));
            leftPanel.setBounds(20, 20, 620, 520);
            leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
    
            JButton backButton = new JButton("Back");
            backButton.setPreferredSize(new Dimension(75, 25)); 
            backButton.addActionListener(e -> {     //lambda expression cant throw an exception :)
                try {
                    homePage();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });
            leftPanel.add(backButton);
    
            BufferedImage chartImage = new chartGenerator(symbol).createChart();
            JLabel chartLabel = new JLabel();
            chartLabel.setIcon(new ImageIcon(chartImage));
            leftPanel.add(chartLabel);
    
    
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
    
            JLabel countLabel = new JLabel("Buy Quantity");
            countLabel.setForeground(Color.WHITE);
            countLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            countLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
            rightPanel.add(countLabel);
    
            JSpinner StockSpinner = new JSpinner(new SpinnerNumberModel(1, -100000, 1000000, 1)); 
            JFormattedTextField stockField = ((JSpinner.NumberEditor) StockSpinner.getEditor()).getTextField();
            ((NumberFormatter) stockField.getFormatter()).setAllowsInvalid(false); // Geçersiz girişleri engelle
            StockSpinner.setMaximumSize(new Dimension(250, 30)); 
            rightPanel.add(StockSpinner);
    
            rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    
            JLabel salePriceLabel = new JLabel("Buy Price");
            salePriceLabel.setForeground(Color.WHITE);
            salePriceLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            salePriceLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
            rightPanel.add(salePriceLabel);
    
            JSpinner PriceSpinner = new JSpinner(new SpinnerNumberModel(1, -100000, 1000000, 0.1)); 
            JFormattedTextField priceField = ((JSpinner.NumberEditor) PriceSpinner.getEditor()).getTextField();
            ((NumberFormatter) priceField.getFormatter()).setAllowsInvalid(false); // Geçersiz girişleri engelle
            PriceSpinner.setMaximumSize(new Dimension(250, 30)); 
            rightPanel.add(PriceSpinner);
            
            rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    
            JButton submitButton = new JButton("BUY");
            submitButton.setPreferredSize(new Dimension(75, 25)); 
            submitButton.setAlignmentX(Component.CENTER_ALIGNMENT); 
            submitButton.addActionListener(e -> {
                int res = 0;
                try{
                    
                    res = controllerObj.buyOperation(symbol, (Double)PriceSpinner.getValue(), (Integer)StockSpinner.getValue());
                }
                catch (Exception e1){
                    e1.printStackTrace();
                    errorPopup(e1);
                }
                if(res == 1){
                    successPopup("Buy");
                }
                if(res == 0){
                    errorPopup(new Exception("INVALID PRICE OR QUANTITY PLEASE CHECK"));
                }
            });
            rightPanel.add(submitButton);
    
            return rightPanel;
        }
    
        private JPanel createRightDownPanel() {
            JPanel rightPanel = new JPanel();
            rightPanel.setBackground(new Color(35, 39, 64));
            rightPanel.setBounds(660, 280, 300, 260);
            rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
            rightPanel.add(Box.createRigidArea(new Dimension(0, 20))); 
        
            JLabel addLabel = new JLabel("ADD SELL");
            addLabel.setForeground(Color.WHITE);
            addLabel.setFont(new Font("Arial", Font.BOLD, 18));
            addLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
            rightPanel.add(addLabel);
        
            rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    
            JLabel countLabel = new JLabel("Sale Quantity");
            countLabel.setForeground(Color.WHITE);
            countLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            countLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
            rightPanel.add(countLabel);
    
            JSpinner StockSpinner = new JSpinner(new SpinnerNumberModel(1, -100000, 100000, 1)); 
            JFormattedTextField stockField = ((JSpinner.NumberEditor) StockSpinner.getEditor()).getTextField();
            ((NumberFormatter) stockField.getFormatter()).setAllowsInvalid(false); // Geçersiz girişleri engelle
            StockSpinner.setMaximumSize(new Dimension(250, 30)); 
            rightPanel.add(StockSpinner);
    
            rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    
            JLabel salePriceLabel = new JLabel("Sale Price");
            salePriceLabel.setForeground(Color.WHITE);
            salePriceLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            salePriceLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
            rightPanel.add(salePriceLabel);
    
            JSpinner PriceSpinner = new JSpinner(new SpinnerNumberModel(1, -100000, 10000, 0.1)); 
            JFormattedTextField priceField = ((JSpinner.NumberEditor) PriceSpinner.getEditor()).getTextField();
            ((NumberFormatter) priceField.getFormatter()).setAllowsInvalid(false); // Geçersiz girişleri engelle
            PriceSpinner.setMaximumSize(new Dimension(250, 30)); 
            rightPanel.add(PriceSpinner);
            
            rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    
    
            JButton submitButton = new JButton("SELL");
            submitButton.setPreferredSize(new Dimension(75, 25)); 
            submitButton.setAlignmentX(Component.CENTER_ALIGNMENT); 
            submitButton.addActionListener(e -> {
                int res = 0;
                try{
                    res = controllerObj.sellOperation(symbol, (Double)PriceSpinner.getValue(), (Integer)StockSpinner.getValue());
                }
                catch (Exception e1){
                    e1.printStackTrace();
                    errorPopup(e1);
                }
                if(res == 1){
                    successPopup("Sell");
                }
                else if(res == 0){
                    errorPopup(new Exception("INVALID PRICE OR QUANTITY PLEASE CHECK"));
                }
    
            });
            rightPanel.add(submitButton);
            
            return rightPanel;
        }
    
    
        private void homePage() throws Exception {
            myGui mg = new myGui(this.controllerObj);     //for refreshing new object created also refreshes main page.
            mg.showGui();    
            this.dispose();
        }
    
        private void errorPopup(Exception e){
            JOptionPane.showMessageDialog(this, e, "ERROR !", JOptionPane.ERROR_MESSAGE);
        }
    
        private void successPopup(String message_param){
            String message = message_param + " Operation Successful";
            JOptionPane.showMessageDialog(this, message, "SUCCESS !", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    class chartGenerator {

        private Double[] data;
        private String symbol;
    
        public chartGenerator(String symbol) throws Exception {
            server_controller controller = new server_controller();
            this.data = controller.getHistPriceData(symbol);
            this.symbol = symbol;
        }
       
        public BufferedImage createChart() {
            // Create a dataset
            DefaultCategoryDataset dataset = new DefaultCategoryDataset(); //necessary things (x, y) and stores datas
            for (int i = 0; i < data.length; i++) {
                dataset.addValue(data[i], "Grafik", "" + (i + 1));
            }
    
            // Create a line chart
            JFreeChart chart = ChartFactory.createLineChart(
                    symbol,   // Chart title
                    "time",       // X-axis label
                    "price",          // Y-axis label
                    dataset           // Dataset
            );
    
            return chart.createBufferedImage(600, 300);
        }
        
    }