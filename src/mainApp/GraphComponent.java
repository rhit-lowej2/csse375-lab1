package mainApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JComponent;
import java.awt.BasicStroke;
import java.awt.Font;
import javax.swing.Timer;
import java.awt.event.ActionListener;

/**
 * Class: GraphComponent
 * 
 * @author AJ Hedden Alex Brickley Hollin Glaze
 *         This class is what we use to draw and keep all the information we need for our graph. 
 *          Our graph takes a lot of information and must keep a list of generations to store
 *          all neccessary details.
 */
public class GraphComponent extends JComponent {

    private ArrayList<Generation> generations = new ArrayList<Generation>();
    private ChromosomeComponent[] survivors;
    private int genSize;
    private static int DELAY = 20;
    private Timer t;
    private static int genIndex = 0;
    private boolean isDrawing = true;
    private String method = "t";
    private boolean crossMe = false;
    private boolean endMax = false;

    private static final Color COLOR_BEST = Color.GREEN;
    private static final Color COLOR_AVERAGE = Color.BLUE;
    private static final Color COLOR_WORST = Color.RED;
    private static final Color COLOR_HAM = Color.YELLOW;
    private static final Color COLOR_CORRECT = Color.MAGENTA;
    private static final Color COLOR_INCORRECT = Color.PINK;
    private static final Color COLOR_UNDECIDED = Color.GRAY;

    public void toggleDrawing(boolean state) {
        isDrawing = state;
    }
    //Tell the component whether it is currently drawing itself or not

    public void compProp(double rate, int popSize, int genSize, String fitMethod) {
        this.genSize = genSize;
    }
    //Set basic instance variables

    public void startDrawing() {
        genIndex = 0;
        t.start();
    }
    //Start drawing/start timer

    private void updateDrawing() {
        if (genIndex < generations.size()) {
            repaint();
            genIndex++;
        } else {
            t.stop();
        }
    }
    //Update the drawing

    public GraphComponent() {
        ActionListener helper = e -> updateDrawing();
        t = new Timer(10, helper);
    }

    public void cross() {
        this.crossMe = true;
    }
    //Set crossover to true

    public void clear() {
        generations.clear();
        repaint();
    }
    //Clear all generations on screen

    private int calcX(int generationIndex) {
        final int horiSpacing = 800 / genSize;
        return 100 + (generationIndex * horiSpacing);
    }
    //Calculate an X value for the graph

    private int calcY(int fitness) {
        final int base = 650;
        final int fitScale = base / 100;
        return base - (fitness * fitScale);
    }
    //Calculate a Y value for the graph

    private void drawFitnessLine(Graphics2D g2, int index, Color color, String fitnessType) {
        g2.setColor(color);
        float thickness = 4f;
        g2.setStroke(new BasicStroke(thickness));
        int x1 = calcX(index - 1);
        int x2 = calcX(index);
        int y1 = 0, y2 = 0;

        if ("best".equals(fitnessType)) {
            y1 = calcY(generations.get(index - 1).getBestFit());
            y2 = calcY(generations.get(index).getBestFit());
        } else if ("average".equals(fitnessType)) {
            y1 = calcY((int) generations.get(index - 1).getAverageFit());
            y2 = calcY((int) generations.get(index).getAverageFit());
        } else if ("worst".equals(fitnessType)) {
            y1 = calcY(generations.get(index - 1).getWorstFit());
            y2 = calcY(generations.get(index).getWorstFit());
        } else if ("ham".equals(fitnessType)) {
            y1 = calcY((int) generations.get(index - 1).getHammingFit());
            y2 = calcY((int) generations.get(index).getHammingFit());
        }
        g2.drawLine(x1, y1, x2, y2);
        repaint();
    }
    //draws the best, worst, average, and hamming lines on the graph


    private void drawNums(Graphics g2, int index) {
    	int popSize = generations.get(generations.size()-1).getPopSize();
        g2.setColor(Color.BLACK);
        int x1 = calcX(index);
        int x2 = calcX(index - 1);
        int[] y1s = generations.get(index).get1s();
        int[] y2s = generations.get(index - 1).get1s();
        int total1 = 0;
        int total2 = 0;
        for (int i = 0; i < y1s.length; i++) {
            total1 += y1s[i];
            total2 += y2s[i];
        }
        double y1 = (total1 * 1.0 / (popSize * 20));
        double y2 = (total2 * 1.0 / (popSize * 20));
        y1 *= 100;
        y2 *= 100;
        g2.drawLine(x1, calcY((int) y1), x2, calcY((int) y2));

        g2.setColor(Color.GREEN);
        total1 = 0;
        total2 = 0;
        y1s = generations.get(index).get0s();
        y2s = generations.get(index - 1).get0s();
        for (int i = 0; i < y1s.length; i++) {
            total1 += y1s[i];
            total2 += y2s[i];
        }
        y1 = (total1 * 1.0 / (popSize * 20));
        y2 = (total2 * 1.0 / (popSize * 20));
        y1 *= 100;
        y2 *= 100;
        g2.drawLine(x1, calcY((int) y1), x2, calcY((int) y2));

        g2.setColor(Color.ORANGE);
        total1 = 0;
        total2 = 0;
        y1s = generations.get(index).get2s();
        y2s = generations.get(index - 1).get2s();
        for (int i = 0; i < y1s.length; i++) {
            total1 += y1s[i];
            total2 += y2s[i];
        }
        y1 = (total1 * 1.0 / (popSize * 20));
        y2 = (total2 * 1.0 / (popSize * 20));
        y1 *= 100;
        y2 *= 100;
        g2.drawLine(x1, calcY((int) y1), x2, calcY((int) y2));
    }
    //Draws lines for 1's, 0's, and unknown alleles on the graph


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!isDrawing) {
            return;
        }
        Graphics2D g2 = (Graphics2D) g;
        float thickness = 4.0f;
        drawGrid(g2);

        g2.setStroke(new BasicStroke(thickness));

        g2.setColor(Color.BLACK);
        g2.drawRect(100, 50, 1100, 600);

        drawBoxes(g);
        drawAxisLabel(g, 600, 20, "Fitness over Generations", false);
        drawAxisLabel(g, 50, 425, "Population", true);

        g2.setColor(Color.LIGHT_GRAY);
    }
    //Tells the graph to paint itself

    private void drawGrid(Graphics2D g2) {
        g2.setColor(Color.BLACK);

        for (int i = 0; i <= 100; i += 10) {
            int yLabel = calcY(i);
            g2.drawLine(100, yLabel, 1200, yLabel);
        }

        int numGenerations = generations.size();
        for (int i = 0; i < numGenerations; i++) {
            int xLabel = calcX(i);
            g2.drawLine(xLabel, 50, xLabel, 650);
        }

        for (int i = 1; i <= genIndex && i < generations.size(); i++) {

            drawFitnessLine(g2, i, COLOR_BEST, "best");
            drawFitnessLine(g2, i, COLOR_AVERAGE, "average");
            drawFitnessLine(g2, i, COLOR_WORST, "worst");
            drawFitnessLine(g2, i, COLOR_HAM, "ham");
            drawNums(g2, i);
            if (i <= 11) {
                g2.setColor(Color.BLACK);
                int currNum = (10 * i) - 10;
                g2.drawString("" + currNum, 70, 660 - (i * 55));
            }

            if (i % generations.size() / 10 == 0) {
                g2.setColor(Color.BLACK);
                g2.drawString("" + i * (genSize / 10), (i * 80) + 100, 670);

            }
        }
    }
    //Draws a grid on the graph making data easier to visualize

    private void drawAxisLabel(Graphics g, int xPosition, int yPosition, String label, boolean isVertical) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setFont(new Font("Default", Font.BOLD, 15));

        if (isVertical) {
            g2.rotate(-Math.PI / 2);
            g2.drawString(label, -yPosition, xPosition);
        } else {

            g2.drawString(label, xPosition, yPosition);
        }
    }
    //Label our axis

    private void drawBoxes(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setFont(new Font("Default", Font.BOLD, 12));

        g2.setColor(Color.BLACK);
        g2.drawString("Average", 1250, 430);
        g2.drawString("Best", 1250, 460);
        g2.drawString("Worst", 1250, 490);
        g2.drawString("Hamming", 1250, 520);
        g2.drawString("1s", 1250, 550);
        g2.drawString("0s", 1250, 580);
        g2.drawString("?s", 1250, 610);

        blackBox(g2, Color.RED, 1220, 480);
        blackBox(g2, Color.BLUE, 1220, 420);
        blackBox(g2, Color.GREEN, 1220, 450);
        blackBox(g2, Color.YELLOW, 1220, 510);
        blackBox(g2, Color.MAGENTA, 1220, 540);
        blackBox(g2, Color.PINK, 1220, 570);
        blackBox(g2, Color.GRAY, 1220, 600);
    }
    //draw the legend for the line colors

    private void blackBox(Graphics2D g2, Color color, int x, int y) {
        g2.setColor(color);
        g2.fillRect(x, y, 20, 20);
        g2.setColor(Color.BLACK);
        g2.drawRect(x, y, 20, 20);
    }
    //Outlines all the previous boxes for added depth

    public void crossover() {
        crossMe = true;
    }
    //Sets crossover to true

    public void setTerminate() {
        this.endMax = true;
    }
    //Tells the program to terminate at 100

    public void addGeneration(Generation g, int genSize) {
    	this.genSize = genSize;
    	this.method = g.getSelection();
    	generations.add(g);
    	repaint();
    }
    
    public void nextGen() {

        for (int i = 1; i < genSize; i++) {
            if (this.method == "t") {
                survivors = generations.get(i - 1).getBest();
            } else {
                survivors = generations.get(i - 1).allOrdered();
            }
            Generation lastGen = generations.get(generations.size()-1);
            Generation newGen = new Generation(survivors, lastGen.getRate(), lastGen.getPopSize(), this.method,
                    lastGen.getElitism(), lastGen.getFitMethod());
            generations.add(newGen);
            if (crossMe == true) {
                int total = 0;
                int[] yess = generations.get(i - 1).get0s();
                for (int j = 0; j < generations.get(i - 1).get0s().length; j++) {
                    total += yess[j];
                }
                generations.get(i - 1).cross();
            }
            repaint();
        }
        repaint();
    }
    //After the first drawing of each has been created, will create the rest of the neccessary generations

    public void drawGeneration(int currentGenerationIndex) {
        throw new UnsupportedOperationException("Unimplemented method 'drawGeneration'");
    }
}
