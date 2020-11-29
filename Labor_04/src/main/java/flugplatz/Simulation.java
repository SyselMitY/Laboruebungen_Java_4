package flugplatz;

import java.util.concurrent.*;

public class Simulation {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(20);
        Tower tower = new Tower();

        for (int i = 0; i < 20; i++) {
            threadPool.submit(new Airplane(tower, "Flugzeug " + i, i%2));
            sleepAndCatch(ThreadLocalRandom.current().nextInt(11) * 1000);
        }

        threadPool.shutdown();
    }

    private static void sleepAndCatch(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
