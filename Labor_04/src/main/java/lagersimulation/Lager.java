package lagersimulation;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Lager {
    private final int capacity;
    private IntegerProperty occupiedSpace;
    private final ReentrantLock lock;
    private final Condition isNotFull;
    private final Condition isNotEmpty;
    private final Set<Lieferant> lieferanten;
    private final Set<Konsument> konsumenten;
    private final Logger logger;
    private final Set<Runnable> lieferantenKonsumentenListeners;

    public Lager(int capacity, Logger logger) {
        this.capacity = capacity;
        this.logger = logger;
        this.lock = new ReentrantLock();
        this.isNotFull = lock.newCondition();
        this.isNotEmpty = lock.newCondition();
        this.lieferanten = new HashSet<>();
        this.konsumenten = new HashSet<>();
        this.occupiedSpace = new SimpleIntegerProperty();
        lieferantenKonsumentenListeners = new HashSet<>();
    }

    public void addCargo(int amount) throws InterruptedException {
        try {
            lock.lock();
            while (occupiedSpace.get() + amount >= capacity) {
                logger.log(Level.INFO, "Lieferant waiting for more space");
                isNotFull.await();
            }
            occupiedSpace.set(occupiedSpace.get() + amount);
            isNotEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void removeCargo(int amount) throws InterruptedException {
        try {
            lock.lock();
            while (occupiedSpace.get() < amount) {
                logger.log(Level.INFO, "Konsument waiting for more cargo");
                isNotEmpty.await();
            }
            occupiedSpace.set(occupiedSpace.get() - amount);
            isNotFull.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public int getLieferantenCount() {
        return lieferanten.size();
    }

    public int getKonsumentenCount() {
        return konsumenten.size();
    }

    public int getOccupiedSpace() {
        return occupiedSpace.get();
    }

    public IntegerProperty occupiedSpaceProperty() {
        return occupiedSpace;
    }

    public int getCapacity() {
        return capacity;
    }

    public void registerLieferant(Lieferant lieferant) {
        lieferanten.add(lieferant);
        notifySubscribers();
    }

    public void deregisterLieferant(Lieferant lieferant) {
        lieferanten.remove(lieferant);
        notifySubscribers();
    }

    public void registerKonsument(Konsument konsument) {
        konsumenten.add(konsument);
        notifySubscribers();
    }

    public void deregisterKonsument(Konsument konsument) {
        konsumenten.remove(konsument);
        notifySubscribers();
    }

    public void addLieferantKonsumentListener(Runnable update) {
        lieferantenKonsumentenListeners.add(update);
    }

    public void removeLieferantKonsumentListener(Runnable update) {
        lieferantenKonsumentenListeners.remove(update);
    }

    private void notifySubscribers() {
        lieferantenKonsumentenListeners.forEach(Runnable::run);
    }
}
