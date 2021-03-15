package kurssystem.persistence;

import kurssystem.domain.Kunde;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;


public class JdbcKundeRepositoryTest {

    private KundeRepository repository;
    private Connection connection;

    @BeforeEach
    void connect() throws SQLException {
        String jdbcUrl = "jdbc:h2:mem:test;INIT=RUNSCRIPT FROM 'classpath:/kurssystem_h2.sql'";
        connection = DriverManager.getConnection(jdbcUrl);
        repository = new JdbcKundeRepository(connection);
    }

    @AfterEach
    void disconnect() throws SQLException {
        connection.close();
    }

    @Test
    void testFindAll() throws SQLException {
        List<Kunde> kunden = repository.findAll();
        assertThat(kunden).extracting(Kunde::getId)
                .containsExactlyInAnyOrder(1, 2, 3, 4, 5, 6);
    }

    @Test
    void testFindByIdKundeExistsInDB() throws SQLException {
        Optional<Kunde> opt = repository.findById(4);
        assertThat(opt.get().getZuname()).isEqualTo("Kunze");
    }

    @Test
    void testFindByIdKundeDoesNotExistInDB() throws SQLException {
        Optional<Kunde> opt = repository.findById(9);
        assertThat(opt).isEmpty();
    }

    @Test
    void saveKunde() throws SQLException {
        Kunde toSave = new Kunde("Gradle", "Josef");
        Kunde saved = repository.save(toSave);

        assertThat(saved.getId()).isEqualTo(7);
        Optional<Kunde> opt = repository.findById(7);
        assertThat(opt.get().getZuname()).isEqualTo("Gradle");
    }

    @Test
    void saveKundeHasId() {
        Kunde toSave = new Kunde(32, "Jonk", "Jenk");
        assertThatIllegalArgumentException().isThrownBy(() -> repository.save(toSave));
    }

    @Test
    void deleteKundeOk() throws SQLException {
        boolean deleted = repository.deleteKunde(2);

        assertThat(deleted).isTrue();
        assertThat(repository.findById(2)).isEmpty();
    }

    @Test
    void deleteKundeWithCourses() throws SQLException {
        boolean deleted = repository.deleteKunde(1);
        assertThat(deleted).isFalse();
    }
}
