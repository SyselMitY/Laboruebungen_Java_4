package labor11;

import javax.persistence.*;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cust_Id")
    private int id;

    @Column(name = "cust_email",length = 60)
    private String email;

    @Column(name = "cust_firstname",length = 65)
    private String firstname;

    @Column(name = "cust_lastname",length = 65)
    private String lastname;

    @OneToOne(optional = false,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cust_addr_id")
    private Address address;
}
