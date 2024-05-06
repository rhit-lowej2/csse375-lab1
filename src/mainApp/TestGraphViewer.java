package mainApp;

public class TestGraphViewer extends GraphViewer {
    boolean shouldError;
    public TestGraphViewer(boolean shouldError) {
        super();
        this.shouldError = shouldError;
    }
    @Override
    public int getGenSize() {
        if (shouldError) { return -1; }
        return 10;
    }

    @Override
    public GenParams getParams() {
        if (shouldError) { return new GenParams(-1, -100, "t", 1); }
        return new GenParams(1, 100, "t", 1);
    }
}
