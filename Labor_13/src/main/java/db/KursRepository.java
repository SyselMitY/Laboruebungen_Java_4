package db;

import model.Kurs;

import java.util.List;
import java.util.Optional;

public class KursRepository extends BaseRepository<Kurs> {

    private KursRepository() {
    }

    private static KursRepository INSTANCE;

    public static KursRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new KursRepository();
        }
        return INSTANCE;
    }

    public Optional<Kurs> findById(Object id) {
        return super.findById(Kurs.class, id);
    }

    public List<Kurs> findAll() {
        return super.findAll(Kurs.class);
    }

    @Override
    public boolean persist(Kurs dozent) {
        return super.persist(dozent);
    }
}
