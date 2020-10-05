package leibnitz;

public class LeibnitzThread extends Thread {
    private double erg;
    private final int start;
    private final int endInclusive;
    private boolean finished;

    public LeibnitzThread(int start, int endInclusive) {
        if (start < 0 || endInclusive <= start)
            throw new IllegalArgumentException("Illegal range");
        this.start = start;
        this.endInclusive = endInclusive;
        this.erg = 0;
        this.finished = false;
    }

    @Override
    public void run() {
        for (int i = start; i <= endInclusive; i++) {
            erg += (double) (i % 2 == 0 ? 1 : -1) / (2 * i + 1);
        }
        finished = true;
    }

    public double getErg() {
        if (!finished)
            throw new IllegalStateException("Thread is not finished yet");
        return erg;
    }

    public int getStart() {
        return start;
    }

    public int getEndInclusive() {
        return endInclusive;
    }
}
