package flugplatz;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Tower {

    private final ReentrantLock commonPointE;
    private Runway[] runways;
    private int nextRunway;


    public Tower() {
        commonPointE = new ReentrantLock(true);
        runways = new Runway[]{
                new Runway(commonPointE, "AB"),
                new Runway(commonPointE, "CD")};
    }

    public Runway getRunway(int runwayNumber) {
        return runways[runwayNumber];
    }

}
