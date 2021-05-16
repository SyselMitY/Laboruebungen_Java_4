package model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
public class Kurstyp {
    @Id
    @Column(name = "typ_id", nullable = false, length = 1)
    @Size(min = 1,max = 1)
    private String typId;

    @Basic
    @Column(name = "typ_bezeichnung", nullable = true, length = 100)
    @Size(max = 100)
    private String typBezeichnung;

    @OneToMany(mappedBy = "kurstyp")
    private List<Kurs> kurse;

    public String getTypId() {
        return typId;
    }

    public void setTypId(String typId) {
        this.typId = typId;
    }

    public String getTypBezeichnung() {
        return typBezeichnung;
    }

    public void setTypBezeichnung(String typBezeichnung) {
        this.typBezeichnung = typBezeichnung;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Kurstyp kurstyp = (Kurstyp) o;

        if (typId != null ? !typId.equals(kurstyp.typId) : kurstyp.typId != null) return false;
        if (typBezeichnung != null ? !typBezeichnung.equals(kurstyp.typBezeichnung) : kurstyp.typBezeichnung != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = typId != null ? typId.hashCode() : 0;
        result = 31 * result + (typBezeichnung != null ? typBezeichnung.hashCode() : 0);
        return result;
    }

    public Collection<Kurs> getKurse() {
        return kurse;
    }

    public void setKurse(List<Kurs> kurse) {
        this.kurse = kurse;
    }

    public Kurstyp(@Size(min = 1, max = 1) String typId, @Size(max = 100) String typBezeichnung) {
        this.typId = typId;
        this.typBezeichnung = typBezeichnung;
        this.kurse = new ArrayList<>();
    }
}
