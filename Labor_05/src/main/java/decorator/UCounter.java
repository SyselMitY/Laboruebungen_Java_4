package decorator;

public class UCounter implements Counter{

    private int current;

    @Override
    public int read() {
        return current;
    }

    @Override
    public Counter tick() {
        current++;
        return this;
    }
}
