package db;

import model.Antwort;
import model.AntwortId;
import model.Frage;
import model.Mitarbeiter;
import org.junit.jupiter.api.*;


import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.validation.ValidationException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UmfrageRepositoryTest {

    private static UmfrageRepository repository;

    @BeforeAll
    static void beforeAll() {
        repository = UmfrageRepository.getInstance();
    }

    @Test
    @Order(1)
    void testInsertMitarbeiter() {
        repository.persist(new Mitarbeiter(123456, "Huber", "Franz"));
        repository.persist(new Mitarbeiter(123457, "Zvonimir", "Karli"));
        repository.persist(new Mitarbeiter(123458, "Zigil-Schupf", "Hannes"));

        assertThrows(ValidationException.class, () -> repository.persist(new Mitarbeiter(997454, null, "Fehler")));
        assertThrows(ValidationException.class, () -> repository.persist(new Mitarbeiter(687525, "null", null)));
        assertThrows(ValidationException.class, () -> repository.persist(new Mitarbeiter(675387, "null", null)));
        assertThrows(ValidationException.class, () -> repository.persist(new Mitarbeiter(1111119, "null", "Hermes")));
        assertThrows(ValidationException.class, () -> repository.persist(new Mitarbeiter(12345, "Vojinsko", "Baralisek")));
    }

    @Test
    @Order(2)
    void testInsertFragen() {
        repository.persist(new Frage("Essen", "Ist das Essen gut und schmeckt es?", LocalDate.of(2021, 5, 20)));
        repository.persist(new Frage("Heisl", "San de Heisl akzeptabel und vertretbar?", LocalDate.of(2021, 5, 25)));

        assertThrows(ValidationException.class, () -> repository.persist(new Frage(null, "Test", LocalDate.now())));
        assertThrows(ValidationException.class, () -> repository.persist(new Frage("Test", null, LocalDate.now())));
        assertThrows(ValidationException.class, () -> repository.persist(new Frage("Test", "Test", null)));
        assertThrows(ValidationException.class, () -> repository.persist(new Frage("TestTestTestTestTestt",
                "TestTest12TestTest12TestTest12TestTest12TestTest12TestTest12TestTest12TestTest12TestTest12TestTest12TestTest12TestTest12TestTest12TestTest12TestTest12TestTest12TestTest12TestTest12TestTest12TestTest12",
                LocalDate.now())));
    }

    @Test
    @Order(3)
    void testAntwort() {
        List<Mitarbeiter> mitarbeiter = repository.findAll(Mitarbeiter.class);
        List<Frage> fragen = repository.findAll(Frage.class);
        repository.persist(new Antwort(new AntwortId(mitarbeiter.get(0), fragen.get(0)), LocalDate.now(), Antwort.Antwortmöglichkeit.Teilweise_Zustimmung));
        repository.persist(new Antwort(new AntwortId(mitarbeiter.get(0), fragen.get(1)), LocalDate.now(), Antwort.Antwortmöglichkeit.Keine_Zustimmung));

        repository.persist(new Antwort(new AntwortId(mitarbeiter.get(1), fragen.get(0)), LocalDate.now(), Antwort.Antwortmöglichkeit.Voellige_Zustimmung));
        repository.persist(new Antwort(new AntwortId(mitarbeiter.get(1), fragen.get(1)), LocalDate.now(), Antwort.Antwortmöglichkeit.Voellige_Zustimmung));

        assertThrows(PersistenceException.class,
                () -> repository.beantworteFrage(new Antwort(new AntwortId(mitarbeiter.get(1), fragen.get(1)), LocalDate.now(), Antwort.Antwortmöglichkeit.Voellige_Zustimmung)));
        assertThrows(IllegalStateException.class,
                () -> repository.beantworteFrage(new Antwort(new AntwortId(mitarbeiter.get(2), fragen.get(0)), LocalDate.of(2022,5,21), Antwort.Antwortmöglichkeit.Voellige_Zustimmung)));
    }
}