package mainApp;

import java.awt.*;
import java.util.List;

public class MiniJackFitnessMethod implements FitnessMethod {
    private Clock clock;
    int flipper = 0;
    @Override
    public int calcTotalFitness(List<Chromosome> chromosomes) {
        int[] max = new int[7];
        int count = 0;
        for (int k = 0; k < 3; k++) {
            max[k] = 0;
        }

        for (int i = 0; i < SMILEY.length(); i++) {
            String curr = SMILEY.substring(i, i + 1);
            if (chromosomes.get(i).getNum() == Double.parseDouble(curr)) {
                max[1]++;
            }
        }

        for (int i = 0; i < GRID.length(); i++) {
            String curr = GRID.substring(i, i + 1);
            if (chromosomes.get(i).getNum() == Double.parseDouble(curr)) {
                max[2]++;
            }
        }

        for (int i = 0; i < DIAMOND.length(); i++) {
            String curr = DIAMOND.substring(i, i + 1);
            if (chromosomes.get(i).getNum() == Double.parseDouble(curr)) {
                max[5]++;
            }
        }

        for (int k = 0; k < 3; k++) {
            if (max[k] >= count) {
                count = max[k];
            }
        }
        clock.incrementTime();
        return count;
    }

    @Override
    public Color getColorAtIndex(int index) {
        if (flipper < 6) {
            flipper++;
        } else {
            flipper = 0;
        }
        if (FITNESS_METHODS[flipper].charAt(index) == '1') {
            return Color.GREEN;
        } else {
            return Color.BLACK;
        }
    }
    @Override
    public void setClock(Clock c) {
        clock = c;
    }
}
