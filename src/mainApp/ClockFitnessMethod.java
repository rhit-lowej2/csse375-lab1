package mainApp;

import java.awt.*;
import java.util.List;

public class ClockFitnessMethod implements FitnessMethod {
    private static final String[] timeLevels = new String[] {ONE_CLOCK, TWO_CLOCK, THREE_CLOCK, FOUR_CLOCK, FIVE_CLOCK, SIX_CLOCK,
            SEVEN_CLOCK, EIGHT_CLOCK, NINE_CLOCK, TEN_CLOCK};

    private Clock clock;
    @Override
    public int calcTotalFitness(List<Chromosome> chromosomes) {
        int count = 0;
        int timeIndex = clock.getTimeIndex();
        for (int i = 0; i < timeLevels[timeIndex].length(); i++) {

            String curr = timeLevels[timeIndex].substring(i, i + 1);
            if (chromosomes.get(i).getNum() == Double.parseDouble(curr)) {
                count++;
            }
        }
        clock.incrementTime();
        return count;
    }

    @Override
    public Color getColorAtIndex(int index) {
        if (timeLevels[clock.getTimeIndex()].charAt(index) == '1') {
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
