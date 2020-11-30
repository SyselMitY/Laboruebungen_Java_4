package decorator;

public class MultiCounter extends FilterCounter {

    private final int times;
    private int currentTimes;

    public MultiCounter(Counter in, int times) {
        super(in);
        if (times < 1)
            throw new IllegalArgumentException("Times must be bigger than 0");
        this.times = times;
    }

    @Override
    public Counter tick() {
        if ((currentTimes++) % times < times - 1)
            return this;
        return super.tick();
    }
}
