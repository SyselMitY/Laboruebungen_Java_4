package synchronizedstack;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedStack<E> {
    private final E[] elements;
    private int size;
    private final ReentrantLock lock;
    private final Condition isNotFull;
    private final Condition isNotEmpty;

    @SuppressWarnings("unchecked")
    public SynchronizedStack(int capacity) {
        if(capacity<=0)
            throw new IllegalArgumentException("Capacity must be positive");
        this.elements = (E[]) new Object[capacity];
        this.lock = new ReentrantLock(true);
        this.isNotEmpty = lock.newCondition();
        this.isNotFull = lock.newCondition();
        this.size = 0;
    }

    public void push(E e) throws InterruptedException {
        try {
            lock.lock();
            while (size >= elements.length) {
                isNotFull.await();
            }
            size++;
            elements[size-1] = e;
            isNotEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public E pop() throws InterruptedException {
        try {
            lock.lock();
            while (size <= 0) {
                isNotEmpty.await();
            }
            E toReturn = elements[size-1];
            size--;
            isNotFull.signalAll();
            return toReturn;
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOf(elements,size));
    }
}
