package db;

import model.Kunde;
import model.Kurs;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class KundeRepository extends BaseRepository<Kunde> {


    private KundeRepository() {
    }

    private static KundeRepository INSTANCE;

    public static KundeRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new KundeRepository();
        }
        return INSTANCE;
    }

    public Optional<Kunde> findById(Object id) {
        return super.findById(Kunde.class, id);
    }

    public List<Kunde> findAll() {
        return super.findAll(Kunde.class);
    }

    public Collection<Kunde> findByKurs(Kurs k) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        return mergeIfNotContained(k, em).getKunden();
    }

    @Override
    public boolean persist(Kunde kunde) {
        return super.persist(kunde);
    }

    @Override
    boolean delete(Kunde kunde) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            kunde = mergeIfNotContained(kunde, em);

            Kunde finalKunde = kunde;
            kunde.getKurse().forEach(finalKunde::removeKurs);

            em.remove(kunde);
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

    public void bucheKurs(Kunde kunde, Kurs kurs) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            kunde = mergeIfNotContained(kunde, em);
            kurs = mergeIfNotContained(kurs, em);

            kunde.addKurs(kurs);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx.isActive())
                tx.rollback();
        } finally {
            em.close();
        }

    }

    public void storniereKurs(Kunde kunde, Kurs kurs) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            kunde = mergeIfNotContained(kunde, em);
            kurs = mergeIfNotContained(kurs, em);

            kunde.removeKurs(kurs);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx.isActive())
                tx.rollback();
        } finally {
            em.close();
        }
    }

}
