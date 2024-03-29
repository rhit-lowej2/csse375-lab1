package mainApp;

public class ChromosomeInfo {
    private int value;
    private int k;
    private int zeros;
    private int ones;
    private int nans;
    public ChromosomeInfo(int value, int k, int zeros, int ones, int nans) {
        this.value = value;
        this.k = k;
        this.zeros = zeros;
        this.ones = ones;
        this.nans = nans;
    }

    public int getZeros() {
        return this.zeros;
    }

    public int getOnes() {
        return this.ones;
    }

    public int getNaNs() {
        return this.nans;
    }
}
