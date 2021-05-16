package model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
public class Kunde {
    @Id
    @Column(name = "kunde_id", nullable = false)
    @GeneratedValue
    private Integer kundeId;

    @Basic
    @Column(name = "kunde_zuname", nullable = true, length = 25)
    @Size(max = 25)
    private String kundeZuname;

    @Basic
    @Column(name = "kunde_vorname", nullable = true, length = 25)
    @Size(max = 25)
    private String kundeVorname;

    @ManyToMany
    @JoinTable(name = "kurs_kunde", catalog = "db_4chif_21_labor_13", schema = "public",
            joinColumns = @JoinColumn(name = "kunde_id", referencedColumnName = "kunde_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "kurs_id", referencedColumnName = "kurs_id", nullable = false))
    private List<Kurs> kurse;

    public Integer getKundeId() {
        return kundeId;
    }

    public void setKundeId(Integer kundeId) {
        this.kundeId = kundeId;
    }

    public String getKundeZuname() {
        return kundeZuname;
    }

    public void setKundeZuname(String kundeZuname) {
        this.kundeZuname = kundeZuname;
    }

    public String getKundeVorname() {
        return kundeVorname;
    }

    public void setKundeVorname(String kundeVorname) {
        this.kundeVorname = kundeVorname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Kunde kunde = (Kunde) o;

        if (!Objects.equals(kundeId, kunde.kundeId)) return false;
        if (!Objects.equals(kundeZuname, kunde.kundeZuname)) return false;
        if (!Objects.equals(kundeVorname, kunde.kundeVorname)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = kundeId != null ? kundeId.hashCode() : 0;
        result = 31 * result + (kundeZuname != null ? kundeZuname.hashCode() : 0);
        result = 31 * result + (kundeVorname != null ? kundeVorname.hashCode() : 0);
        return result;
    }

    public List<Kurs> getKurse() {
        return kurse;
    }

    public void addKurs(Kurs kurs) {
        this.kurse.add(kurs);
        kurs.getKunden().add(this);
    }

    public Kunde(@Size(max = 25) String kundeZuname, @Size(max = 25) String kundeVorname) {
        this.kundeZuname = kundeZuname;
        this.kundeVorname = kundeVorname;
        this.kurse = new ArrayList<>();
    }

    public void removeKurs(Kurs kurs) {
        this.kurse.remove(kurs);
        kurs.getKunden().remove(this);
    }
}
