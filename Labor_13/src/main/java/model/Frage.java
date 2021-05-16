package model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
public class Frage {
    @Id
    @GeneratedValue
    private Integer id;

    @Size(max = 20)
    @NotNull
    private String heading;

    @Size(max = 200)
    @NotNull
    private String text;

    @NotNull
    private LocalDate expirationDate;

    public Frage(String heading, String text, LocalDate expirationDate) {
        this.heading = heading;
        this.text = text;
        this.expirationDate = expirationDate;
    }
}
