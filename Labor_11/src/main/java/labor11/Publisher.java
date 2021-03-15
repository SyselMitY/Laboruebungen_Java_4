package labor11;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "publisher")
public class Publisher {
    @Id
    @Column(name = "pub_name",length = 100)
    private String name;

    @Column(name = "pub_description", length = 255)
    private String description;

    @OneToMany(mappedBy = "book")
    private List<Book> books;
}
