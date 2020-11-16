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

    public Konsument(int minConsumedSize, int maxConsumedSize, Lager lager, Logger logger, int minWaitingTime, int maxWaitingTime) {
        this.minConsumedSize = minConsumedSize;
        this.maxConsumedSize = maxConsumedSize;
        this.lager = lager;
        this.logger = logger;
        this.maxWaitingTime = maxWaitingTime;
        this.minWaitingTime = minWaitingTime;
    }

    @Override
    public void run() {
        while (!(lager.getOccupiedSpace() == 0 && lager.getLieferantenCount() == 0)) {
            int waitingTime = ThreadLocalRandom.current().nextInt(minWaitingTime, maxWaitingTime + 1);
            int nextConsumationSize = ThreadLocalRandom.current().nextInt(minConsumedSize, maxConsumedSize + 1);
            if (lager.getLieferantenCount() == 0) {
                nextConsumationSize = Math.min(nextConsumationSize, lager.getOccupiedSpace());
            }

            try {
                lager.removeCargo(nextConsumationSize);
                String logline = String.format("Removed %dm³ of cargo from the storage (total %dm³)", nextConsumationSize,lager.getOccupiedSpace());
                logger.log(Level.INFO, logline);
                Thread.sleep(waitingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
