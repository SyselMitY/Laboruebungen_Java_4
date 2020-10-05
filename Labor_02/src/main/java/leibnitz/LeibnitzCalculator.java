package leibnitz;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LeibnitzCalculator {
    public static void main(String[] args) {
        final int THREAD_COUNT = 7;
        final int RANGE = 99999;
        final int THREAD_RANGE = RANGE / THREAD_COUNT;
        int remainder = RANGE % THREAD_COUNT;

        System.out.println(remainder);

        List<LeibnitzThread> threads = new ArrayList<>();

        int startRange;
        int endRange = -1;
        for (int i = 0; i < THREAD_COUNT; i++) {
            startRange = endRange + 1;
            endRange = startRange + THREAD_RANGE-1 + (remainder-- < 0 ? 0 : 1);
            LeibnitzThread newThread = new LeibnitzThread(startRange, endRange);
            newThread.setName("Thread-" + i);
            threads.add(newThread);
        }

        printThreads(threads);
        threads.forEach(Thread::start);
        joinAll(threads);

        double pi = 4 * threads.stream()
                .mapToDouble(LeibnitzThread::getErg)
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
