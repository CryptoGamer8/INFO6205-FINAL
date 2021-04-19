import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Canvas extends JPanel{

    public Canvas() {

    }
    public void paint(Graphics g) {
        try {
            drawCanvas(g);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void drawCanvas(Graphics g) throws IOException {
        Graphics2D g2d = (Graphics2D)g; // Cast to the better G2D interface

        Dimension size = getSize();

        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, size.width, size.height);

        Font f1= new Font("Times New Roman",Font.BOLD,18);
        g2d.setFont(f1);
        g2d.setColor(Color.BLACK);
        g2d.drawString("COVID-19 Simulation", 10, 80);
        g2d.drawString("Team members:", 10, 120);
        g2d.drawString("Xinyi Wu, Ken Zhang, Yumeng Zhang", 10, 150);

        g2d.drawString("Instruction:", 10, 190);
        g2d.drawString("1. Please choose a virus ( COVID-19 / SARS )", 10, 230);
        g2d.drawString("2. Customize settings in the control panel or press Default button", 10, 260);
        g2d.drawString("3. Press Run button ", 10, 290);
        g2d.drawString("4. When pops up appears, Please read the hint carefully and re-enter your input ", 10, 320);
        g2d.drawString("5. Press Clear button to reset parameters", 10, 350);
    }
}
