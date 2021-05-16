package db;

import model.Kunde;
import model.Kurs;

import javax.persistence.EntityManager;
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

    public List<Kunde> findByKurs(Kurs k) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        return em.createQuery("SELECT k.kunden FROM Kurs k WHERE k = :kurs", Kunde.class)
                .setParameter("kurs", k)
                .getResultList();
    }

    @Override
    public boolean persist(Kunde kunde) {
        return super.persist(kunde);
    }

    @Override
    boolean delete(Kunde kunde) {
        return super.delete(kunde);
    }
}
