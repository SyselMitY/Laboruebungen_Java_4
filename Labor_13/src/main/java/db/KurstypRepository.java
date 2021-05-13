package db;

import model.Kurstyp;

import java.util.List;
import java.util.Optional;

public class KurstypRepository extends BaseRepository<Kurstyp> {

    private KurstypRepository() {
    }

    private static KurstypRepository INSTANCE;

    public static KurstypRepository getInstance() {
        if (INSTANCE != null) {
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
}
