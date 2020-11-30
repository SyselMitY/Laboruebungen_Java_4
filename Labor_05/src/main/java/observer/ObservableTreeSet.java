package observer;

import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class ObservableTreeSet<T> extends TreeSet<T> {
    public ObservableTreeSet() {
    }

    public ObservableTreeSet(Comparator<? super T> comparator) {
        super(comparator);
    }

    public ObservableTreeSet(Collection<? extends T> c) {
        super(c);
    }

    public ObservableTreeSet(SortedSet<T> s) {
        super(s);
    }

}
