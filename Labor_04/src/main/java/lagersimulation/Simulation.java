package lagersimulation;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Simulation {
    public static void main(String[] args) {
        try {
            Logger logger = Logger.getLogger(Simulation.class.getName());
            FileHandler handler = new FileHandler("lager.log");
            handler.setEncoding("UTF-8");
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);

            Lager lager = new Lager(20, logger);
            Thread lieferant = new Thread(new Lieferant(35, 5, 10, lager, logger, 1000, 5000));
            Thread konsument = new Thread(new Konsument(7, 7, lager, logger, 3000, 6000));
            lieferant.start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            konsument.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
