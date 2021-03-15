package labor11;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int id;

    @Column(name = "book_author",length = 255)
    private String author;

    @Column(name = "book_date")
    private LocalDate date;

    @Column(name = "book_isbn", length = 13)
    private String isbn;

    @Column(name = "book_title", length = 255)
    private String title;

    @JoinColumn(name = "book_pub_id")
    @ManyToOne(optional = false,cascade = CascadeType.PERSIST)
    private Publisher publisher;
}
