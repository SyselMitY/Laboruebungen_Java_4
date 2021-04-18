package labor11.modelreise;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "reisetyp")
@Getter
@NoArgsConstructor
public class Reisetyp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "bezeichnung",nullable = false)
    @NotNull
    private String bezeichnung;

    @OneToMany(mappedBy = "reisetyp")
    private Set<Reiseveranstaltung> reiseveranstaltungen;

    public Reisetyp(String bezeichnung) {
        this.bezeichnung = bezeichnung;
        this.reiseveranstaltungen = new HashSet<>();
    }
}
