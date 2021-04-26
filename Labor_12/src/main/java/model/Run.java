package model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Run {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private LocalDate date;

    @NotNull
    private Integer minutes;

    @NotNull
    private Integer distance;

    @ManyToOne(optional = false)
    private Runner runner;

    public Run(LocalDate date, Integer minutes, Integer distance) {
        this.date = date;
        this.minutes = minutes;
        this.distance = distance;
    }

    public void setRunner(Runner runner) {
        this.runner = runner;
        runner.getRuns().add(this);
    }
}
