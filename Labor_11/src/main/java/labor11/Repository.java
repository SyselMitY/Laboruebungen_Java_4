package labor11;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

public class Repository {
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

    public void close() {
        JPAUtil.close();
    }
}
