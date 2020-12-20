package observer;

@FunctionalInterface
public interface TreeSetObserver<T> {
    void notifyObserver(TreeSetEvent<T> event);
}
