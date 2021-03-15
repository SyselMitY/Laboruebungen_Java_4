package kurssystem.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Kurs {
    private final Integer id;
    private final char typ;
    private final int dozId;
    private final String bezeichnung;
    private final LocalDate beginn;

    public Kurs(Integer id, char typ, int dozId, String bezeichnung, LocalDate beginn) {
        this.id = id;
        this.typ = typ;
        this.dozId = dozId;
        this.bezeichnung = bezeichnung;
        this.beginn = beginn;
    }

    public Kurs(char typ, int dozId, String bezeichnung, LocalDate beginn) {
        this.id = null;
        this.typ = typ;
        this.dozId = dozId;
        this.bezeichnung = bezeichnung;
        this.beginn = beginn;
    }

    public Integer getId() {
        return id;
    }

    public char getTyp() {
        return typ;
    }

    public int getDozId() {
        return dozId;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public LocalDate getBeginn() {
        return beginn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kurs kurs = (Kurs) o;
        return typ == kurs.typ && dozId == kurs.dozId && Objects.equals(id, kurs.id) && Objects.equals(bezeichnung, kurs.bezeichnung) && Objects.equals(beginn, kurs.beginn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, typ, dozId, bezeichnung, beginn);
    }
}
