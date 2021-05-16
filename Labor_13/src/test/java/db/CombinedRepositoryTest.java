package db;

import model.Kunde;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CombinedRepositoryTest {

    private static CombinedRepository repository;

    @BeforeAll
    static void beforeAll() {
        repository = CombinedRepository.getInstance();
    }

    @Test
    @Order(1)
    void insertKunde() {
        repository.insertKunde(new Kunde("Bubba", "Krana"));
        repository.insertKunde(new Kunde("Bär", "Müdi"));
        repository.insertKunde(new Kunde("Stoascheißa", "Koarl"));
        repository.insertKunde(new Kunde("Blatt", "Peter"));
        repository.insertKunde(new Kunde("Mofuggin", "Matteo"));

        assertThrows(KursDBException.class, () -> repository.insertKunde(new Kunde("Vielzulangernamederdievalidationfehlschlagenlässt", "Herr")));
    }

    @Test
    @Order(2)
    void getKunden() {
        List<Kunde> kunden = repository.getKunden();
        assertEquals(5, kunden.size());
        assertEquals("Bubba", kunden.get(0).getKundeZuname());
    }

    @Test
    @Order(3)
    void deleteKunde() {
        List<Kunde> kunden = repository.getKunden();
        repository.deleteKunde(kunden.get(1));

        assertEquals(4, repository.getKunden().size());
    }

    @Test
    void getDozenten() {
    }

    @Test
    void getKurstypen() {
    }

    @Test
    void getKurse() {
    }

    @Test
    void insertKurstyp() {
    }

    @Test
    void deleteKurstyp() {
    }

    @Test
    void insertKurs() {
    }

    @Test
    void getKundenFromKurs() {
    }

    @Test
    void bucheKurs() {
    }

    @Test
    void storniereKurs() {
    }
}