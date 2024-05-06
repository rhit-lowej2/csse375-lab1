package mainApp;

import java.awt.*;
import java.util.List;

public class AllFitnessMethod implements FitnessMethod {

    private Clock clock;
    @Override
    public int calcTotalFitness(List<Chromosome> chromosomes) {
        int count = 0;
        int timeIndex = clock.getTimeIndex();
        for (int i = 0; i < FITNESS_METHODS[timeIndex].length(); i++) {

            String curr = FITNESS_METHODS[timeIndex].substring(i, i + 1);
            if (chromosomes.get(i).getNum() == Double.parseDouble(curr)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public Color getColorAtIndex(int index) {
        if (FITNESS_METHODS[clock.getTimeIndex()].charAt(index) == '1') {
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
