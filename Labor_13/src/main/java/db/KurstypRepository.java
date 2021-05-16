package db;

import model.Kunde;
import model.Kurs;
import model.Kurstyp;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class KurstypRepository extends BaseRepository<Kurstyp> {

    private KurstypRepository() {
    }

    private static KurstypRepository INSTANCE;

    public static KurstypRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new KurstypRepository();
        }
        return INSTANCE;
    }

    public Optional<Kurstyp> findById(Object id) {
        return super.findById(Kurstyp.class, id);
    }

    public List<Kurstyp> findAll() {
        return super.findAll(Kurstyp.class);
    }

    @Override
    public boolean persist(Kurstyp dozent) {
        return super.persist(dozent);
    }

    @Override
    boolean delete(Kurstyp kurstyp) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            kurstyp = mergeIfNotContained(kurstyp, em);
            List<Kurs> kurseToDelete = em.createQuery("SELECT k FROM Kurs k WHERE k.kurstyp = :kurstyp", Kurs.class)
                    .setParameter("kurstyp", kurstyp)
                    .getResultList();
            for (Kurs kurs : kurseToDelete) {
                final Iterator<Kunde> iterator = kurs.getKunden().iterator();
                while (iterator.hasNext()) {
                    iterator.next().getKurse().remove(kurs);
                    iterator.remove();
                }
                em.remove(kurs);
            }
            em.remove(kurstyp);
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
