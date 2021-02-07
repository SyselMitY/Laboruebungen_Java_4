package kurssystem.persistence;

import kurssystem.domain.Dozent;
import kurssystem.domain.Kunde;
import kurssystem.domain.Kurs;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class JdbcKursRepositoryTest {

    public static final Kurs TEST_KURS = new Kurs('W', 2, "Neuer Toller Kurs", LocalDate.now());
    private Connection connection;
    private KursRepository repository;

    @BeforeEach
    void setUp() throws SQLException {
        String jdbcUrl = "jdbc:h2:mem:test;INIT=RUNSCRIPT FROM 'classpath:/kurssystem_h2.sql'";
        connection = DriverManager.getConnection(jdbcUrl);
        repository = new JdbcKursRepository(connection);
    }

    @AfterEach
    void disconnect() throws SQLException {
        connection.close();
    }

    @Test
    void findAll() throws SQLException {
        assertThat(repository.findAll())
                .extracting(Kurs::getId)
                .contains(1, 2, 3, 4, 5, 6);
    }

    @Test
    void findAllByDozent() throws SQLException {
        assertThat(repository.findAllByDozent(new Dozent(2,"","")))
                .extracting(Kurs::getId)
                .contains(1, 3);
    }

    @Test
    void findById() throws SQLException {
        assertThat(repository.findById(4)).isNotEmpty();
    }

    @Test
    void save() throws SQLException {
        Kurs saved = repository.save(TEST_KURS);
        assertThat(saved.getId()).isEqualTo(7);

        assertThat(repository.findById(7)).get().extracting(Kurs::getBezeichnung).isEqualTo("Neuer Toller Kurs");
    }

    @Test
    void getKurseByKunde() throws SQLException {
        assertThat(repository.getKurseByKunde(3))
                .extracting(Kurs::getId)
                .contains(1, 2);
    }

    @Test
    void bucheStorniereKurs() throws SQLException {
        Kurs kurs = repository.findById(5).orElseThrow();
        Kunde kunde = new Kunde(4, "Kunze", "Sieglinde");
        assertThat(repository.bucheKurs(kunde, kurs)).isTrue();
        assertThat(repository.getKurseByKunde(4)).contains(kurs);

        assertThat(repository.storniereKurs(kunde, kurs)).isTrue();
        assertThat(repository.getKurseByKunde(4)).doesNotContain(kurs);
    }

}