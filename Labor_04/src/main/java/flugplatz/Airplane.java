package flugplatz;

import java.util.Objects;

public class Airplane implements Runnable {
    private final Tower destinationTower;
    private final String name;
    private final int runwayNumber;

    public Airplane(Tower destinationTower, String name, int runwayNumber) {
        this.destinationTower = Objects.requireNonNull(destinationTower);
        this.name = name;
        this.runwayNumber = runwayNumber;
    }

    @Override
    public void run() {
        Runway landingRunway = destinationTower.getRunway(runwayNumber);
        System.out.println(name + " is waiting for runway " + landingRunway.getName());
        getRunwayLock(landingRunway);
        System.out.println(name + " is landing on runway " + landingRunway.getName());
        sleepAndCatch(3000);
        System.out.println(name + " crosses E on runway " + landingRunway.getName());
        landingRunway.getCommonPoint().unlock();
        sleepAndCatch(5000);
        System.out.println(name + " leaves runway " + landingRunway.getName());
        landingRunway.getRunwayLock().unlock();
    }

    private void getRunwayLock(Runway runway) {
        runway.getCommonPoint().lock();
        runway.getRunwayLock().lock();
    }

    private void sleepAndCatch(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
