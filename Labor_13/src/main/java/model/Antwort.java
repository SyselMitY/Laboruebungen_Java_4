package model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Antwort {

    @EmbeddedId
    private AntwortId id;

    private LocalDate timestamp;

    @Enumerated(EnumType.STRING)
    private Antwortmöglichkeit antwort;

    public enum Antwortmöglichkeit {
        Voellige_Zustimmung, Teilweise_Zustimmung, Keine_Zustimmung
    }

    public Antwort(AntwortId id, LocalDate timestamp, Antwortmöglichkeit antwort) {
        this.id = id;
        this.timestamp = timestamp;
        this.antwort = antwort;
    }
}
