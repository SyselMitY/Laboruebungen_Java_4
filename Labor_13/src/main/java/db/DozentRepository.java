package db;

import model.Dozent;

import java.util.List;
import java.util.Optional;

public class DozentRepository extends BaseRepository<Dozent> {

    private DozentRepository() {
    }

    private static DozentRepository INSTANCE;

    public static DozentRepository getInstance() {
        if (INSTANCE != null) {
            INSTANCE = new DozentRepository();
        }
        return INSTANCE;
    }

    public Optional<Dozent> findById(Object id) {
        return super.findById(Dozent.class, id);
    }

    public List<Dozent> findAll() {
        return super.findAll(Dozent.class);
    }

    @Override
    public boolean persist(Dozent dozent) {
        return super.persist(dozent);
    }
}
