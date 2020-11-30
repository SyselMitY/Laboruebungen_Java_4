package decorator;

public class LimitedCounter extends FilterCounter {

    private final int limit;

    public LimitedCounter(Counter in, int limit) {
        super(in);
        this.limit = limit;
    }

    @Override
    public int read() {
        return Math.min(super.read(),limit);
    }
}
