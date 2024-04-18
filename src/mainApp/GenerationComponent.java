package mainApp;

import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JComponent;

/**
 * Class: GenerationComponent
 * 
 * @author AJ Hedden Alex Brickley Hollin Glaze
 *         This class keeps track of all previous generations and keeps a stored
 *         list of
 *         them.
 *         After each generation is created a new one is created based off of
 *         methods in
 *         the Generation class
 *         a set number of times.
 */

public class GenerationComponent extends JComponent {

    protected ArrayList<Generation> generations = new ArrayList<Generation>();
    protected ChromosomeComponent[] survivors;
    protected double rate;
    protected String method = "t";
    protected double elitism;
    protected int genSize;
    private boolean isMax = false;
    private boolean onlyTop = false;
    protected int popSize = 100;
    protected boolean crossing;
    private FitnessMethod fitnessMethod = new StringCompareFitnessMethod("Smiley");

    public void drawGeneration(int currentGenerationIndex) {
        if (currentGenerationIndex < generations.size()) {
            repaint();
        }
    }
    //redraws all of the generations on the screen

    public GenerationComponent() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < generations.size(); i++) {
            generations.get(i).drawOn(g);
        }
    }
    //Tells component to paint itself

    public void term() {
        this.isMax = true;
    }
    //Tells the program to terminate at 100

    public void randomize(double rate, String method, int genSize, int popSize, double elitism, FitnessMethod fitnessMethod) {
        this.elitism = elitism;
        this.method = method;
        this.rate = rate;
        this.genSize = genSize;
        this.popSize = popSize;
        this.fitnessMethod = fitnessMethod;
        Generation first = new Generation(null, new GenParams(rate, popSize, method, elitism), fitnessMethod);
        generations.add(first);
        first.drawOn(getGraphics());
        nextGen();
    }
    //Used for all selection methods, creates first generation and sets some parameters

    public void cross() {
        this.crossing = true;
    }
    //Sets crossver to true

    public void nextGen() {
        for (int i = 1; i < genSize; i++) {
            if (this.method.equals("t")) {
                survivors = generations.get(i - 1).getBest();
            } else {
                survivors = generations.get(i - 1).allOrdered();
            }
            Generation newGen = new Generation(survivors, new GenParams(rate, popSize, method, elitism), fitnessMethod);
            generations.add(newGen);
            if (crossing == true) {
                generations.get(i).cross();
            }
            newGen.drawOn(getGraphics());
        }
    }
    //After the first drawing of each has been created, will create the rest of the neccessary generations
}