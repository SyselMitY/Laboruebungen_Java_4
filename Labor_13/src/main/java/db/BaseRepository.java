package db;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

public abstract class BaseRepository<T> {

    BaseRepository() {

    }

    Optional<T> findById(Class<T> c, Object id) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        return Optional.ofNullable(em.find(c, id));
    }

    List<T> findAll(Class<T> c) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        final String query = "select c from %s c".formatted(c.getSimpleName());
        return em.createQuery(query, c)
                .getResultList();
    }

    boolean persist(T t) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(t);
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

}
