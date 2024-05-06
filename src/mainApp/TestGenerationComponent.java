package mainApp;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class TestGenerationComponent extends GenerationComponent {
    private double elitism;
    private String method;
    private int genSize;
    private int popSize;
    private double rate;
    private FitnessMethod fitnessMethod = new FakeFitnessMethod();

    public TestGenerationComponent() {
    }

    public void randomize(double rate, String method, int genSize, int popSize, double elitism, FitnessMethod fitnessMethod) {
        this.elitism = elitism;
        this.method = method;
        this.rate = rate;
        this.genSize = genSize;
        this.popSize = popSize;
        this.fitnessMethod = fitnessMethod;
        Generation first = new Generation(null, new GenParams(rate, popSize, method, elitism), fitnessMethod);
        generations.add(first);
    }
    @Override
    public void nextGen() {
        for (int i = 1; i < genSize; i++) {
            survivors = generations.get(i - 1).getBest();
            Generation newGen = new Generation(survivors, new GenParams(rate, popSize, method, elitism), fitnessMethod);
            generations.add(newGen);
        }
    }
    public List<Double> getDebugOutput() {
        List<Double> list = new ArrayList<>();
        for (Generation g : generations) {
            list.add(g.getAverageFit());
        }
        return list;
    }

    //After the first drawing of each has been created, will create the rest of the neccessary generations
}
