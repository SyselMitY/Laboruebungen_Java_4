package schuelerdb;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

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
                    "geschlecht varchar(1) ," +
                    "PRIMARY KEY (katalognummer,klasse))");
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
        delayFürProgessBar(700,3000);
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
        delayFürProgessBar(700,3000);
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT katalognummer,klasse,vorname,familienname,geschlecht\n" +
                        "FROM schueler\n" +
                        "WHERE geschlecht=?")) {
            statement.setString(1, String.valueOf(geschlecht));
            statement.execute();
            return getSchuelerFromResultSet(statement.getResultSet());
        }
    }

    @Override
    public Map<String, Long> getKlassen() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            delayFürProgessBar(700, 3000);
            statement.execute(
                    "SELECT klasse, COUNT(katalognummer)\n" +
                            "FROM schueler\n" +
                            "GROUP BY klasse\n" +
                            "ORDER BY klasse");
            return getKlassenMapFromResultSet(statement.getResultSet());
        }
    }

    private void delayFürProgessBar(int min, int max) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(min, max));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Map<String, Long> getKlassenMapFromResultSet(ResultSet resultSet) throws SQLException {
        Map<String, Long> klassenMap = new TreeMap<>();
        while (resultSet.next()) {
            String klasse = resultSet.getString("klasse");
            long schueler = resultSet.getLong(2);
            klassenMap.put(klasse, schueler);
        }
        return klassenMap;
    }

    @Override
    public void close() throws IOException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
}
