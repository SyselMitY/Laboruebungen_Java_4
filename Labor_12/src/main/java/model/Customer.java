package model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor
public class Customer {
    @Id
    @Min(value = 111111)
    @Max(value = 999999)
    private Integer customerNumber;

    @NotNull
    private String lastName;

    @NotNull
    private String firstName;

    public Customer(Integer customerNumber, String lastName, String firstName) {
        this.customerNumber = customerNumber;
        this.lastName = lastName;
        this.firstName = firstName;
    }
}
