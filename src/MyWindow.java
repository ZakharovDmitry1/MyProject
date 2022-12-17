import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MyWindow extends JPanel {
    int width, height;
    BufferedImage image;
    Graphics2D graphics2D;

    boolean[][] grow;
    JFrame window;

    public MyWindow(int width, int height) {
        this.window = new JFrame();
        this.window.setSize(width, height);
        Dimension dimension = new Dimension();
        dimension.height = height;
        dimension.width = width;
        this.window.setMaximumSize(dimension);
        this.window.setMinimumSize(dimension);
        this.width = width;
        this.height = height;
        this.window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.grow = new boolean[width][height];
        System.out.println(grow);
        fillGrow();
        first_draw_color();
        this.window.pack();
        setCircle(400, 400, 50);
    }

    public void fillGrow() {
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                if (j > 250) {
                    this.grow[i][j] = true;
                } else
                    this.grow[i][j] = false;
            }
        }
    }

    public void setCircle(int xcoord, int ycoord, int radius) {
        for (int i = xcoord - radius;i <= xcoord + radius; i++) {
            for (int j = ycoord - radius; j <= ycoord + radius; j++) {
                if ((i - xcoord) * (i - xcoord) + (j - ycoord) * (j - ycoord) <= radius){
                    this.grow[i][j] = false;
                    byte alpha = 0;
                    int color = this.image.getRGB(i, j);
                    int mc = (alpha << 24) | 0x00ffffff;
                    int newcolor = color & mc;
                    this.image.setRGB(i, j, newcolor);

                }
            }
        }
    }

    public void first_draw_color() {
        try {
            this.image = ImageIO.read(new File("Data/Resurses/img.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.graphics2D = (Graphics2D) this.image.getGraphics();
        this.graphics2D.dispose();
        ImageIcon icon = new ImageIcon(this.image);
        this.window.add(new JLabel(icon));
        byte alpha = 0;
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                if (!this.grow[i][j]) {
                    int color = this.image.getRGB(i, j);

                    int mc = (alpha << 24) | 0x00ffffff;
                    int newcolor = color & mc;
                    this.image.setRGB(i, j, newcolor);
                }
            }
        }
    }

    public void firstDrawGrow() {
        this.image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        this.graphics2D = (Graphics2D) this.image.getGraphics();
        this.graphics2D.setColor(Color.white);
        Color n = new Color(0, 255, 0, 255);
        this.graphics2D.setColor(n);
        //this.graphics2D.setColor(Color.green);
        //this.graphics2D.drawLine(0, 0, 640, 480);
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                if (this.grow[i][j])
                    this.graphics2D.fillRect(i, j, 1, 1);
            }
        }
        this.graphics2D.dispose();
        ImageIcon icon = new ImageIcon(this.image);
        this.window.add(new JLabel(icon));
        File outputfile = new File("grow.png");
        try {
            ImageIO.write(this.image, "png", outputfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void setVisible(boolean f) {
        if (f)
            this.window.setVisible(true);
        else
            this.window.setVisible(false);


    }

    public void drawGrow(Graphics g) {


    }

    public void paintComponent(Graphics g) {

    }
}
