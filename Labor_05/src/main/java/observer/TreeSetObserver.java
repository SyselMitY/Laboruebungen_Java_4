package observer;

public interface TreeSetObserver<T> {
    void notifyObserver(TreeSetEvent<T> event);
}
