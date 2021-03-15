package kurssystem.persistence;

import kurssystem.domain.Kunde;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcKundeRepository implements KundeRepository {

    private final Connection connection;

    public JdbcKundeRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Kunde> findAll() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("SELECT kunde_id,kunde_zuname,kunde_vorname FROM kunde;");
            return getListFromResultSet(statement.getResultSet());
        }
    }

    private List<Kunde> getListFromResultSet(ResultSet resultSet) throws SQLException {
        List<Kunde> list = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("kunde_id");
            String name = resultSet.getString("kunde_zuname");
            String vorname = resultSet.getString("kunde_vorname");
            list.add(new Kunde(id, name, vorname));
        }
        return list;
    }

    @Override
    public Optional<Kunde> findById(Integer id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT kunde_id,kunde_zuname,kunde_vorname FROM kunde WHERE kunde_id = ?;")) {
            statement.setInt(1, id);
            statement.execute();
            List<Kunde> result = getListFromResultSet(statement.getResultSet());
            return result.stream().findFirst();
        }
    }

    @Override
    public Kunde save(Kunde kunde) throws SQLException {
        if (kunde.getId() != null) {
            throw new IllegalArgumentException("Kunde hat Id");
        } else {
            return saveWithoutId(kunde);
        }
    }

    private Kunde saveWithoutId(Kunde kunde) throws SQLException {
        String[] key = {"kunde_id"};
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO kunde(kunde_zuname,kunde_vorname) VALUES (?,?);", key)) {
            statement.setString(1, kunde.getZuname());
            statement.setString(2, kunde.getVorname());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (!generatedKeys.next())
                throw new SQLException("No generated Keys found");
            return new Kunde(generatedKeys.getInt(1), kunde.getZuname(), kunde.getVorname());
        }
    }

    @Override
    public boolean deleteKunde(Integer id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM kunde WHERE kunde_id = ?;")) {
            statement.setInt(1, id);
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            return false;
        }
    }
}
