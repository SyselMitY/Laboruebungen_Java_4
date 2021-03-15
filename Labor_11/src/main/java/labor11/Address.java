package labor11;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class Address {

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

    @OneToOne(optional = false,mappedBy = "customer")
    private Customer customer;
}
