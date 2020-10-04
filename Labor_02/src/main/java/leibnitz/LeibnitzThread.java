package leibnitz;

public class LeibnitzThread extends Thread {
    private double erg;
    private final int start;
    private final int endInclusive;

    public LeibnitzThread(int start,int endInclusive) {
        if(start<0||endInclusive<=start)
            throw new IllegalArgumentException("Illegal range");
        this.start = start;
        this.endInclusive = endInclusive;
        this.erg = -1;
    }

    @Override
    public void run() {
        double calcErg = 0;
        for (int i = start; i <= endInclusive; i++) {
            calcErg += (Math.pow(-1,i))/(2*i+1);
        }
        erg = calcErg;
    }

    public double getErg() {
        if(erg<0)
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
