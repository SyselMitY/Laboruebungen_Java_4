package labor11.model;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class Address {

    public Address() {
    }

    public Address(int zip, String street, String city) {
        this.city = city;
        this.street = street;
        this.zip = zip;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "addr_id")
    private int id;

    @Column(name = "addr_city", length = 60)
    private String city;

    @Column(name = "addr_street", length = 60)
    private String street;

    @Column(name = "addr_zip")
    private int zip;

    @OneToOne(mappedBy = "address")
    private Customer customer;
}
