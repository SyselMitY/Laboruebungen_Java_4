package decorator;

public class BaseCounter implements Counter {

    private final int base;
    private int current;

    public BaseCounter(int base) {
        this.base = base;
    }

    @Override
    public int read() {
        return Integer.parseInt(Integer.toString(current, base));
    }

    @Override
    public Counter tick() {
        current++;
        return this;
    }
}
