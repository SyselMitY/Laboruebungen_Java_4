package lagersimulation;

import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Konsument implements Runnable {

    private final int minConsumedSize;
    private final int maxConsumedSize;
    private final Lager lager;
    private final Logger logger;
    private final int minWaitingTime;
    private final int maxWaitingTime;
    private final Runnable runAfter;

    public Konsument(int minConsumedSize, int maxConsumedSize, Lager lager, Logger logger, int minWaitingTime, int maxWaitingTime, Runnable runAfter) {
        this.minConsumedSize = minConsumedSize;
        this.maxConsumedSize = maxConsumedSize;
        this.lager = lager;
        this.logger = logger;
        this.maxWaitingTime = maxWaitingTime;
        this.minWaitingTime = minWaitingTime;
        this.runAfter = runAfter;
    }

    public Konsument(int minConsumedSize, int maxConsumedSize, Lager lager, Logger logger, int minWaitingTime, int maxWaitingTime) {
        this(minConsumedSize, maxConsumedSize, lager, logger, minWaitingTime, maxWaitingTime, null);
    }

    @Override
    public void run() {
        lager.registerKonsument(this);
        while (!(lager.getOccupiedSpace() == 0 && lager.getLieferantenCount() == 0)) {
            int waitingTime = ThreadLocalRandom.current().nextInt(minWaitingTime, maxWaitingTime + 1);
            int nextConsumationSize = ThreadLocalRandom.current().nextInt(minConsumedSize, maxConsumedSize + 1);
            if (lager.getLieferantenCount() == 0) {
                nextConsumationSize = Math.min(nextConsumationSize, lager.getOccupiedSpace());
            }

            if (nextConsumationSize != 0) {
                try {
                    lager.removeCargo(nextConsumationSize);
                    String logline = String.format("Removed %dm³ of cargo from the storage (total %dm³)", nextConsumationSize, lager.getOccupiedSpace());
                    logger.log(Level.INFO, logline);
                    Thread.sleep(waitingTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        lager.deregisterKonsument(this);
        if (runAfter != null) {
            runAfter.run();
        }
    }
}
