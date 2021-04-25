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

    public boolean clear() {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.createQuery("delete from Rental ").executeUpdate();
            em.createQuery("delete from Customer ").executeUpdate();
            em.createQuery("delete from Car").executeUpdate();
            em.createQuery("delete from Station ").executeUpdate();
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

    public Optional<Car> findCarById(String id) {
        return findEntityById(Car.class, id);
    }

    public Optional<Customer> findCustomerById(Integer id) {
        return findEntityById(Customer.class, id);
    }

    public Optional<Rental> findRentalById(Integer id) {
        return findEntityById(Rental.class, id);
    }
}
