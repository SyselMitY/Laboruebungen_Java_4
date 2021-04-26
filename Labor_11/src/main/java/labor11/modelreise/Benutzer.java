package labor11.modelreise;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "benutzer")
@Getter
@NoArgsConstructor
public class Benutzer {
    @Id
    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "passwort", nullable = false)
    @Size(min = 6, message = "Passwort mindestens 6 Zeichen lang")
    @NotNull
    private String passwort;

    @Column(name = "newsletter", nullable = false)
    private boolean newsletter;

    @ManyToMany
    @JoinTable(name = "benutzer_reisetyp", joinColumns = {@JoinColumn(name = "benutzer_id")},
            inverseJoinColumns = {@JoinColumn(name = "reisetyp_id")})
    private Set<Reisetyp> reisetypen;

    public Benutzer(String email, String passwort, boolean newsletter) {
        this.email = email;
        this.passwort = passwort;
        this.newsletter = newsletter;
        this.reisetypen = new HashSet<>();
    }

    public void addReisetyp(Reisetyp reisetyp) {
        reisetypen.add(reisetyp);
    }
}
