package streamingone;

import java.math.BigInteger;
import java.util.AbstractMap;
import java.util.function.Supplier;

class FaktSupplierAdvanced implements Supplier<AbstractMap.SimpleImmutableEntry<Integer, BigInteger>> {
    private int i = 0;
    private BigInteger faktorielle = BigInteger.ONE;

    @Override
    public AbstractMap.SimpleImmutableEntry<Integer, BigInteger> get() {
        faktorielle = faktorielle.multiply(BigInteger.valueOf(++i));
        return new AbstractMap.SimpleImmutableEntry<>(i, faktorielle);
    }
}
