package mainApp;

public class Clock {
    private int clock;
    private int timeIndex;
    public Clock() {
        clock = 0;
    }
    public void incrementTime() {
        clock++;
        if (clock > 300000) {
            timeIndex++;
            if (timeIndex == 7) {
                timeIndex = 0;
            }
            clock = 0;
        }
    }

    public int getTimeIndex() {
        return timeIndex;
    }
}
