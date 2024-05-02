package mainApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

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

    private ArrayList<Chromosome> chromosomes = new ArrayList<Chromosome>();
    private String slime = "1000000001101111110110101101011010110101101011010110111111011011011101101111110110000000011111111111";
    private String smiley = "1000000001101111110110101101011010110101101011010110111111011010000101101111110110000000011111111111";
    private String grid = "1010101010010101010110101010100101010101101010101001010101011010101010010101010110101010100101010101";
    private String fallen = "0000000000111111111100000000001111111111000000000011111111110000000000111111111100000000001111111111";
    private String marks = "0111111110101111110111011110111110110111111100111111110011111110110111110111101110111111010111111110";
    private String dimiond = "0000110000000111100000111111000111111110111111111111111111110111111110001111110000011110000000110000";
    private String risen = "1111111111000000000011111111110000000000111111111100000000001111111111000000000011111111110000000000";

    private String oneOclock = "1111111111111111111100000000000000000000000000000000000000000000000000000000000000000000000000000000";
    private String twoOclock = "0000000000111111111111111111110000000000000000000000000000000000000000000000000000000000000000000000";
    private String threeOclock = "0000000000000000000011111111111111111111000000000000000000000000000000000000000000000000000000000000";
    private String fourclock = "0000000000000000000000000000001111111111111111111100000000000000000000000000000000000000000000000000";
    private String fiveOclock = "0000000000000000000000000000000000000000111111111111111111110000000000000000000000000000000000000000";
    private String sixOclock = "0000000000000000000000000000000000000000000000000011111111111111111111000000000000000000000000000000";
    private String sevenOclock = "0000000000000000000000000000000000000000000000000000000000001111111111111111111100000000000000000000";
    private String eightOclock = "0000000000000000000000000000000000000000000000000000000000000000000000111111111111111111110000000000";
    private String nineOclock = "0000000000000000000000000000000000000000000000000000000000000000000000000000000011111111111111111111";
    private String tenOclock = "1111111111000000000000000000000000000000000000000000000000000000000000000000000000000000001111111111";

    private String fitMethod = "Smiley";
    private double rate;
    private int[] theseGenes = new int[20];
    private int chromWidth = 30;
    private int chromIndex = 1;
    private int initalX = 1 / 55;
    private int initialY = 50;
    private int count;
    private int[] max = new int[7];
    private int[] finalArray = new int[1000];
    private String[] jackie = new String[] { slime, grid, fallen, marks, smiley, dimiond, risen };
    private int[] dancingOutput = new int[5];

    private int flipper = 0;
    int mystery = 1;
    boolean crack = false;
    double fiddyfiddy = 0.5;
    int crisper = 0;
    int totalZeros = 0;
    int totalOnes = 0;
    int totalQuestions = 0;
    static int whatTimeIsIt = 0;
    String[] timeLevels = new String[] { oneOclock, twoOclock, threeOclock, fourclock, fiveOclock, sixOclock,
            sevenOclock, eightOclock, nineOclock, tenOclock };
    static int massiveClock;

    public ChromosomeComponent() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        int len = 100;
        if (fitMethod.equals("twenty 1s")) {
            len = 20;
        }
        for (int i = 0; i < len; i++) {
            chromosomes.get(i).drawOn(g);
        }
    }
    //Tells the chromosomes to paint themselves

    public int longness() {
        return chromosomes.size();
    }

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
            for (int i = 0; i < smiley.length(); i++) {
                if (fitMethod.equals("Smiley")) {
                    if (smiley.charAt(i) == '1') {
                        g2.setColor(Color.GREEN);
                    } else {
                        g2.setColor(Color.BLACK);
                    }
                } else if (fitMethod.equals("Slime")) {
                    if (slime.charAt(i) == '1') {
                        g2.setColor(Color.GREEN);
                    } else {
                        g2.setColor(Color.BLACK);
                    }
                } else if (fitMethod.equals("All Green")) {
                    g2.setColor(Color.GREEN);
                } else if (fitMethod.equals("Jack")) {
                    if (flipper < 6) {
                        flipper++;
                    } else {
                        flipper = 0;
                    }
                    if (jackie[flipper].charAt(i) == '1') {
                        g2.setColor(Color.GREEN);
                    } else {
                        g2.setColor(Color.BLACK);
                    }
                } else if (fitMethod.equals("Clock")) {
                    if (timeLevels[whatTimeIsIt].charAt(i) == '1') {
                        g2.setColor(Color.GREEN);
                    } else {
                        g2.setColor(Color.BLACK);
                    }
                } else if (fitMethod.equals("All")) {
                    if (jackie[whatTimeIsIt].charAt(i) == '1') {
                        g2.setColor(Color.GREEN);
                    } else {
                        g2.setColor(Color.BLACK);
                    }
                } else if (fitMethod.equals("miniJack")) {
                    if (flipper < 6) {
                        flipper++;
                    } else {
                        flipper = 0;
                    }
                    if (jackie[flipper].charAt(i) == '1') {
                        g2.setColor(Color.GREEN);
                    } else {
                        g2.setColor(Color.BLACK);
                    }
                }

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

    public void setGeneration(int[] genetics, int index, String method) {
        this.fitMethod = method;
        this.chromIndex = index;
        this.chromWidth = 5;

        if (method != "d") {
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

    public void singleChrom() {
        for (int i = 0; i < 20; i++) {
            double random = Math.random();
            if (random < 0.25) {
                theseGenes[i] = 1;
            } else if (random < 0.5) {
                theseGenes[i] = 0;
            } else if (random > 0.5) {
                theseGenes[i] = 2;
            }
        }
        int[] fitnesses = dancing();
        int fitness = fitnesses[0];
    }
    //Creates just 1 chromosome

    public int[] dancing() {

        crack = false;
        crisper = 0;
        totalOnes = 0;
        totalZeros = 0;
        totalQuestions = 0;
        for (Chromosome walking : chromosomes) {
            mystery = walking.getNum();
            if (mystery == 0) {
                totalZeros++;
            } else if (mystery == 1) {
                totalOnes++;
            } else {
                totalQuestions++;
            }
        }

        for (int k = 1000; k > 0; k--) {
            for (Chromosome walking : chromosomes) {
                mystery = walking.getNum();
                if (mystery == 0) {
                    crack = true;
                    break;
                }
                if (mystery == 2) {
                    double fiddyfiddy = Math.random();
                    if (fiddyfiddy <= 0.5) {
                        break;
                    }
                }
                crisper++;
                if (crisper == chromosomes.size() - 1) {
                    dancingOutput[0] = 1 + ((19 * k) / 1000);
                    dancingOutput[1] = k;
                    dancingOutput[2] = totalZeros;
                    dancingOutput[3] = totalOnes;
                    dancingOutput[4] = totalQuestions;
                    return dancingOutput;
                }
            }
            if (crack) {
                break;
            }
        }
        dancingOutput[0] = 1;
        dancingOutput[1] = 0;
        dancingOutput[2] = totalZeros;
        dancingOutput[3] = totalOnes;
        dancingOutput[4] = totalQuestions;
        return dancingOutput;
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

    public int calc1s() {
        int[] allData = dancing();
        return allData[3];
    }
    //Gives 1s in the milestone 4 genes

    public int calc0s() {
        int[] allData = dancing();
        return allData[2];
    }
    //Gives 0s in the milestone 4 genes

    public int calc2s() {
        int[] allData = dancing();
        return allData[4];
    }
    //Gives ?s in the milestone 4 genes

    public int calcTotFitness(String fitMethod) {
        this.fitMethod = fitMethod;
        if (fitMethod == "twenty 1s" || fitMethod == "d") {
            count = 0;
            for (int i = 0; i < chromosomes.size(); i++) {
                if (chromosomes.get(i).getNum() == 1) {
                    count++;
                }
            }
        }

        if (fitMethod.equals("Smiley")) {
            count = 0;
            for (int i = 0; i < smiley.length(); i++) {
                String curr = smiley.substring(i, i + 1);
                if (chromosomes.get(i).getNum() == Double.parseDouble(curr)) {
                    count++; // Compare current chromosomes genes to target chromosome
                } // Increment by 1 if a gene matches
            }
        } else if (fitMethod.equals("Slime")) {
            count = 0;
            for (int i = 0; i < slime.length(); i++) {
                String curr = slime.substring(i, i + 1);
                if (chromosomes.get(i).getNum() == Double.parseDouble(curr)) {
                    count++;
                }
            }
        } else if (fitMethod.equals("All Green")) {
            count = 0;
            for (int i = 0; i < chromosomes.size(); i++) {
                if (chromosomes.get(i).getNum() == 1) {
                    count++;
                }
            }
        } else if (fitMethod.equals("Jack")) {

            count = 0;
            for (int k = 0; k < 6; k++) {
                max[k] = 0;
            }

            for (int i = 0; i < slime.length(); i++) {
                String curr = slime.substring(i, i + 1);
                if (chromosomes.get(i).getNum() == Double.parseDouble(curr)) {
                    max[0]++;
                }
            }

            for (int i = 0; i < smiley.length(); i++) {
                String curr = smiley.substring(i, i + 1);
                if (chromosomes.get(i).getNum() == Double.parseDouble(curr)) {
                    max[1]++;
                }
            }

            for (int i = 0; i < grid.length(); i++) {
                String curr = grid.substring(i, i + 1);
                if (chromosomes.get(i).getNum() == Double.parseDouble(curr)) {
                    max[2]++;
                }
            }

            for (int i = 0; i < fallen.length(); i++) {
                String curr = fallen.substring(i, i + 1);
                if (chromosomes.get(i).getNum() == Double.parseDouble(curr)) {
                    max[3]++;
                }
            }

            for (int i = 0; i < marks.length(); i++) {
                String curr = marks.substring(i, i + 1);
                if (chromosomes.get(i).getNum() == Double.parseDouble(curr)) {
                    max[4]++;
                }
            }

            for (int i = 0; i < dimiond.length(); i++) {
                String curr = dimiond.substring(i, i + 1);
                if (chromosomes.get(i).getNum() == Double.parseDouble(curr)) {
                    max[5]++;
                }
            }

            for (int i = 0; i < risen.length(); i++) {
                String curr = risen.substring(i, i + 1);
                if (chromosomes.get(i).getNum() == Double.parseDouble(curr)) {
                    max[6]++;
                }
            }

            for (int k = 0; k < 7; k++) {
                if (max[k] >= count) {
                    count = max[k];
                }
            }
        } else if (fitMethod.equals("Clock")) {
            count = 0;
            for (int i = 0; i < timeLevels[whatTimeIsIt].length(); i++) {

                String curr = timeLevels[whatTimeIsIt].substring(i, i + 1);
                if (chromosomes.get(i).getNum() == Double.parseDouble(curr)) {
                    count++;
                }
            }
            massiveClock++;
            if (massiveClock > 300000) {
                whatTimeIsIt++;
                if (whatTimeIsIt == 10) {
                    whatTimeIsIt = 0;
                }
                massiveClock = 0;
            }

        } else if (fitMethod.equals("All")) {
            count = 0;
            for (int i = 0; i < jackie[whatTimeIsIt].length(); i++) {

                String curr = jackie[whatTimeIsIt].substring(i, i + 1);
                if (chromosomes.get(i).getNum() == Double.parseDouble(curr)) {
                    count++;
                }
            }
        } else if (fitMethod.equals("miniJack")) {

            count = 0;
            for (int k = 0; k < 3; k++) {
                max[k] = 0;
            }

            for (int i = 0; i < smiley.length(); i++) {
                String curr = smiley.substring(i, i + 1);
                if (chromosomes.get(i).getNum() == Double.parseDouble(curr)) {
                    max[1]++;
                }
            }

            for (int i = 0; i < grid.length(); i++) {
                String curr = grid.substring(i, i + 1);
                if (chromosomes.get(i).getNum() == Double.parseDouble(curr)) {
                    max[2]++;
                }
            }

            for (int i = 0; i < dimiond.length(); i++) {
                String curr = dimiond.substring(i, i + 1);
                if (chromosomes.get(i).getNum() == Double.parseDouble(curr)) {
                    max[5]++;
                }
            }

            for (int k = 0; k < 3; k++) {
                if (max[k] >= count) {
                    count = max[k];
                }
            }
        }

        massiveClock++;
        if (massiveClock > 300000) {
            whatTimeIsIt++;
            if (whatTimeIsIt == 7) {
                whatTimeIsIt = 0;
            }
            massiveClock = 0;

        }

        return count;
    }
    //Returns the value of the fitness with all different evolution methods.
}
