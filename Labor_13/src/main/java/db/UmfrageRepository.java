package db;

import model.Antwort;
import model.Frage;
import model.Mitarbeiter;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class UmfrageRepository implements AutoCloseable {

    private static UmfrageRepository INSTANCE;

    public static UmfrageRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UmfrageRepository();
        }
        return INSTANCE;
    }

    public boolean beantworteFrage(Antwort antwort) {
        LocalDate expirationDate = antwort.getId().getFrage().getExpirationDate();
        if(antwort.getTimestamp().isAfter(expirationDate))
            throw new IllegalStateException("Frage has already expired");
        return this.persist(antwort);
    }

    public <T> Optional<T> findById(Class<T> c, Object id) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        return Optional.ofNullable(em.find(c, id));
    }

    public <T> List<T> findAll(Class<T> c) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        final String query = "select c from %s c".formatted(c.getSimpleName());
        return em.createQuery(query, c)
                .getResultList();
    }

    public <T> boolean persist(T t) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(t);
            tx.commit();
            return true;
        }  catch (Exception e) {
            e.printStackTrace();
            if (tx.isActive())
                tx.rollback();
            if(e.getCause() instanceof RuntimeException)
                throw (RuntimeException) e.getCause();
            return false;
        } finally {
            em.close();
        }
    }

    public <T> boolean delete(T t) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            t = mergeIfNotContained(t, em);
            em.remove(t);
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

    <T> T mergeIfNotContained(T o, EntityManager em) {
        return em.contains(o) ? o : em.merge(o);
    }

    @Override
    public void close() throws Exception {
        JPAUtil.close();
    }

}
