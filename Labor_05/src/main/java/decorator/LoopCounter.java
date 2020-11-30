package decorator;

import java.util.NoSuchElementException;

public class LoopCounter implements Counter {

    private final int[] values;
    private int current;

    public LoopCounter(int... values) {
        if(values.length < 1)
            throw new NoSuchElementException("Mindestens ein Wert erforderlich");
        this.values = values;
    }

    @Override
    public int read() {
        return values[current];
    }

    @Override
    public Counter tick() {
        current = (current + 1) % values.length;
        return this;
    }
}
