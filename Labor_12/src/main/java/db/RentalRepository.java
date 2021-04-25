package db;

import model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.time.LocalDate;
import java.util.Optional;

public class RentalRepository {
    private final static RentalRepository INSTANCE = new RentalRepository();

    private RentalRepository() {
    }

    public static RentalRepository getINSTANCE() {
        return INSTANCE;
    }

    private boolean persist(Object object) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(object);
            tx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (tx.isActive())
                tx.rollback();
            return false;
        } finally {
            em.close();
        }
    }

    private boolean merge(Object object) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(object);
            tx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (tx.isActive())
                tx.rollback();
            return false;
        } finally {
            em.close();
        }
    }

    public <T> Optional<T> findEntityById(Class<T> c, Object id) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        try {
            return Optional.ofNullable(em.find(c, id));
        } finally {
            em.close();
        }
    }

    public boolean insertStation(Station station) {
        return persist(station);
    }

    public Optional<Station> findStationById(Integer id) {
        return findEntityById(Station.class, id);
    }

    public boolean insertCar(Car car) {
        return persist(car);
    }

    public boolean insertCustomer(Customer customer) {
        return persist(customer);
    }

    public boolean insertRental(Rental rental) {
        if (!persist(rental)) return false;
        return merge(rental.getCar());
    }

    public boolean returnCar(Rental r, Station returnStation, LocalDate returnDate, Integer km) {
        if (r.getKm() != null) throw new IllegalStateException("Car already returned");
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            if (!r.returnCar(returnStation, returnDate, km)) return false;
            em.merge(r);
            em.merge(r.getCar());
            tx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (tx.isActive())
                tx.rollback();
            return false;
        } finally {
            em.close();
        }
    }

//    public boolean clear() {
//        EntityManager em = JPAUtil.getEMF().createEntityManager();
//        EntityTransaction tx = em.getTransaction();
//        try {
//            tx.begin();
//            em.createQuery("delete from Rental ").executeUpdate();
//            em.createQuery("delete from Customer ").executeUpdate();
//            em.createQuery("delete from Car").executeUpdate();
//            em.createQuery("delete from Station ").executeUpdate();
//            tx.commit();
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            if (tx.isActive())
//                tx.rollback();
//            return false;
//        } finally {
//            em.close();
//        }
//    }

    public Optional<Car> findCarById(String id) {
        return findEntityById(Car.class, id);
    }

    public Optional<Customer> findCustomerById(Integer id) {
        return findEntityById(Customer.class, id);
    }

    public Optional<Rental> findRentalById(Integer id) {
        return findEntityById(Rental.class, id);
    }

    public static void main(String[] args) {
        RentalRepository repository = RentalRepository.getINSTANCE();

        repository.insertStation(new Station("Kirchberg"));
        repository.insertStation(new Station("Traisen"));
        repository.insertStation(new Station("Meindlingsreith-Oberzipf"));

        repository.insertCar(new Car("PL123XY", 2005, 42, "Ibishu Pigeon", repository.findStationById(1).orElseThrow()));
        repository.insertCar(new Car("PL223XY", 2006, 46, "ETK Sunburst", repository.findStationById(1).orElseThrow()));
        repository.insertCar(new Car("PL323XY", 2015, 142, "Klazölla 17er", repository.findStationById(1).orElseThrow()));
        repository.insertCar(new Car("PL423XY", 2007, 142, "Snomius Drive", repository.findStationById(1).orElseThrow()));
        repository.insertCar(new Car("PL523XY", 2008, 4423, "Motorola Motodriver", repository.findStationById(2).orElseThrow()));
        repository.insertCar(new Car("PL623XY", 2017, 941, "Mikrotik RB751G", repository.findStationById(2).orElseThrow()));
        repository.insertCar(new Car("PL723XY", 2016, 4153, "Desinfektionsmittel Auto", repository.findStationById(2).orElseThrow()));
        repository.insertCar(new Car("PL823XY", 2021, 9312, "Fenstergriff Turbo", repository.findStationById(3).orElseThrow()));
        repository.insertCar(new Car("PL923XY", 2020, 8223, "Nikon D5600", repository.findStationById(3).orElseThrow()));
        repository.insertCar(new Car("PL1023XY", 2018, 81234, "Taschentiachl Marker", repository.findStationById(3).orElseThrow()));

        repository.insertCustomer(new Customer(123456, "Oberreitberger", "Heinz"));
        repository.insertCustomer(new Customer(223456, "Scheinacher", "Karl"));
        repository.insertCustomer(new Customer(323456, "Bichler", "Tanja"));

        repository.insertRental(new Rental(LocalDate.of(2021, 1, 14), repository.findCarById("PL123XY").orElseThrow(), repository.findCustomerById(123456).orElseThrow()));
        repository.insertRental(new Rental(LocalDate.of(2021, 1, 17), repository.findCarById("PL423XY").orElseThrow(), repository.findCustomerById(223456).orElseThrow()));
        repository.insertRental(new Rental(LocalDate.of(2021, 1, 29), repository.findCarById("PL523XY").orElseThrow(), repository.findCustomerById(323456).orElseThrow()));
        repository.insertRental(new Rental(LocalDate.of(2021, 2, 4), repository.findCarById("PL723XY").orElseThrow(), repository.findCustomerById(223456).orElseThrow()));
        repository.insertRental(new Rental(LocalDate.of(2021, 2, 8), repository.findCarById("PL923XY").orElseThrow(), repository.findCustomerById(123456).orElseThrow()));

        repository.returnCar(repository.findRentalById(1).orElseThrow(), repository.findStationById(2).orElseThrow(), LocalDate.of(2021, 5, 12), 51);
        repository.returnCar(repository.findRentalById(2).orElseThrow(), repository.findStationById(1).orElseThrow(), LocalDate.of(2021, 6, 12), 151);
        repository.returnCar(repository.findRentalById(3).orElseThrow(), repository.findStationById(3).orElseThrow(), LocalDate.of(2021, 5, 12), 73);
    }
}
