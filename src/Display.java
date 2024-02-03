import java.awt.*;
import javax.swing.*;

public class Display extends JFrame{

    public Display(){

        setTitle("Blackjack");
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        //setLayout(null);
        setLayout(new BorderLayout());
        //setLocation(0, 0);
        

        Container container=getContentPane();
        container.setLayout(null);
        container.setBackground(Color.GRAY);

        //JPanel tablePanel = new Table(container);
        Table table = new Table(container);
        table.setLayout(new BorderLayout());
        container.add(table, BorderLayout.CENTER);
        table.setBounds(screenSize.width/4, -screenSize.height/7, 700, 600);
        
        
    }

    public static void main(String[] args) {
        //new Card().CreateDesk();
        //new Dealer();
        //new Game();
        //new Card().CreateStructureBunch();
        //new Bunch();
        //new Display();
    }
}
