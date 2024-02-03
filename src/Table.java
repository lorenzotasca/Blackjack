import java.awt.*;
import java.awt.geom.AffineTransform;

import javax.swing.*;

public class Table extends JPanel{

    //private JPanel tablePanel;
    //private JLabel[] cardLabels;

    public Table(Container container){
        //setSize(1200, 700);
        setVisible(true);
        setBackground(Color.GRAY);
        
    }

    @Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setColor(new Color(0, 170, 0));
        // Draw a semicircle using drawArc
        g2d.fillArc(0,0, 700, 600, -180, 180);

        // porta fiche
        g2d.setColor(Color.BLACK);
        g2d.fillRect(250, 300, 200, 80);

        
        // porta carte
        // Save the current transform
        AffineTransform old = g2d.getTransform();

        int radius = 250;
        // Draw 7 rectangles around the semicircle
        for (int i = 0; i < 7; i++) {
            /// Calculate the angle and position for each rectangle
            double angle = Math.toRadians(30 + (120 / 6 * i));
            int x = 350 + (int) (radius * Math.cos(angle));
            int y = 270 + (int) (radius * Math.sin(angle));

            // Rotate the Graphics2D context around the center of each rectangle
            g2d.rotate(angle, x, y);

            g2d.setColor(Color.BLACK);
            g2d.drawRoundRect(x, y-20, 60, 40, 20, 20);

            // Restore the old transform
            g2d.setTransform(old);
        }
        
        

        // porta mazzi (Shuffler Mate)
        g2d.setColor(Color.LIGHT_GRAY);

        // Save the current transform
        AffineTransform old2 = g2d.getTransform();

        // Translate to the center of the rectangle
        g2d.translate(465 + 45 / 2, 330 + 30 / 2);

        // Rotate the Graphics2D context
        g2d.rotate(Math.toRadians(300));

        // Draw the rectangle centered at the origin
        g2d.fillRect(-45 / 2, -50 / 2, 60, 45);

        // Restore the old transform
        g2d.setTransform(old2);


    }


}

