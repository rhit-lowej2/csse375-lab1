package mainApp;

import java.awt.*;
import java.util.List;

public class StringCompareFitnessMethod implements FitnessMethod {
    private String stringToCompare;
    private boolean debug;
    public StringCompareFitnessMethod(String s) {
        stringToCompare = s;
    }
    @Override
    public int calcTotalFitness(List<Chromosome> chromosomes) {
        int count = 0;
        for (int i = 0; i < stringToCompare.length(); i++) {
            String curr = stringToCompare.substring(i, i + 1);
            if (chromosomes.get(i).getNum() == Double.parseDouble(curr)) {
                count++; // Compare current chromosomes genes to target chromosome
            } // Increment by 1 if a gene matches
        }
        return count;
    }

    @Override
    public Color getColorAtIndex(int index) {
        if (stringToCompare.charAt(index) == '1') {
            return Color.GREEN;
        } else {
            return Color.BLACK;
        }
    }


    public void setDebug(boolean debug) {
        this.debug = debug;
    }
    @Override
    public boolean isDebug() {
        return debug;
    }
}
