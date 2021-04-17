package labor11.model;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="bookcategory")
@Getter
@NoArgsConstructor
public class BookCategory {

    @Id
    @Column(name = "cat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cat_description")
    private String description;

    @ManyToMany(mappedBy = "categories")
    private Set<Book> bookSet;

    public BookCategory(String description) {
        this.description = description;
        this.bookSet = new HashSet<>();
    }
}
