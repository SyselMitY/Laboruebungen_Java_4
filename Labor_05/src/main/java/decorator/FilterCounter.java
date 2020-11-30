package decorator;

public abstract class FilterCounter implements Counter {

    private final Counter in;

    public FilterCounter(Counter in) {
        this.in = in;
    }

    @Override
    public int read() {
        return in.read();
    }

    @Override
    public Counter tick() {
        in.tick();
        return this;
    }
}
