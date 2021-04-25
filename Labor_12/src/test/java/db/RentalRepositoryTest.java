package db;

import model.Car;
import model.Customer;
import model.Rental;
import model.Station;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("ConstantConditions")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RentalRepositoryTest {

    private static final RentalRepository repository = RentalRepository.getINSTANCE();

//    @AfterEach
//    void tearDown() {
//        repository.clear();
//    }

    @Test
    @Order(1)
    void insertStation() {
        assertTrue(repository.insertStation(new Station("Kirchberg")));
        assertTrue(repository.insertStation(new Station("Traisen")));
        assertTrue(repository.insertStation(new Station("Meindlingsreith-Oberzipf")));
        assertFalse(repository.insertStation(new Station(null)));
    }

    @Test
    @Order(2)
    void insertCar() {
        assertTrue(repository.insertCar(new Car("PL123XY",
                2005,
                42,
                "Ibishu Pigeon",
                repository.findStationById(1).orElseThrow())));

        assertTrue(repository.insertCar(new Car("PL223XY",
                2006,
                46,
                "ETK Sunburst",
                repository.findStationById(1).orElseThrow())));

        assertTrue(repository.insertCar(new Car("PL323XY",
                2015,
                142,
                "Klazölla 17er",
                repository.findStationById(1).orElseThrow())));

        assertTrue(repository.insertCar(new Car("PL423XY",
                2007,
                142,
                "Snomius Drive",
                repository.findStationById(1).orElseThrow())));

        assertTrue(repository.insertCar(new Car("PL523XY",
                2008,
                4423,
                "Motorola Motodriver",
                repository.findStationById(2).orElseThrow())));

        assertTrue(repository.insertCar(new Car("PL623XY",
                2017,
                941,
                "Mikrotik RB751G",
                repository.findStationById(2).orElseThrow())));

        assertTrue(repository.insertCar(new Car("PL723XY",
                2016,
                4153,
                "Desinfektionsmittel Auto",
                repository.findStationById(2).orElseThrow())));

        assertTrue(repository.insertCar(new Car("PL823XY",
                2021,
                9312,
                "Fenstergriff Turbo",
                repository.findStationById(3).orElseThrow())));

        assertTrue(repository.insertCar(new Car("PL923XY",
                2020,
                8223,
                "Nikon D5600",
                repository.findStationById(3).orElseThrow())));

        assertTrue(repository.insertCar(new Car("PL1023XY",
                2018,
                81234,
                "Taschentiachl Marker",
                repository.findStationById(3).orElseThrow())));


        assertFalse(repository.insertCar(new Car(null,
                2018,
                81234,
                "Taschentiachl Marker",
                repository.findStationById(3).orElseThrow())));

        assertFalse(repository.insertCar(new Car("PL1023XY",
                null,
                81234,
                "Taschentiachl Marker",
                repository.findStationById(3).orElseThrow())));

        assertFalse(repository.insertCar(new Car("PL1023XY",
                2018,
                null,
                "Taschentiachl Marker",
                repository.findStationById(3).orElseThrow())));

        assertFalse(repository.insertCar(new Car("PL1023XY",
                2018,
                1234,
                null,
                repository.findStationById(3).orElseThrow())));

        assertFalse(repository.insertCar(new Car("PL1023XY",
                2018,
                1234,
                "Test Auto",
                null)));

    }

    @Test
    @Order(3)
    void insertCustomer() {
        assertTrue(repository.insertCustomer(new Customer(123456, "Oberreitberger", "Heinz")));
        assertTrue(repository.insertCustomer(new Customer(223456, "Scheinacher", "Karl")));
        assertTrue(repository.insertCustomer(new Customer(323456, "Bichler", "Tanja")));

        assertFalse(repository.insertCustomer(new Customer(123456, "Bichler", "Tanja")));

        assertFalse(repository.insertCustomer(new Customer(9234567, "Test", "Mensch")));
        assertFalse(repository.insertCustomer(new Customer(null, "Test", "Mensch")));
        assertFalse(repository.insertCustomer(new Customer(823456, null, "Mensch")));
        assertFalse(repository.insertCustomer(new Customer(723456, "Testiger", null)));
    }

    @Test
    @Order(4)
    void insertRental() {
        assertTrue(repository.insertRental(new Rental(
                LocalDate.of(2021, 1, 14),
                repository.findCarById("PL123XY").orElseThrow(),
                repository.findCustomerById(123456).orElseThrow())));

        assertTrue(repository.insertRental(new Rental(
                LocalDate.of(2021, 1, 17),
                repository.findCarById("PL423XY").orElseThrow(),
                repository.findCustomerById(223456).orElseThrow())));

        assertTrue(repository.insertRental(new Rental(
                LocalDate.of(2021, 1, 29),
                repository.findCarById("PL523XY").orElseThrow(),
                repository.findCustomerById(323456).orElseThrow())));

        assertTrue(repository.insertRental(new Rental(
                LocalDate.of(2021, 2, 4),
                repository.findCarById("PL723XY").orElseThrow(),
                repository.findCustomerById(223456).orElseThrow())));

        assertTrue(repository.insertRental(new Rental(
                LocalDate.of(2021, 2, 8),
                repository.findCarById("PL923XY").orElseThrow(),
                repository.findCustomerById(123456).orElseThrow())));

        //Already in use
        assertThrows(IllegalStateException.class, () -> repository.insertRental(new Rental(
                LocalDate.of(2021, 2, 8),
                repository.findCarById("PL923XY").orElseThrow(),
                repository.findCustomerById(323456).orElseThrow())));


        assertFalse(repository.insertRental(new Rental(
                null,
                repository.findCarById("PL1023XY").orElseThrow(),
                repository.findCustomerById(323456).orElseThrow())));
        assertThrows(NullPointerException.class, () -> repository.insertRental(new Rental(
                LocalDate.of(2021, 2, 8),
                null,
                repository.findCustomerById(323456).orElseThrow())));
        assertFalse(repository.insertRental(new Rental(
                LocalDate.of(2021, 2, 8),
                repository.findCarById("PL1023XY").orElseThrow(),
                null)));
        assertFalse(repository.insertRental(null));

    }

    @Test
    @Order(5)
    void returnCar() {
        Rental rental = repository.findRentalById(1).orElseThrow();

        int kmBefore = rental.getCar().getMileage();
        repository.returnCar(rental,
                repository.findStationById(2).orElseThrow(),
                LocalDate.of(2021, 5, 12), 51);
        int kmAfter = rental.getCar().getMileage();
        assertEquals(kmBefore + 51, kmAfter);
        assertNotNull(rental.getCar().getLocation());


        rental = repository.findRentalById(2).orElseThrow();

        kmBefore = rental.getCar().getMileage();
        repository.returnCar(rental,
                repository.findStationById(1).orElseThrow(),
                LocalDate.of(2021, 6, 12), 151);
        kmAfter = rental.getCar().getMileage();
        assertEquals(kmBefore + 151, kmAfter);
        assertNotNull(rental.getCar().getLocation());


        rental = repository.findRentalById(3).orElseThrow();

        kmBefore = rental.getCar().getMileage();
        repository.returnCar(rental,
                repository.findStationById(3).orElseThrow(),
                LocalDate.of(2021, 5, 12), 73);
        kmAfter = rental.getCar().getMileage();
        assertEquals(kmBefore + 73, kmAfter);
        assertNotNull(rental.getCar().getLocation());


        Rental finalRental = rental;
        assertThrows(IllegalStateException.class, () -> repository.returnCar(
                finalRental,
                repository.findStationById(3).orElseThrow(),
                LocalDate.of(2021, 5, 13),
                73));

        assertFalse(repository.returnCar(
                repository.findRentalById(4).orElseThrow(),
                repository.findStationById(2).orElseThrow(),
                LocalDate.of(1980, 5, 13),
                16
        ));

        assertFalse(repository.returnCar(
                repository.findRentalById(4).orElseThrow(),
                repository.findStationById(2).orElseThrow(),
                LocalDate.of(2021, 7, 13),
                -32
        ));
    }
}