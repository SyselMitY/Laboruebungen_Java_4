package labor11;

import labor11.model.Address;
import labor11.model.Book;
import labor11.model.BookCategory;
import labor11.model.Publisher;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Repository implements AutoCloseable {
    private final static Repository INSTANCE = new Repository();

    private Repository() {

    }

    public static Repository getINSTANCE() {
        return INSTANCE;
    }

    public <T, U> Optional<T> getEntityById(U id, Class<T> c) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        try {
            return Optional.ofNullable(em.find(c, id));
        } finally {
            em.close();
        }
    }

    public boolean persist(Object object) {
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

    public <T> List<T> findAll(Class<T> c) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        try {
            return em.createQuery("select c from " + c.getSimpleName() + " c", c).getResultList();
        } finally {
            em.close();
        }
    }

    public boolean insertTestData() {
        Publisher p1 = new Publisher("entwickler.press", "Fachverlag fur Softwareentwicklung");
        Publisher p2 = new Publisher("Michael M uller Verlag", "Reisef uhrer und Romane");
        BookCategory bc1 = new BookCategory("EDV-Fachbuch");
        BookCategory bc2 = new BookCategory("Datenbanken");
        BookCategory bc3 = new BookCategory("Reisef uhrer");
        LocalDate d1 = LocalDate.of(2012, 3, 20);
        LocalDate d2 = LocalDate.of(2008, 1, 17);
        Book b1 = new Book("9783868020144", "JPA mit Hibernate", "Daniel R oder", d1, p1);
        b1.addBookCategory(bc1);
        b1.addBookCategory(bc2);
        Book b2 = new Book("9783777020144", "Das Java Persistance API", "John Smith", d2, p1);
        b2.addBookCategory(bc1);
        b2.addBookCategory(bc2);
        Book b3 = new Book("3831713502", "Pyrenaen", "Michael Schuh", d2, p2);
        b3.addBookCategory(bc3);
        Book b4 = new Book("9783899533388", "Toskana", "Michael M uller", d1, p2);
        b4.addBookCategory(bc3);
        p1.getAddresses().add(new Address(1010, "Herrengasse 17", "Wien"));
        p1.getAddresses().add(new Address(3100, "Linzerstraße 37", "St.P olten"));
        p2.getAddresses().add(new Address(2473, "Obere Hauptstraße 32", "Prellenkirchen"));

        p1.getAddresses().add(new Address(3200, "Hauptstraße 20", "Ober Grafendorf"));
        Book[] books = {b1, b2, b3, b4};
        BookCategory[] categories = {bc1, bc2, bc3};
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(p1);
            em.persist(p2);
            Arrays.stream(categories).forEach(em::persist);
            Arrays.stream(books).forEach(em::persist);
            tx.commit();
            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
            if (tx.isActive()) {
                tx.rollback();
            }
            return false;

        } finally {
            em.close();
        }
    }

    public void close() {
        JPAUtil.close();
    }
}
