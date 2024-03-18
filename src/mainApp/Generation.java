package mainApp;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 * Class: Generation
 * 
 * @author AJ Hedden Alex Brickley Hollin Glaze
 *         This class is essentially a list of Chromosomes (or
 *         ChromosomeComponents).
 *         It can be called from GenerationViewer and when the randomize button
 *         is
 *         pressed, the first generation will be randomly
 *         generated and then using methods both here and in
 *         GenerationComponent, a set
 *         amount of new, parent
 *         generations will be created using the best half of previous ones.
 */

public class Generation {

    ArrayList<ChromosomeComponent> chromosomeList;
    ArrayList<ChromosomeComponent> newChroms;
    ChromosomeComponent[] topHalf = new ChromosomeComponent[100];
    ChromosomeComponent best;
    ChromosomeComponent currBest;
    private int[] origGenes;
    private double spotOnWheel = 0;
    private ChromosomeComponent[] currReproduce;
    private double totalWheel;
    private double rate;
    private int popSize;
    private int geneSize;
    private double elitism;
    private String selection;
    private boolean terminateMe = false;
    private String fitnessMethod;

    public Generation(ChromosomeComponent[] survivors, double rate, int popSize, String selection, double elitism,
            String fitnessMethod) {
        this.rate = rate;
        this.selection = selection;
        this.popSize = popSize;
        currReproduce = new ChromosomeComponent[popSize];
        this.elitism = elitism;
        this.fitnessMethod = fitnessMethod;
        if (popSize != 100) {
            geneSize = 20;
        } else {
            geneSize = 100;
        }
        origGenes = new int[geneSize]; // Gene size
        chromosomeList = new ArrayList<ChromosomeComponent>();

        if (selection != "d") {

            if (survivors == null) {
                for (int i = 0; i < popSize; i++) {
                    chromosomeList.add(new ChromosomeComponent());
                    for (int j = 0; j < geneSize; j++) {
                        double random = Math.random();
                        if (random < 0.5) {
                            origGenes[j] = 1;
                        } else if (random > 0.5) {
                            origGenes[j] = 0;
                        }
                    }
                    chromosomeList.get(i).setGeneration(origGenes, i, fitnessMethod);
                }
            } else if (selection.equals("t")) {
                truncateReproduce(survivors);
            } else if (this.selection.equals("la") || this.selection.charAt(0) == 'l') {
                rankingReproduce(survivors);
            } else if (selection.equals("ro")) {
                rouletteWheelReproduce(survivors);
            }
            topHalf = new ChromosomeComponent[chromosomeList.size() / 2];
            calcBest();
        } else {
            if (survivors == null) {
                for (int i = 0; i < popSize; i++) {
                    chromosomeList.add(new ChromosomeComponent());
                    for (int j = 0; j < geneSize; j++) {
                        double random = Math.random();
                        if (random < 0.25) {
                            origGenes[j] = 1;
                        } else if (random < 0.5) {
                            origGenes[j] = 0;
                        } else {
                            origGenes[j] = 2;
                        }
                    }

                    chromosomeList.get(i).setGeneration(origGenes, i, fitnessMethod);
                }
            } else {
                newRoulette(survivors);
            }

        }
    }
    //Creates the generation and tells it where to go next based off the input arguments

    public void truncateReproduce(ChromosomeComponent[] survivors) {
        for (int i = 0; i < survivors.length * 2; i++) {
            chromosomeList.add(new ChromosomeComponent());
            if (rate != 100) {
                double actual = (double) i / popSize;
                if (actual > elitism / 100) {
                    survivors[i % (popSize / 2)].mutate(rate);
                }
            }
            origGenes = survivors[i % (popSize / 2)].getGenes();
            chromosomeList.get(i).setGeneration(origGenes, i, fitnessMethod);

            double actual = (double) i / popSize;
            if (actual > elitism / 100) {
                survivors[i % popSize / 2].mutate(rate);
            }
            origGenes = survivors[i % popSize / 2].getGenes();
            chromosomeList.get(i).setGeneration(origGenes, i, fitnessMethod);
        }
    }
    //Shortens the list and gives info for next generation

    public void newRoulette(ChromosomeComponent[] allChromosomes) {
        for (ChromosomeComponent c : allChromosomes) {
            totalWheel += c.calcTotFitness(fitnessMethod);
        }

        for (int i = 0; i < popSize; i++) {
            chromosomeList.add(new ChromosomeComponent());
            for (int k = 0; k < allChromosomes.length; k++) {
                currReproduce[k % geneSize / 2] = allChromosomes[k];
            }
            for (ChromosomeComponent c : allChromosomes) {
                double rouletteResult = Math.random();
                if ((c.calcTotFitness(fitnessMethod)) / totalWheel + spotOnWheel >= rouletteResult) {
                    currReproduce[i] = c;
                    break;
                }
                spotOnWheel += (c.calcTotFitness(fitnessMethod)) / totalWheel;
            }
            origGenes = currReproduce[i].getGenes();
            chromosomeList.get(i).setGeneration(origGenes, i, fitnessMethod);
        }
    }
    //Roulette wheel used for milestone 4

    public void rouletteWheelReproduce(ChromosomeComponent[] allChromosomes) {
        for (ChromosomeComponent c : allChromosomes) {
            totalWheel += c.calcTotFitness(fitnessMethod);
        }

        for (int i = 0; i < popSize; i++) {
            chromosomeList.add(new ChromosomeComponent());

            for (int k = 0; k < allChromosomes.length; k++) {
                currReproduce[k] = allChromosomes[k];
                currReproduce[k % popSize / 2] = allChromosomes[k];
            }
            for (ChromosomeComponent c : allChromosomes) {
                double spin = Math.random();
                if ((c.calcTotFitness(fitnessMethod)) / totalWheel + spotOnWheel >= spin) {
                    currReproduce[i] = c;
                    double actual = (double) i / popSize;
                    if (actual > elitism / 100) {
                        currReproduce[i].mutate(rate);
                    }
                    break;
                }
                spotOnWheel += (c.calcTotFitness(fitnessMethod)) / totalWheel;
            }
            origGenes = currReproduce[i].getGenes();
            chromosomeList.get(i).setGeneration(origGenes, i, fitnessMethod);
        }
    }
    //Traditional roulette wheel logic and reasoning

    public void ending() {
        this.terminateMe = true;
    }

    public void rankingReproduce(ChromosomeComponent[] survivors) {
        calcBest();
        ChromosomeComponent topTier = new ChromosomeComponent();
        int rankTopIndex = 0;
        for (int i = 0; i < elitism && i < 50; i++) {
            ChromosomeComponent keyChrom = survivors[i];
            chromosomeList.add(new ChromosomeComponent());
            topTier.add(keyChrom);
            origGenes = keyChrom.getGenes();
            chromosomeList.get(i).setGeneration(origGenes, i, fitnessMethod);
        }
        for (int k = (int) elitism; k <= popSize;) {
            while (k < 30) {
                ChromosomeComponent keyChrom = survivors[rankTopIndex];
                chromosomeList.add(new ChromosomeComponent());
                topTier.add(keyChrom);
                origGenes = keyChrom.getGenes();
                chromosomeList.get(k).setGeneration(origGenes, k, fitnessMethod);
                double actual = (double) k / popSize;
                if (actual > elitism / 100.0) {
                    chromosomeList.get(k).mutate(rate);
                }
                k++;
                if (rankTopIndex != 9) {
                    rankTopIndex++;
                } else {
                    rankTopIndex = 0;
                }
            }
            while (k <= 90) {
                if (rankTopIndex < 10) {
                    rankTopIndex = 10;
                }
                ChromosomeComponent keyChrom = survivors[rankTopIndex];
                chromosomeList.add(new ChromosomeComponent());
                topTier.add(keyChrom);
                origGenes = keyChrom.getGenes();
                chromosomeList.get(k).setGeneration(origGenes, k, fitnessMethod);
                double actual = (double) k / popSize;
                if (actual > elitism / 100.0) {
                    chromosomeList.get(k).mutate(rate);
                }
                k++;
                if (rankTopIndex != 40) {
                    rankTopIndex++;
                } else {
                    rankTopIndex = 10;
                }
            }
            while (k < 100) {
                if (rankTopIndex < 40) {
                    rankTopIndex = 40;
                }
                ChromosomeComponent keyChrom = survivors[rankTopIndex];
                chromosomeList.add(new ChromosomeComponent());
                topTier.add(keyChrom);
                origGenes = keyChrom.getGenes();
                chromosomeList.get(k).setGeneration(origGenes, k, fitnessMethod);
                double actual = (double) k / popSize;
                if (actual > elitism / 100.0) {
                    chromosomeList.get(k).mutate(rate);
                }
                k++;
                if (rankTopIndex != 50) {
                    rankTopIndex++;
                } else {
                    rankTopIndex = 40;
                }
            }
            k++;
        }
    }
    //Method used for ranking selection

    public int[] get1s() {
        int[] output = new int[chromosomeList.size()];
        for (int i = 0; i < chromosomeList.size(); i++) {
            output[i] = chromosomeList.get(i).calc1s();
        }
        return output;
    }
    //Used to get all of the 1 values from the milestone 4 chromosomeList

    public int[] get0s() {
        int[] output = new int[chromosomeList.size()];
        for (int i = 0; i < chromosomeList.size(); i++) {
            output[i] = chromosomeList.get(i).calc0s();
        }
        return output;
    }
    //Used to get all of the 0 values from the milestone 4 chromosomeList
    public int[] get2s() {
        int[] output = new int[chromosomeList.size()];
        for (int i = 0; i < chromosomeList.size(); i++) {
            output[i] = chromosomeList.get(i).calc2s();
        }
        return output;
    }
//Used to get all of the ? values from the milestone 4 chromosomeList
    public int getBestFit() {
        int best = -1;
        for (int i = 0; i < chromosomeList.size(); i++) {
            if (chromosomeList.get(i).calcTotFitness(fitnessMethod) > best) {
                best = chromosomeList.get(i).calcTotFitness(fitnessMethod);
            }
        }
        return best;
    }
    //Gets the best fit chromosome of the generation

    public double getAverageFit() {
        double total = 0;
        for (ChromosomeComponent chromosome : chromosomeList) {
            total += chromosome.calcTotFitness(fitnessMethod);
        }
        return total / chromosomeList.size();
    }
    //Get the average fit chromosome in any generation

    public int getWorstFit() {
        int worst = 100;
        for (int i = 0; i < chromosomeList.size(); i++) {
            if (chromosomeList.get(i).calcTotFitness(fitnessMethod) < worst) {
                worst = chromosomeList.get(i).calcTotFitness(fitnessMethod);
            }
        }
        return worst;
    }
    //Gets the least fit chromosome of each generation

    public double getHammingFit() {
        int total = 0;
        for (int i = 0; i < popSize - 1; i++) {
            int[] currGen = chromosomeList.get(i).getGenes();
            int[] nextGen = chromosomeList.get(i + 1).getGenes();
            for (int k = 0; k < currGen.length; k++) {
                if (currGen[i] != nextGen[i]) {
                    total++;
                }
            }
        }
        return total / chromosomeList.get(0).getGenes().length;
    }
    //calculates the hamming distance between chromosomes in a generation

    public void drawOn(Graphics g) {
        for (ChromosomeComponent chromosomes : chromosomeList) {
            chromosomes.paintComponent(g);
            if (topHalf[0].calcTotFitness(fitnessMethod) == 100 && terminateMe) {
                chromosomes.paintComponent(g);
                break;
            }
        }
        int[] newGenes = topHalf[0].getGenes();
        for (int i = 0; i < 100; i++) {
            topHalf[0].drawBest(g, newGenes[i], i);
        }
    }
    //Draws all of the necessary chromosomes on hte screen

    public void calcBest() {
        ArrayList<ChromosomeComponent> removeList = new ArrayList<ChromosomeComponent>(); // List will be removed
        for (int i = 0; i < chromosomeList.size(); i++) {
            removeList.add(chromosomeList.get(i)); // Transferring whole list to new list
        }
        for (int k = 0; k < chromosomeList.size() / 2; k++) {
            int max = -1;
            for (int i = 0; i < removeList.size(); i++) {
                if (removeList.get(i).calcTotFitness(fitnessMethod) > max) { // Finding current greatest fit
                    max = removeList.get(i).calcTotFitness(fitnessMethod);
                    best = removeList.get(i);
                }
            }
            removeList.remove(best); // Removing the greatest from the new list
            this.topHalf[k] = best;
        } 
    }
    //Creates a list of the best top half of each generation, used mainly for truncation.
    public ChromosomeComponent[] getBest() {
        return topHalf;
    }

    public ArrayList<ChromosomeComponent> getList() {
        return chromosomeList;
    }

    public ChromosomeComponent[] allOrdered() {
        ChromosomeComponent[] actualList = new ChromosomeComponent[chromosomeList.size()];
        ArrayList<ChromosomeComponent> removeMe = new ArrayList<ChromosomeComponent>();
        for (int i = 0; i < chromosomeList.size(); i++) {
            removeMe.add(chromosomeList.get(i));
        }

        for (int i = 0; i < chromosomeList.size(); i++) {
            int max = -1;
            for (int j = 0; j < removeMe.size(); j++) {
                if (removeMe.get(j).calcTotFitness(fitnessMethod) > max) {
                    max = removeMe.get(j).calcTotFitness(fitnessMethod);
                    currBest = removeMe.get(j);
                }
            }
            removeMe.remove(currBest);
            actualList[i] = currBest;
        }
        return actualList;
    }
    //Makes an entire ordered list of the chromosomes in a generation

    public void cross() {
        int[] currGenes = new int[chromosomeList.get(0).getGenes().length];
        int[] nextGenes = new int[chromosomeList.get(0).getGenes().length];

        int max = chromosomeList.size() - 1;
        for (int i = (int) elitism + 1; i < max; i++) {
            if (i % 2 == 0) {
                currGenes = chromosomeList.get(i).getGenes();
                nextGenes = chromosomeList.get(i + 1).getGenes();
                for (int k = 0; k < currGenes.length; k++) {
                    double rando = Math.random();
                    if (k > rando * 100) {
                        if (currGenes[k] != nextGenes[k]) {
                            int swap = currGenes[k];
                            currGenes[k] = nextGenes[k];
                            nextGenes[k] = swap;
                        }
                    }
                }
            }
            chromosomeList.get(i).setGenes(currGenes);
            chromosomeList.get(i + 1).setGenes(nextGenes);
        }
    }
    //Implenents crossover
}