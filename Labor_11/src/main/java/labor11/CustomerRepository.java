package labor11;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class CustomerRepository implements AutoCloseable {
    private final static CustomerRepository INSTANCE = new CustomerRepository();

    private CustomerRepository() {

    }

    public static CustomerRepository getINSTANCE() {
        return INSTANCE;
    }

    public boolean persist(Customer customer) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(customer);
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

    public void close() {
        JPAUtil.close();
    }
}
