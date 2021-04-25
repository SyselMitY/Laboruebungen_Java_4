package model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
public class Rental {
    @Id
    @GeneratedValue
    private Integer id;

    private Integer km;

    @NotNull
    private LocalDate rentalDate;

    private LocalDate returnDate;

    @ManyToOne(optional = false)
    private Station rentalStation;

    @ManyToOne
    private Station returnStation;

    @ManyToOne(optional = false)
    private Car car;

    @ManyToOne(optional = false)
    private Customer driver;

    public Rental(LocalDate rentalDate, @NotNull Car car, Customer driver) {
        if (Objects.requireNonNull(car).getLocation() == null) throw new IllegalStateException("Car is already in use");
        this.rentalDate = rentalDate;
        this.rentalStation = car.getLocation();
        this.car = car;
        this.driver = driver;
        car.getRentalList().add(this);
        car.setLocation(null);
    }

    public boolean returnCar(Station returnStation, LocalDate returnDate, Integer km) {
        if (returnDate.isBefore(rentalDate) || km <= 0) return false;

        this.returnStation = returnStation;
        this.returnDate = returnDate;
        this.km = km;
        this.car.setLocation(returnStation);
        this.car.setMileage(this.car.getMileage() + km);
        return true;
    }
}
