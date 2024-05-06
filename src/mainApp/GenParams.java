package mainApp;

public class GenParams {
    double rate;
    int popSize;
    int geneSize;
    double elitism;
    String selection;

    public GenParams(double rate, int popSize, String selection, double elitism) {
        this.rate = rate;
        this.popSize = popSize;
        this.selection = selection;
        this.elitism = elitism;
        if (popSize != 100) {
            geneSize = 20;
        } else {
            geneSize = 100;
        }
    }
}
