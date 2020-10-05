package primetest;

import java.util.Collection;
import java.util.List;

public class PrimeDaemonThread extends Thread {
    public static final int REPORT_SLEEP_TIME = 500;
    private final Collection<Integer> primes;

    public PrimeDaemonThread(Collection<Integer> primes) {
        this.primes = primes;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Active Checkers: " + PrimeTest.getRunningThreads());
            System.out.println(primes);
            try {
                sleep(REPORT_SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
