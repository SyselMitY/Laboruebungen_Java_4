package db;

import model.Kurs;

import java.util.List;
import java.util.Optional;

public class KursRepository extends BaseRepository<Kurs> {
    public static void main(String[] args) {
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
