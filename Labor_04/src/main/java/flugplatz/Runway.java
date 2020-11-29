package flugplatz;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Runway {
    private final ReentrantLock runwayLock;
    private final ReentrantLock commonPoint;
    private final String name;

    public Runway(ReentrantLock commonPoint, String name) {
        this.name = name;
        this.runwayLock = new ReentrantLock();
        this.commonPoint = commonPoint;
    }

    public ReentrantLock getRunwayLock() {
        return runwayLock;
    }


    public ReentrantLock getCommonPoint() {
        return commonPoint;
    }

    public String getName() {
        return name;
    }
}
