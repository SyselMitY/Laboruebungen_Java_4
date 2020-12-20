package observer;

import java.util.*;

public class ObservableTreeSet<T> extends TreeSet<T> {

    private final Set<TreeSetObserver<T>> subscribers;

    public ObservableTreeSet() {
        subscribers = new HashSet<>();
    }

    public ObservableTreeSet(Comparator<? super T> comparator) {
        super(comparator);
        subscribers = new HashSet<>();
    }

    public ObservableTreeSet(Collection<? extends T> c) {
        super(c);
        subscribers = new HashSet<>();
    }

    public ObservableTreeSet(SortedSet<T> s) {
        super(s);
        subscribers = new HashSet<>();
    }

    @Override
    public boolean add(T t) {
        boolean ret = super.add(t);
        if(ret)
            notifySubscribers(new TreeSetEvent<>(this,"New element added"));
        return ret;
    }

    @Override
    public boolean remove(Object o) {
        boolean ret = super.remove(o);
        if(ret)
            notifySubscribers(new TreeSetEvent<>(this,"Element removed"));
        return ret;
    }

    @Override
    public void clear() {
        boolean modified = this.size() > 0;
        super.clear();
        if(modified)
            notifySubscribers(new TreeSetEvent<>(this,"Set cleared"));
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean ret = super.retainAll(c);
        if(ret)
            notifySubscribers(new TreeSetEvent<>(this,"Element(s) removed"));
        return ret;
    }

    @Override
    public T pollFirst() {
        T ret = super.pollFirst();
        if (ret != null)
            notifySubscribers(new TreeSetEvent<>(this,"Element removed"));
        return ret;
    }

    @Override
    public T pollLast() {
        T ret = super.pollLast();
        if (ret != null)
            notifySubscribers(new TreeSetEvent<>(this,"Element removed"));
        return ret;
    }

    private void notifySubscribers(TreeSetEvent<T> event) {
        subscribers.forEach(subscriber -> subscriber.notifyObserver(event));
    }

    public void subscribe(TreeSetObserver<T> subscriber) {
        subscribers.add(subscriber);
    }

    public void unsubscribe(TreeSetObserver<T> subscriber) {
        subscribers.remove(subscriber);
    }
}
