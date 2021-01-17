package schuelerdb;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class SchuelerRepositoryTest {
    Connection con;
    SchuelerRepository rep;
    static String jdbc_url = null;
    static String jdbc_user = null;
    static String jdbc_pwd = null;

    @BeforeAll
    static void readProps() throws  IOException{
        InputStream in = SchuelerRepositoryTest.class.getResourceAsStream("/connection.properties");
        Properties props = new Properties();
        props.load(in);
        jdbc_url = props.getProperty("jdbc_url");
        jdbc_user = props.getProperty("jdbc_user");
        jdbc_pwd = props.getProperty("jdbc_pwd");
    }


    @BeforeEach
    void setUp() throws SQLException, IOException {
        con = DriverManager.getConnection(jdbc_url, jdbc_user, jdbc_pwd);
        rep = new JdbcSchuelerRepository(con);
        int result = rep.persistSchueler(SchuelerTools.readFromCSV("files/schueler.csv"));
    }

    @AfterEach
    void tearDown() throws  SQLException {
        con.close();
    }

    @Test
    void persistSchuelerOk() throws SQLException, IOException {
        int result = rep.persistSchueler(SchuelerTools.readFromCSV("files/schueler.csv"));
        assertThat(result).isEqualTo(352);
    }

    @Test
    void persistSchuelerFail() throws SQLException, IOException {
        assertThatExceptionOfType(SQLException.class).isThrownBy(() ->
                rep.persistSchueler(SchuelerTools.readFromCSV("files/schueler_err.csv")) );
    }

    @Test
    void deleteAll() throws SQLException {
        int result = rep.deleteAll();
        assertThat(result).isEqualTo(352);
    }

    @Test
    void findSchuelerByKlasse1AHIF() throws SQLException {
        List<Schueler> result = rep.findSchuelerByKlasse("1AHIF");
        assertThat(result.size()).isEqualTo(32);
        assertThat(result.get(31).getFamilienname()).isEqualTo("Zöchling");
    }

    @Test
    void findSchuelerByKlasseNotExist() throws SQLException {
        List<Schueler> result = rep.findSchuelerByKlasse("1DHIF");
        assertThat(result).isEmpty();
    }

    @Test
    void findSchuelerByGeschlecht_correctCount() throws SQLException {
        int count = rep.findSchuelerByGeschlecht('w').size();
        assertThat(count).isEqualTo(28);
    }

    @Test
    void findSchuelerByGeschlecht_nonexistantIsZero() throws SQLException {
        int count = rep.findSchuelerByGeschlecht('!').size();
        assertThat(count).isZero();
    }

    @Test
    void findSchuelerByGeschlecht_invalidThrows() throws SQLException {
        assertThatThrownBy(() -> rep.findSchuelerByGeschlecht('\0')).isInstanceOf(SQLException.class);
    }

    @Test
    void getKlassen_correctCount() throws SQLException {
        int count = rep.getKlassen().size();
        assertThat(count).isEqualTo(14);
    }

    @Test
    void getKlass_correctSchuelerCount() throws SQLException {
        long schuelerCount = rep.getKlassen().get("4CHIF");
        assertThat(schuelerCount).isEqualTo(22);
    }

}
