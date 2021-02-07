package kurssystem.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Dozent {
    private final Integer id;
    private final String zuname;
    private final String vorname;

    public Dozent(String zuname, String vorname) {
        this.id = null;
        this.zuname = zuname;
        this.vorname = vorname;
    }

    public Dozent(Integer id, String zuname, String vorname) {
        this.id = id;
        this.zuname = zuname;
        this.vorname = vorname;
    }

    public Integer getId() {
        return id;
    }

    public String getZuname() {
        return zuname;
    }

    public String getVorname() {
        return vorname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dozent dozent = (Dozent) o;
        return Objects.equals(id, dozent.id) && Objects.equals(zuname, dozent.zuname) && Objects.equals(vorname, dozent.vorname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, zuname, vorname);
    }
}
