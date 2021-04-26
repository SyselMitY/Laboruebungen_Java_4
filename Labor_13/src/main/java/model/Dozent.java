package model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
public class Dozent {
    @Id
    @Column(name = "doz_id", nullable = false)
    @GeneratedValue
    private Integer dozId;

    @Basic
    @Column(name = "doz_zuname", nullable = true, length = 25)
    @Size(max = 25)
    private String dozZuname;

    @Basic
    @Column(name = "doz_vorname", nullable = true, length = 25)
    @Size(max = 25)
    private String dozVorname;

    @OneToMany(mappedBy = "dozent")
    private List<Kurs> kurse;

    public Integer getDozId() {
        return dozId;
    }

    public void setDozId(Integer dozId) {
        this.dozId = dozId;
    }

    public String getDozZuname() {
        return dozZuname;
    }

    public void setDozZuname(String dozZuname) {
        this.dozZuname = dozZuname;
    }

    public String getDozVorname() {
        return dozVorname;
    }

    public void setDozVorname(String dozVorname) {
        this.dozVorname = dozVorname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dozent dozent = (Dozent) o;

        if (dozId != null ? !dozId.equals(dozent.dozId) : dozent.dozId != null) return false;
        if (dozZuname != null ? !dozZuname.equals(dozent.dozZuname) : dozent.dozZuname != null) return false;
        if (dozVorname != null ? !dozVorname.equals(dozent.dozVorname) : dozent.dozVorname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dozId != null ? dozId.hashCode() : 0;
        result = 31 * result + (dozZuname != null ? dozZuname.hashCode() : 0);
        result = 31 * result + (dozVorname != null ? dozVorname.hashCode() : 0);
        return result;
    }

    public List<Kurs> getKurse() {
        return kurse;
    }

    public void setKurse(List<Kurs> kurse) {
        this.kurse = kurse;
    }

    public Dozent(@Size(max = 25) String dozZuname, @Size(max = 25) String dozVorname) {
        this.dozZuname = dozZuname;
        this.dozVorname = dozVorname;
        this.kurse = new ArrayList<>();
    }
}
