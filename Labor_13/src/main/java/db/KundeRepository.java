package db;

import model.Kunde;

import java.util.List;
import java.util.Optional;

public class KundeRepository extends BaseRepository<Kunde> {
    public static void main(String[] args) {
    }

    public Optional<Kunde> findById(Object id) {
        return super.findById(Kunde.class, id);
    }

    public List<Kunde> findAll() {
        return super.findAll(Kunde.class);
    }

    @Override
    public boolean persist(Kunde dozent) {
        return super.persist(dozent);
    }
}
