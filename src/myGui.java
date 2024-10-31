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

        leftPanel.add(Box.createRigidArea(new Dimension(0, 230))); // BIST 100 kutusu konumu için
/*
        JLabel stockPanel = new JLabel("Daily Stock Infos", JLabel.CENTER);
        stockPanel.setPreferredSize(new Dimension(1, 100));
        leftPanel.add(stockPanel);
*/

        JPanel chartPanel = new JPanel();
        chartPanel.setPreferredSize(new Dimension(300, 200));
        chartPanel.setBackground(new Color(35, 39, 64));
        chartPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE), "BIST100", 2, 2, new Font("Arial", Font.BOLD, 12), Color.WHITE));
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
        this.dispose(); //varolan sayfayı kapatıyor
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


//********************************************************************SECOND PAGE**************************************************** */
class OperationPage extends JFrame {
    public OperationPage() {
        setTitle("Operation Page");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(20, 24, 54));

        JPanel leftPanel = createLeftPanel();
        add(leftPanel);
        
        JPanel rightPanel = createRightPanel();
        add(rightPanel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(35, 39, 64));
        leftPanel.setBounds(20, 20, 620, 520);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        return leftPanel;
    }

    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(35,39,64));
        rightPanel.setBounds(660, 20, 300, 520);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        return rightPanel;
    }

}


//****************************************************************************************************************************************** */

class TradePage extends JFrame {
    public TradePage () { //parametre olarak ana sayfadan seçilen hissenin ismi gelmeli ki dataları ona göre çekelim
        
    }
}