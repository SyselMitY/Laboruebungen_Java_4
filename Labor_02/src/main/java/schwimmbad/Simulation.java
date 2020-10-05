package schwimmbad;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Simulation {

    public static void main(String[] args) {
        Schwimmbad bad = new Schwimmbad(5);
        List<Badegast> badegasts = IntStream.rangeClosed(1, 50)
                .mapToObj(value -> new Badegast("Badegast_" + value, bad))
                .collect(Collectors.toList());
        badegasts.forEach(Thread::start);
        joinAll(badegasts);
        System.out.println("Alle Badegäste haben das Bad verlassen");
    }

    private static void joinAll(List<Badegast> badegasts) {
        for (Badegast badegast : badegasts) {
            try {
                badegast.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
