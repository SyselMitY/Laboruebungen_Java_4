package streamingone;

import java.math.BigInteger;
import java.util.AbstractMap;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

public class FiboSupplier implements Supplier<AbstractMap.SimpleImmutableEntry<Integer, BigInteger>> {
    private int index;
    private BigInteger previous;
    private BigInteger current;

    public FiboSupplier() {
        this.previous = BigInteger.valueOf(0);
        this.current = BigInteger.valueOf(1);
        this.index = 1;
    }

    @Override
    public AbstractMap.SimpleImmutableEntry<Integer, BigInteger> get() {
        current = current.add(previous);
        previous = current.subtract(previous);

        return new AbstractMap.SimpleImmutableEntry<>(index++, previous);
    }
}
