package model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Car {
    @Id
    private String registrationNr;

    @NotNull
    private Integer constructionYear;

    @NotNull
    @Setter
    private Integer mileage;

    @NotNull
    private String model;

    @ManyToOne
    @Setter(AccessLevel.PACKAGE)
    private Station location;

    @OneToMany(mappedBy = "car")
    private List<Rental> rentalList;

    public Car(String registrationNr, Integer constructionYear, Integer mileage, String model, @NotNull Station location) {
        this.registrationNr = registrationNr;
        this.constructionYear = constructionYear;
        this.mileage = mileage;
        this.model = model;
        this.location = location;
        this.rentalList = new ArrayList<>();
    }
}
