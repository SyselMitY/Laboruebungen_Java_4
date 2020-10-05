package primetest;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PrimeTest implements Runnable {
    public static final int LOOP_SLEEP_TIME = 1000;
    private final int numberToTest;
    private final Set<Integer> primeSet;
    private static int runningThreads;

    public PrimeTest(int numberToTest, Set<Integer> primeSet) {
        this.numberToTest = numberToTest;
        this.primeSet = primeSet;
    }

    public synchronized static int getRunningThreads() {
        return runningThreads;
    }

    public synchronized static void setRunningThreads(int runningThreads) {
        PrimeTest.runningThreads = runningThreads;
    }

    @Override
    public void run() {
        setRunningThreads(getRunningThreads() + 1);
        if (isPrime(numberToTest)) {
            synchronized (primeSet) {
                primeSet.add(numberToTest);
            }
        }
        setRunningThreads(getRunningThreads() - 1);
    }

    private boolean isPrime(int numberToTest) {
        if (numberToTest <= 1)
            return false;
        if (numberToTest % 2 == 0)
            return false;
        for (int i = 3; i <= Math.sqrt(numberToTest); i += 2) {
            if (numberToTest % i == 0)
                return false;
            try {
                Thread.sleep(LOOP_SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return true;
    }


    public static void main(String[] args) {
        Set<Integer> primes = new TreeSet<>();
        List<Thread> primeThreads =
                IntStream.range(1, 1000)
                        .mapToObj(value -> new PrimeTest(value, primes))
                        .map(Thread::new)
                        .collect(Collectors.toList());
        primeThreads.forEach(Thread::start);

        Thread daemon = new PrimeDaemonThread(primes);
        daemon.setDaemon(true);
        daemon.setPriority(Thread.MIN_PRIORITY);
        daemon.start();

        joinAll(primeThreads);
        System.out.println("-----------------------------------------------------------------");
        synchronized (primes) {
            System.out.println(primes);
        }
    }

    private static void joinAll(List<Thread> primeThreads) {
        for (Thread primeThread : primeThreads) {
            try {
                primeThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
