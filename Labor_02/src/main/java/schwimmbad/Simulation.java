package schwimmbad;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Simulation {

    public static final int SCHWIMBAD_HAWARA_ANZAHL = 50;
    public static final int MAX_LIEGEN = 5;

    public static void main(String[] args) {
        Schwimmbad bad = new Schwimmbad(MAX_LIEGEN);
        List<Badegast> badegasts = IntStream.rangeClosed(1, SCHWIMBAD_HAWARA_ANZAHL)
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
