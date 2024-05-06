package mainApp;

public class GenerationInfo {
    private int[] zeros;
    private int[] ones;
    private int[] twos;
    public GenerationInfo (int[] zeros, int[] ones, int[] twos) {
        this.zeros = zeros;
        this.ones = ones;
        this.twos = twos;
    }

    public int totalZeros() {
        int total = 0;
        for (int i = 0; i < zeros.length; i++) {
            total += zeros[i];
        }
        return total;
    }

    public int totalOnes() {
        int total = 0;
        for (int i = 0; i < ones.length; i++) {
            total += ones[i];
        }
        return total;
    }

    public int totalTwos() {
        int total = 0;
        for (int i = 0; i < twos.length; i++) {
            total += twos[i];
        }
        return total;
    }
}
