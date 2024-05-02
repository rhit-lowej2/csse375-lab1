package mainApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.*;

import javax.swing.JComponent;

/**
 * Class: ChromosomeComponent
 * 
 * @author AJ Hedden Alex Brickley Hollin Glaze
 *         Stores a list of genes to construct our entire chromosome.
 * 
 *         Stores the ideal chromosomes that the user can choose from at runtime
 *
 *         Ability to calculate fitnesses of the chromosome as well as draw
 *         itself.
 *
 */

public class ChromosomeComponent extends JComponent {

    private Stack<List<Chromosome>> history = new Stack<List<Chromosome>>();
    private List<Chromosome> chromosomes = new ArrayList<Chromosome>();

    private final int CHROMOSOME_LENGTH = 100;
    private String fitMethod = "Smiley";
    private FitnessMethod fitnessMethod = new StringCompareFitnessMethod("Smiley");
    private double rate;
    private int chromWidth = 30;
    private int chromIndex = 1;
    private int initalX = 1 / 55;
    private int initialY = 50;
    private int[] finalArray = new int[1000];

    public ChromosomeComponent() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        int len = 100;
        if (fitMethod.equals("twenty 1s")) {
            len = 20;
        }
        for (int i = 0; i < Math.min(len, chromosomes.size()); i++) {
            chromosomes.get(i).drawOn(g);
        }
    }
    //Tells the chromosomes to paint themselves

    public void drawBest(Graphics g, int gene, int index) {
        Graphics2D g2 = (Graphics2D) g;
        if (gene == 1) {
            g2.setColor(Color.GREEN);
        } else {
            g2.setColor(Color.BLACK);
        }
        Rectangle rect = new Rectangle(600 + index % 10 * 20, 100 + (index / 10) * 20, 20, 20);
        g2.fill(rect);

        if (index == 0) {

            g2.setColor(Color.BLACK);
            g2.drawString("Current Best Fit: ", 650, 80);
            g2.drawString("Target Chromosome: ", 650, 380);
            for (int i = 0; i < CHROMOSOME_LENGTH; i++) {
                g2.setColor(fitnessMethod.getColorAtIndex(i));
                Rectangle act = new Rectangle(600 + i % 10 * 20, 400 + (i / 10) * 20, 20, 20);
                g2.fill(act);
            }
        }
    }
    //Draws the best chromosome and target chromosome on the right of the screen

    public void setGenes(int[] genes) {
        for (int i = 0; i < genes.length; i++) {
            chromosomes.add(new Chromosome(genes[i], i, chromWidth, initalX + (chromIndex % 10 * 55),
                    initialY + 55 * (chromIndex / 10)));
        }
    }
    //Sets the genes of an entire chromosome

    public void setGeneration(int[] genetics, int index, FitnessMethod method) {
        this.fitnessMethod = method;
        this.chromIndex = index;
        this.chromWidth = 5;

        if (!method.isDebug()) {
            setGenes(genetics);
        } else {
            int[] actGenes = new int[20];
            for (int i = 0; i < 20; i++) {
                actGenes[i] = genetics[i];
            }
            setGenes(genetics);
        }
    }
    //Calls setGenes, sets some basic parameters, distinguishes between some of the methods and changes length if needed

    public void writeFile(String filename) {
        chromosomes.get(0).setFile(filename);
        repaint();
    }
    //Sets file for milestone 1

    public void mutate(double rate, double random) {
        saveState();
        this.rate = rate / 100; // Turn rate to a percentage
        for (Chromosome chromosome : chromosomes) {
            if (Math.random() < this.rate) { // If the mutate rate is higher than the random number, switch gene
                if (chromosome.getNum() == 1) {
                    chromosome.color = Color.BLACK;
                    chromosome.setNum(0);
                } else {
                    chromosome.color = Color.GREEN;
                    chromosome.setNum(1);
                }
            }
        }
        repaint();
    }
    //Mutates each chromosome at a given rate
    public void saveState() {
        ArrayList<Chromosome> copy = new ArrayList<>();
        for (Chromosome c : chromosomes) {
            copy.add(c.copy());
        }
        history.push(copy);
    }
    public void undo() {
        chromosomes = history.pop();
        repaint();
    }

    public ChromosomeInfo getInfo() {

        boolean done = false;
        int crisper = 0;
        int totalOnes = 0;
        int totalZeros = 0;
        int totalQuestions = 0;
        int num;
        for (Chromosome chromosome : chromosomes) {
            num = chromosome.getNum();
            if (num == 0) {
                totalZeros++;
            } else if (num == 1) {
                totalOnes++;
            } else {
                totalQuestions++;
            }
        }

        for (int k = 1000; k > 0; k--) {
            for (Chromosome chromosome : chromosomes) {
                num = chromosome.getNum();
                if (num == 0) {
                    done = true;
                    break;
                }
                if (num == 2) {
                    if (Math.random() <= 0.5) {
                        break;
                    }
                }
                crisper++;
                if (crisper == chromosomes.size() - 1) {
                    ChromosomeInfo info = new ChromosomeInfo(1 + ((19 * k) / 1000), k, totalZeros, totalOnes, totalQuestions);
                    return info;
                }
            }
            if (done) {
                break;
            }
        }
        ChromosomeInfo info = new ChromosomeInfo(1, 0, totalZeros, totalOnes, totalQuestions);
        return info;
    }
    //Calculates all of the data and information needed for graphing info on milestone 4

    public void changeColor(int x, int y) {
        for (int i = 0; i < chromosomes.size(); i++) {
            chromosomes.get(i).swapColor(x, y);
        }
    }
    //Swaps the color of a gene when called

    public int[] getGenes() {
        int len = chromosomes.size();
        if (chromosomes.size() > 1000) {
            len = 1000;
        }
        for (int i = 0; i < len; i++) {
            if (chromosomes.get(i).getNum() == 1) {
                finalArray[i] = 1;
            } else {
                finalArray[i] = 0;
            }
        }
        return finalArray;
    }
    //returns an array of 1s and 0s, the genes.

    public int calc0s() {
        return getInfo().getZeros();
    }
    //Gives 0s in the milestone 4 genes

    public int calc1s() {
        return getInfo().getOnes();
    }
    //Gives 1s in the milestone 4 genes

    public int calc2s() {
        return getInfo().getNaNs();
    }
    //Gives ?s in the milestone 4 genes

    public int calcTotalFitness(FitnessMethod fitnessMethod) {
        this.fitnessMethod = fitnessMethod;
        return fitnessMethod.calcTotalFitness(chromosomes);
    }

    public List<Chromosome> getChromosomes() {
        return chromosomes;
    }
}
