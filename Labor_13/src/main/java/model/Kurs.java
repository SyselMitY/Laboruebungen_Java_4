package model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class Kurs {
    @Id
    @Column(name = "kurs_id", nullable = false)
    @GeneratedValue
    private Integer kursId;

    @Basic
    @Column(name = "kurs_bezeichnung", nullable = true, length = 100)
    @Size(max = 100)
    private String kursBezeichnung;

    @Basic
    @Column(name = "kurs_beginndatum", nullable = true)
    private Date kursBeginndatum;

    @ManyToOne
    @JoinColumn(name = "kurs_typ", referencedColumnName = "typ_id")
    private Kurstyp kurstyp;

    @ManyToOne
    @JoinColumn(name = "kurs_doz_id", referencedColumnName = "doz_id")
    private Dozent dozent;

    @ManyToMany(mappedBy = "kurse")
    private List<Kunde> kunden;

    public Integer getKursId() {
        return kursId;
    }

    public void setKursId(Integer kursId) {
        this.kursId = kursId;
    }

    public String getKursBezeichnung() {
        return kursBezeichnung;
    }

    public void setKursBezeichnung(String kursBezeichnung) {
        this.kursBezeichnung = kursBezeichnung;
    }

    public Date getKursBeginndatum() {
        return kursBeginndatum;
    }

    public void setKursBeginndatum(Date kursBeginndatum) {
        this.kursBeginndatum = kursBeginndatum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Kurs kurs = (Kurs) o;

        if (kursId != null ? !kursId.equals(kurs.kursId) : kurs.kursId != null) return false;
        if (kursBezeichnung != null ? !kursBezeichnung.equals(kurs.kursBezeichnung) : kurs.kursBezeichnung != null)
            return false;
        if (kursBeginndatum != null ? !kursBeginndatum.equals(kurs.kursBeginndatum) : kurs.kursBeginndatum != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = kursId != null ? kursId.hashCode() : 0;
        result = 31 * result + (kursBezeichnung != null ? kursBezeichnung.hashCode() : 0);
        result = 31 * result + (kursBeginndatum != null ? kursBeginndatum.hashCode() : 0);
        return result;
    }

    public Kurstyp getKurstyp() {
        return kurstyp;
    }

    public void setKurstyp(Kurstyp kurstyp) {
        this.kurstyp = kurstyp;
    }

    public Dozent getDozent() {
        return dozent;
    }

    public List<Kunde> getKunden() {
        return kunden;
    }

    public void addKunde(Kunde kunde) {
        this.kunden.add(kunde);
        kunde.getKurse().add(this);
    }

    public Kurs(@Size(max = 100) String kursBezeichnung, Date kursBeginndatum, Kurstyp kurstyp, Dozent dozent) {
        this.kursBezeichnung = kursBezeichnung;
        this.kursBeginndatum = kursBeginndatum;
        this.kurstyp = kurstyp;
        this.dozent = dozent;
        this.kunden = new ArrayList<>();
        kurstyp.getKurse().add(this);
        dozent.getKurse().add(this);
    }
}
