package db;

import model.Runner;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RunnerAppRepositoryTest {

    private static RunnerAppRepository repository;

    @org.junit.jupiter.api.BeforeAll
    static void setUp() {
        repository = RunnerAppRepository.getINSTANCE();
        repository.insertTestData();
    }

    @org.junit.jupiter.api.AfterAll
    static void tearDown() {
        repository.close();
    }

    // Testfall: testet repository.longRun(25000)
    // Liefert eine Liste mit der L ̈aufer Huber Karl
    @Test
    void testLongRun1() {
        List<Runner> runner = repository.longRun(25000);
        assertEquals(1, runner.size());
        assertEquals("Huber", runner.get(0).getLastName());
    }

    // Testfall: testet repository.longRun(10000)
    // Liefert eine Liste mit zwei L ̈aufern
    @Test
    void testLongRun2() {
        List<Runner> runner = repository.longRun(10000);
        assertEquals(2, runner.size());
    }

    // Testfall: testet repository.longRun(50000)
    // Liefert eine leere Liste
    @Test
    void testLongRun3() {
        List<Runner> runner = repository.longRun(50000);
        assertTrue(runner.isEmpty());
    }

    // Testfall: testet repository.longRun(-10000)
    // Erwartetes Ergebnis: wirft eine IllegalArgumentException
    @Test
    void testLongRun4() {
        assertThrows(IllegalArgumentException.class, () -> repository.longRun(-10000));
    }

    // Testfall: liefert die Gesamtstrecke des L ̈aufers mit der ID 1 im Juli 2019
    // Erwartetes Ergebnis: 36000
    @Test
    void testTotalDistance1() {
        Runner r = repository.findRunnerById(2).orElseThrow();
        System.out.println(r);
        assertEquals(r.getLastName(), "Schmidt");
        long distance = repository.totalDistance(r, LocalDate.of(2019, 7, 1), LocalDate.of(2019, 7, 31));
        assertEquals(36000, distance);
    }

    // Testfall: falsche Argumente
    // Erwartetes Ergebnis: Exception
    @Test
    void testTotalDistance2() {
        assertThrows(IllegalArgumentException.class, () -> repository.totalDistance(repository.findRunnerById(1).orElseThrow(),
                LocalDate.of(2019, 2, 8),
                LocalDate.of(2000, 3, 7)));

        assertThrows(IllegalArgumentException.class, () -> repository.totalDistance(null,
                LocalDate.of(2019, 2, 8),
                LocalDate.of(2021, 3, 7)));
    }

    // Testfall: liefert die Gesamtstrecke des L ̈aufers mit der ID 3 im Juni 2019
    // Erwartetes Ergebnis: 52545
    @Test
    void testTotalDistance3() {
        Runner r = repository.findRunnerById(1).orElseThrow();
        System.out.println(r);
        assertEquals("Huber", r.getLastName());
        long distance = repository.totalDistance(r, LocalDate.of(2019, 6, 1), LocalDate.of(2019, 6, 20));
        assertEquals(52545, distance);
    }
}