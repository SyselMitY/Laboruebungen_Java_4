package spanningtree;

import java.util.AbstractMap;
import java.util.Map;
import java.util.function.Function;

class IndexMapper<T> implements Function<T, Map.Entry<Integer, T>> {
    private int counter;

    public IndexMapper() {
        this.counter = 0;
    }

    @Override
    public Map.Entry<Integer, T> apply(T o) {
        return new AbstractMap.SimpleImmutableEntry<>(counter++, o);
    }
}
