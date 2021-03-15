package kurssystem.persistence;

import kurssystem.domain.Dozent;
import kurssystem.domain.Kunde;
import kurssystem.domain.Kurs;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcKursRepository implements KursRepository {

    private final Connection connection;

    public JdbcKursRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Kurs> findAll() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("SELECT kurs_id,kurs_typ,kurs_doz_id,kurs_bezeichnung,kurs_beginndatum FROM kurs;");
            return getListFromResultSet(statement.getResultSet());
        }
    }

    private List<Kurs> getListFromResultSet(ResultSet resultSet) throws SQLException {
        List<Kurs> list = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("kurs_id");
            char typ = resultSet.getString("kurs_typ").charAt(0);
            int dozId = resultSet.getInt("kurs_doz_id");
            String bezeichnung = resultSet.getString("kurs_bezeichnung");
            LocalDate beginn = resultSet.getDate("kurs_beginndatum").toLocalDate();
            list.add(new Kurs(id, typ, dozId, bezeichnung, beginn));
        }
        return list;
    }

    @Override
    public List<Kurs> findAllByDozent(Dozent dozent) throws SQLException {
        if (dozent == null || dozent.getId() == null)
            throw new IllegalArgumentException("May not be null");

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             "SELECT kurs_id,kurs_typ,kurs_doz_id,kurs_bezeichnung,kurs_beginndatum " +
                                     "FROM kurs WHERE kurs_doz_id = ?;")) {
            statement.setInt(1, dozent.getId());
            statement.execute();
            return getListFromResultSet(statement.getResultSet());
        }
    }

    @Override
    public Optional<Kurs> findById(Integer id) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(
                             "SELECT kurs_id,kurs_typ,kurs_doz_id,kurs_bezeichnung,kurs_beginndatum " +
                                     "FROM kurs WHERE kurs_id = ?;")) {
            statement.setInt(1, id);
            statement.execute();
            return getListFromResultSet(statement.getResultSet())
                    .stream()
                    .findFirst();
        }
    }

    @Override
    public Kurs save(Kurs kurs) throws SQLException {
        if (kurs.getId() != null) {
            throw new IllegalArgumentException("Kurs hat Id");
        }
        String[] key = {"kurs_id"};
        try (PreparedStatement statement =
                     connection.prepareStatement(
                             "INSERT INTO kurs(kurs_typ,kurs_doz_id,kurs_bezeichnung,kurs_beginndatum)" +
                                     "VALUES(?,?,?,?);",key)) {
            statement.setString(1,String.valueOf(kurs.getDozId()));
            statement.setInt(2, kurs.getDozId());
            statement.setString(3,kurs.getBezeichnung());
            statement.setDate(4, Date.valueOf(kurs.getBeginn()));
            statement.execute();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (!generatedKeys.next())
                throw new SQLException("No generated Keys found");
            return new Kurs(generatedKeys.getInt(1), kurs.getTyp(), kurs.getDozId(), kurs.getBezeichnung(), kurs.getBeginn());
        }
    }

    @Override
    public List<Kurs> getKurseByKunde(Integer id) throws SQLException {
        if (id == null)
            throw new IllegalArgumentException("May not be null");

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             "SELECT kurs_id,kurs_typ,kurs_doz_id,kurs_bezeichnung,kurs_beginndatum " +
                                     "FROM kurs WHERE kurs_id IN (SELECT kurs_id FROM kurs_kunde WHERE kunde_id = ?);")) {
            statement.setInt(1,id);
            statement.execute();
            return getListFromResultSet(statement.getResultSet());
        }
    }

    @Override
    public boolean bucheKurs(Kunde kunde, Kurs kurs) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(
                             "INSERT INTO kurs_kunde(kunde_id, kurs_id) VALUES(?,?)")) {
            statement.setInt(1, kunde.getId());
            statement.setInt(2,kurs.getId());
            statement.executeUpdate();
            return statement.getUpdateCount() != 0;
        }
    }

    @Override
    public boolean storniereKurs(Kunde kunde, Kurs kurs) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(
                             "DELETE FROM kurs_kunde WHERE kunde_id = ? AND kurs_id = ?")) {
            statement.setInt(1, kunde.getId());
            statement.setInt(2,kurs.getId());
            statement.executeUpdate();
            return statement.getUpdateCount() != 0;
        }
    }
}
