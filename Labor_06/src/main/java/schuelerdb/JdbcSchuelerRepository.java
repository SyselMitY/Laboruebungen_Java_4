package schuelerdb;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcSchuelerRepository implements SchuelerRepository {

    Connection connection;

    public JdbcSchuelerRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int persistSchueler(List<Schueler> lst) throws SQLException {
        deleteIfExists();
        createSchuelerTable();
        int updateCount = 0;
        for (Schueler schueler : lst) {
            updateCount += addSchueler(schueler);
        }
        return updateCount;
    }

    private void createSchuelerTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE schueler (" +
                    "katalognummer int," +
                    "klasse varchar(10) ," +
                    "vorname varchar(64) ," +
                    "familienname varchar(64) ," +
                    "geschlecht varchar(1))");
        }
    }

    private void deleteIfExists() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS schueler");
        }
    }

    public int addSchueler(Schueler schueler) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO schueler VALUES(?,?,?,?,?)")) {
            statement.setInt(1, schueler.getKatalognummer());
            statement.setString(2, schueler.getKlasse());
            statement.setString(3, schueler.getVorname());
            statement.setString(4, schueler.getFamilienname());
            statement.setString(5, String.valueOf(schueler.getGeschlecht()));
            return statement.executeUpdate();
        }
    }

    @Override
    public int deleteAll() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM schueler");
            return statement.getUpdateCount();
        }
    }

    @Override
    public List<Schueler> findSchuelerByKlasse(String klasse) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT katalognummer,klasse,vorname,familienname,geschlecht\n" +
                        "FROM schueler\n" +
                        "WHERE klasse = ?")) {
            statement.setString(1, klasse);
            statement.execute();
            return getSchuelerFromResultSet(statement.getResultSet());
        }
    }

    private List<Schueler> getSchuelerFromResultSet(ResultSet resultSet) throws SQLException {
        List<Schueler> schuelers = new ArrayList<>();
        while (resultSet.next()) {
            int katalognummer = resultSet.getInt("katalognummer");
            String klasse = resultSet.getString("klasse");
            String vorname = resultSet.getString("vorname");
            String familienname = resultSet.getString("familienname");
            String geschlecht = resultSet.getString("geschlecht");
            schuelers.add(new Schueler(familienname, vorname, geschlecht.charAt(0), katalognummer, klasse));
        }
        return schuelers;
    }

    @Override
    public List<Schueler> findSchuelerByGeschlecht(char geschlecht) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT katalognummer,klasse,vorname,nachname,geschlecht\n" +
                        "FROM schueler\n" +
                        "WHERE geschlecht=?")) {
            statement.setString(1, String.valueOf(geschlecht));
            statement.execute();
            return getSchuelerFromResultSet(statement.getResultSet());
        }
    }

    @Override
    public Map<String, Integer> getKlassen() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(
                    "SELECT klasse, COUNT(katalognummer)\n" +
                            "FROM schueler\n" +
                            "GROUP BY klasse\n" +
                            "ORDER BY klasse");
            return getKlassenMapFromResultSet(statement.getResultSet());
        }
    }

    private Map<String, Integer> getKlassenMapFromResultSet(ResultSet resultSet) throws SQLException {
        Map<String, Integer> klassenMap = new HashMap<>();
        while (resultSet.next()) {
            String klasse = resultSet.getString("klasse");
            int schueler = resultSet.getInt("COUNT(katalognummer)");
            klassenMap.put(klasse, schueler);
        }
        return klassenMap;
    }
}
