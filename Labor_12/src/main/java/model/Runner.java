package model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Runner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String lastName;

    @NotNull
    private String firstName;

    @NotNull
    private LocalDate birthDay;

    @NotNull
    @Pattern(regexp = "[MW]")
    private String gender;

    @NotNull
    @Positive
    private Integer weight;

    @OneToMany(mappedBy = "runner")
    private List<Run> runs;

    public Runner(String lastName, String firstName, LocalDate birthDay, String gender, Integer weight) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDay = birthDay;
        this.gender = gender;
        this.weight = weight;
        this.runs = new ArrayList<>();
    }

    public void addRun(Run run) {
        runs.add(run);
        run.setRunner(this);
    }
}
