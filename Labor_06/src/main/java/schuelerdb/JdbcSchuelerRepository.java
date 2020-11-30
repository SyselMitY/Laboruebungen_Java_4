package schuelerdb;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class JdbcSchuelerRepository implements SchuelerRepository {

    Connection connection;

    public JdbcSchuelerRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int persistSchueler(List<Schueler> lst) throws SQLException {
        return 0;
    }

    @Override
    public int deleteAll() throws SQLException {
        return 0;
    }

    @Override
    public List<Schueler> findSchuelerByKlasse(String klasse) throws SQLException {
        return null;
    }

    @Override
    public List<Schueler> findSchuelerByGeschlecht(char geschlecht) throws SQLException {
        return null;
    }

    @Override
    public Map<String, Integer> getKlassen() throws SQLException {
        return null;
    }
}
