package mainApp;

import java.awt.*;
import java.util.List;

public class FakeFitnessMethod implements FitnessMethod {

    @Override
    public int calcTotalFitness(List<Chromosome> chromosomes) {
        return chromosomes.size();
    }

    @Override
    public Color getColorAtIndex(int index) {
        return Color.yellow;
    }
}
