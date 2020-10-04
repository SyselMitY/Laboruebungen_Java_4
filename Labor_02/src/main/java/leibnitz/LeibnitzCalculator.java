package leibnitz;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LeibnitzCalculator {
    public static void main(String[] args) {
        final int THREAD_COUNT = 7;
        final int RANGE = 99999;
        final int THREAD_RANGE = (int) ((double) RANGE / THREAD_COUNT);

        List<LeibnitzThread> threads = new ArrayList<>();

        for (int i = 0; i < THREAD_COUNT; i++) {
            int startRange = i * (THREAD_RANGE+1);
            int endRange = Math.min(startRange + (int) Math.ceil((double) RANGE / THREAD_COUNT)-1, RANGE);

            LeibnitzThread newThread = new LeibnitzThread(startRange, endRange);
            newThread.setName("Thread-" + i);
            threads.add(newThread);
        }

        printThreads(threads);
        threads.forEach(Thread::start);
        joinAll(threads);

        double pi = 4 * threads.stream()
                .mapToDouble(thread -> thread.getErg())
                .sum();
        System.out.println(pi);
    }

    private static void printThreads(List<LeibnitzThread> threads) {
        threads.forEach(thread -> System.out.printf("%s: [%6d,%6d] => %5d\n",
                thread.getName(),
                thread.getStart(),
                thread.getEndInclusive(),
                thread.getEndInclusive() - thread.getStart() + 1
        ));
    }

    private static void joinAll(Collection<LeibnitzThread> threads) {
        for (LeibnitzThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
