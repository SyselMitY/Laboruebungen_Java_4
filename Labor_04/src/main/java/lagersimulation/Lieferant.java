package lagersimulation;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Lieferant implements Runnable {

    private final int numberOfShipments;
    private final int minShipmentSize;
    private final int maxShipmentSize;
    private final Lager lager;
    private final Logger logger;
    private final int minWaitingTime;
    private final int maxWaitingTime;
    private final Runnable runAfter;

    public Lieferant(int numberOfShipments, int minShipmentSize, int maxShipmentSize, Lager lager, Logger logger, int minWaitingTime, int maxWaitingTime) {
        this(numberOfShipments, minShipmentSize, maxShipmentSize, lager, logger, minWaitingTime, maxWaitingTime, null);
    }

    public Lieferant(int numberOfShipments, int minShipmentSize, int maxShipmentSize, Lager lager, Logger logger, int minWaitingTime, int maxWaitingTime, Runnable runAfter) {
        this.numberOfShipments = numberOfShipments;
        this.minShipmentSize = minShipmentSize;
        this.maxShipmentSize = maxShipmentSize;
        this.lager = lager;
        this.logger = logger;
        this.maxWaitingTime = maxWaitingTime;
        this.minWaitingTime = minWaitingTime;
        this.runAfter = runAfter;
    }

    @Override
    public void run() {
        lager.registerLieferant(this);
        for (int i = 0; i < numberOfShipments; i++) {
            int waitingTime = ThreadLocalRandom.current().nextInt(minWaitingTime, maxWaitingTime + 1);
            int nextShipmentSize = ThreadLocalRandom.current().nextInt(minShipmentSize, maxShipmentSize + 1);
            try {
                lager.addCargo(nextShipmentSize);
                String logline = String.format("Added %dm³ of cargo to the storage (total %dm³)", nextShipmentSize, lager.getOccupiedSpace());
                logger.log(Level.INFO, logline);
                Thread.sleep(waitingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lager.deregisterLieferant(this);
        if (runAfter != null) {
            runAfter.run();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lieferant lieferant = (Lieferant) o;
        return numberOfShipments == lieferant.numberOfShipments &&
                minShipmentSize == lieferant.minShipmentSize &&
                maxShipmentSize == lieferant.maxShipmentSize &&
                maxWaitingTime == lieferant.maxWaitingTime &&
                minWaitingTime == lieferant.minWaitingTime &&
                Objects.equals(lager, lieferant.lager);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfShipments, minShipmentSize, maxShipmentSize, lager, maxWaitingTime, minWaitingTime);
    }
}
