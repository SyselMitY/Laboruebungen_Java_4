package kurssystem.persistence;

import kurssystem.domain.Dozent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class JdbcDozentRepository implements DozentRepository {

    private final Connection connection;

    public JdbcDozentRepository(Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

    @Override
    public List<Dozent> findAll() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("SELECT doz_id,doz_zuname,doz_vorname FROM dozent;");
            return getListFromResultSet(statement.getResultSet());
        }
    }

    private List<Dozent> getListFromResultSet(ResultSet resultSet) throws SQLException {
        List<Dozent> list = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("doz_id");
            String name = resultSet.getString("doz_zuname");
            String vorname = resultSet.getString("doz_vorname");
            list.add(new Dozent(id, name, vorname));
        }
        return list;
    }

    @Override
    public Optional<Dozent> findById(Integer id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT doz_id,doz_zuname,doz_vorname FROM dozent WHERE doz_id = ?;")) {
            statement.setInt(1, id);
            statement.execute();
            List<Dozent> result = getListFromResultSet(statement.getResultSet());
            return result.stream().findFirst();
        }
    }

    @Override
    public Dozent save(Dozent dozent) throws SQLException {
        if (dozent.getId() != null) {
            throw new IllegalArgumentException("Dozent hat Id");
        } else {
            return saveWithoutId(dozent);
        }
    }

    private Dozent saveWithoutId(Dozent dozent) throws SQLException {
        String[] key = {"doz_id"};
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO dozent(doz_zuname,doz_vorname) VALUES (?,?);", key)) {
            statement.setString(1, dozent.getZuname());
            statement.setString(2, dozent.getVorname());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (!generatedKeys.next())
                throw new SQLException("No generated Keys found");
            return new Dozent(generatedKeys.getInt(1), dozent.getZuname(), dozent.getVorname());
        }
    }

    @Override
    public boolean deleteDozent(Integer id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM dozent WHERE doz_id = ?;")) {
            statement.setInt(1, id);
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            return false;
        }
    }
}
