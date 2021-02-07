package kurssystem.domain;

import java.util.Objects;

public class Kunde {
    private final Integer id;
    private final String zuname;
    private final String vorname;

    public Kunde(Integer id, String zuname, String vorname) {
        this.id = id;
        this.zuname = zuname;
        this.vorname = vorname;
    }

    public Kunde(String zuname, String vorname) {
        this.id = null;
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
        Kunde kunde = (Kunde) o;
        return Objects.equals(id, kunde.id) && Objects.equals(zuname, kunde.zuname) && Objects.equals(vorname, kunde.vorname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, zuname, vorname);
    }
}
