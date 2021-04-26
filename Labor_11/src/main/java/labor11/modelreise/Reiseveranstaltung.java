package labor11.modelreise;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "reiseveranstaltung")
@Getter
@NoArgsConstructor
public class Reiseveranstaltung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "zielort", nullable = false, length = 30)
    @Size(max = 30)
    @NotNull
    private String zielort;

    @Column(name = "beschreibung", nullable = false, length = 300)
    @Size(max = 300)
    @NotNull
    private String beschreibung;

    @Column(name = "beginn", nullable = false)
    @NotNull
    private LocalDate beginn;

    @Column(name = "ende")
    private LocalDate ende;

    @Column(name = "preis", nullable = false)
    private double preis;

    @ManyToOne(optional = false)
    @NotNull
    private Reisetyp reisetyp;

    public Reiseveranstaltung(String zielort, String beschreibung, LocalDate beginn, LocalDate ende, double preis, Reisetyp reisetyp) {
        this.zielort = zielort;
        this.beschreibung = beschreibung;
        this.beginn = beginn;
        this.ende = ende;
        this.preis = preis;
        this.reisetyp = reisetyp;
        reisetyp.getReiseveranstaltungen().add(this);
    }
}
