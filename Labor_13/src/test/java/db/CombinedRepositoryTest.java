package db;

import model.Dozent;
import model.Kunde;
import model.Kurs;
import model.Kurstyp;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
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
        repository.insertKunde(new Kunde("Litunde", "Krana"));
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
        assertEquals("Litunde", kunden.get(0).getKundeZuname());
    }

    @Test
    @Order(3)
    void insertKurstyp() {
        repository.insertKurstyp(new Kurstyp("C", "Computertechnik"));
        repository.insertKurstyp(new Kurstyp("K", "Kohlebergbau"));
        assertThrows(KursDBException.class, () -> repository.insertKurstyp(new Kurstyp("ab", "Falsch")));
    }

    @Test
    @Order(4)
    void getKurstypen() {
        List<Kurstyp> kurstypList = repository.getKurstypen();
        assertEquals(2, kurstypList.size());
        assertEquals("C", kurstypList.get(0).getTypId());
    }

    @Test
    @Order(5)
    void insertDozent() {
        repository.insertDozent(new Dozent("Author", "Christian"));
        repository.insertDozent(new Dozent("Armel", "Othmar"));
        repository.insertDozent(new Dozent("Nichtstu", "Nathan"));
        assertThrows(KursDBException.class, () -> repository.insertDozent(new Dozent("Zulangernamederaucheineexceptionwerfensoll", "Herr")));
    }

    @Test
    @Order(6)
    void getDozenten() {
        List<Dozent> dozentList = repository.getDozenten();
        assertEquals(3, dozentList.size());
        assertEquals("Armel", dozentList.get(1).getDozZuname());
    }

    @Test
    @Order(7)
    void insertKurs() {
        List<Kurstyp> kurstypList = repository.getKurstypen();
        List<Dozent> dozentList = repository.getDozenten();
        repository.insertKurs(new Kurs("Spitzhacken bedienen und benutzen",
                LocalDate.of(2021, 6, 20),
                kurstypList.get(1),
                dozentList.get(0)));
        repository.insertKurs(new Kurs("Einen 4-bit Full-Adder mit Wasser konstruieren",
                LocalDate.of(2021, 7, 10),
                kurstypList.get(0),
                dozentList.get(1)));
        repository.insertKurs(new Kurs("Wie man Kohle nachhaltig nutzt",
                LocalDate.of(2021, 7, 15),
                kurstypList.get(1),
                dozentList.get(2)));
        assertThrows(KursDBException.class,
                () -> repository.insertKurs(new Kurs("langlanglanglanglanglanglanglanglanglanglanglanglanglanglanglanglanglanglanglanglanglanglanglanglanglang",
                        LocalDate.of(2021, 7, 10),
                        kurstypList.get(0),
                        dozentList.get(1))));
    }

    @Test
    @Order(8)
    void getKurse() {
        List<Kurs> kursList = repository.getKurse();
        assertEquals(3, kursList.size());
        assertEquals("Spitzhacken bedienen und benutzen", kursList.get(0).getKursBezeichnung());
    }

    @Test
    @Order(9)
    void bucheKurs() {
        List<Kunde> kunden = repository.getKunden();
        List<Kurs> kurse = repository.getKurse();
        repository.bucheKurs(kunden.get(0),kurse.get(0));
        repository.bucheKurs(kunden.get(0),kurse.get(1));
        kurse = repository.getKurse();
        kunden = repository.getKunden();
        assertEquals(2,kunden.get(0).getKurse().size());

        repository.bucheKurs(kunden.get(1),kurse.get(1));
        repository.bucheKurs(kunden.get(1),kurse.get(2));
        kurse = repository.getKurse();
        kunden = repository.getKunden();
        assertEquals(2,kurse.get(1).getKunden().size());

        repository.bucheKurs(kunden.get(2),kurse.get(1));
        kurse = repository.getKurse();
        kunden = repository.getKunden();
        assertEquals(3,kurse.get(1).getKunden().size());
        assertEquals(1,kunden.get(2).getKurse().size());
    }

    @Test
    @Order(10)
    void getKundenFromKurs() {
        List<Kurs> kurse = repository.getKurse();

        assertEquals(3,repository.getKundenFromKurs(kurse.get(1)).size());
        assertEquals(1,repository.getKundenFromKurs(kurse.get(0)).size());
        assertEquals(1,repository.getKundenFromKurs(kurse.get(2)).size());
    }

    @Test
    @Order(11)
    void storniereKurs() {
        List<Kunde> kunden = repository.getKunden();
        List<Kurs> kurse = repository.getKurse();
        repository.storniereKurs(kunden.get(2),kurse.get(1));
        kurse = repository.getKurse();
        kunden = repository.getKunden();
        assertEquals(2,kurse.get(1).getKunden().size());
        assertEquals(0,kunden.get(2).getKurse().size());
    }

    @Test
    @Order(12)
    void deleteKunde() {
        List<Kunde> kunden = repository.getKunden();
        repository.deleteKunde(kunden.get(1));

        List<Kurs> kurse = repository.getKurse();

        assertEquals(4, repository.getKunden().size());
        assertEquals(1,kurse.get(0).getKunden().size());
        assertEquals(1,kurse.get(1).getKunden().size());
        assertEquals(0,kurse.get(2).getKunden().size());
    }

    @Test
    @Order(12)
    void deleteKurstyp() {
        List<Kurstyp> kurstypen = repository.getKurstypen();
        repository.deleteKurstyp(kurstypen.get(1));
        kurstypen = repository.getKurstypen();

        assertEquals(1,kurstypen.size());
        assertEquals(1,repository.getKurse().size());
        assertEquals(1,repository.getKunden().get(0).getKurse().size());
    }
}