package db;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

public abstract class BaseRepository<T> {
    protected Optional<T> findById(Class<T> c, Object id) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        return Optional.ofNullable(em.find(c, id));
    }

    protected List<T> findAll(Class<T> c) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        return em.createQuery("select c from " + c.getSimpleName() + " c", c)
                .getResultList();
    }

    protected boolean persist(T t) {
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
