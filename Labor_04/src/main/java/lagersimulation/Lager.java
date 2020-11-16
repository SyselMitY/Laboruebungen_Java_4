package lagersimulation;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Lager {
    private final int capacity;
    private int occupiedSpace;
    private final ReentrantLock lock;
    private final Condition isNotFull;
    private final Condition isNotEmpty;
    private final Set<Lieferant> lieferanten;
    private final Logger logger;

    public Lager(int capacity, Logger logger) {
        this.capacity = capacity;
        this.logger = logger;
        this.lock = new ReentrantLock();
        this.isNotFull = lock.newCondition();
        this.isNotEmpty = lock.newCondition();
        this.lieferanten = new HashSet<>();
    }

    public void addCargo(int amount) throws InterruptedException {
        try {
            lock.lock();
            while (occupiedSpace + amount >= capacity) {
                logger.log(Level.INFO, "Lieferant waiting for more space");
                isNotFull.await();
            }
            occupiedSpace += amount;
            isNotEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void removeCargo(int amount) throws InterruptedException {
        try {
            lock.lock();
            while (occupiedSpace < amount) {
                logger.log(Level.INFO, "Konsument waiting for more cargo");
                isNotEmpty.await();
            }
            occupiedSpace -= amount;
            isNotFull.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public int getLieferantenCount() {
        return lieferanten.size();
    }

    public void registerLieferant(Lieferant lieferant) {
        lieferanten.add(lieferant);
    }

    public void deregisterLieferant(Lieferant lieferant) {
        lieferanten.remove(lieferant);
    }

    public int getOccupiedSpace() {
        return occupiedSpace;
    }
}
