package mainApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Class: Chromosome
 * 
 * @author AJ Hedden Alex Brickley Hollin Glaze
 *         Defines visual components such as color, index
 *         Drawn to place on screen and create array of lists of x(list of 1s
 *         and zeros
 *         that define color)
 *         Chromosome can be visualized based on this class
 */

public class Chromosome {

    Color color = Color.GREEN;

    private int width;
    private int actualX;
    private int actualY;
    private int num;
    private String filename;
    Graphics2D g2;

    public Chromosome(int num, int index, int width, int initialX, int initialY) {
        this.num = num;
        this.width = width;
        if (num == 2) {
            color = Color.BLUE;
        } else if (num == 1) {
            color = Color.GREEN;
        } else if (num == 0) {
            color = Color.BLACK;
        }
        this.actualX = initialX + index % 10 * width;
        this.actualY = initialY + (index / 10) * width;
    }

    public void swapColor(int x, int y) {
        if (x > actualX && x < actualX + width && y > actualY && y < actualY + width) {
            System.out.println(color);
            if (this.color == Color.BLACK) {
                this.color = Color.GREEN;
                Rectangle rect = new Rectangle(this.actualX, this.actualY, width, width);
                g2.setColor(color);
                g2.fill(rect);
            } else {
                this.color = Color.BLACK;
                Rectangle rect = new Rectangle(this.actualX, this.actualY, width, width);
                g2.setColor(color);
                g2.fill(rect);
                System.out.println("here");
            }
        }
    }
    //Changes color of chromosome

    public void drawOn(Graphics g) {
        this.g2 = (Graphics2D) g;
        Rectangle rect = new Rectangle(this.actualX, this.actualY, width, width);
        g2.setColor(color);
        g2.fill(rect);
    }
    //Draws chromosome

    public void setNum(int num) {
        this.num = num;
    }
    //Sets the gene

    public int getNum() {
        return this.num;
    }
    //Gets a gene
    public void setFile(String filename) {
        this.filename = filename;
    }
    //Sets file
}