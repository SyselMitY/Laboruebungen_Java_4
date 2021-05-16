package model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@Getter
public class Mitarbeiter {
    @Id
    @Min(111111)
    @Max(999999)
    private Integer id;

    @NotNull
    private String lastname;

    @NotNull
    private String firstname;

    public Mitarbeiter(Integer id, String lastname, String firstname) {
        this.id = id;
        this.lastname = lastname;
        this.firstname = firstname;
    }
}
