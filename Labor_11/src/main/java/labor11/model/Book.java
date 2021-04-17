package labor11.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int id;

    @Column(name = "book_author", length = 255)
    private String author;

    @Column(name = "book_isbn", length = 13, unique = true)
    private String isbn;

    @Column(name = "book_title", length = 255)
    private String title;

    @Column(name = "book_date")
    private LocalDate date;

    @JoinColumn(name = "book_pub_id")
    @ManyToOne(optional = false)
    private Publisher publisher;

    @ManyToMany
    @JoinTable(joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    private Set<BookCategory> categories;

    public Book(String isbn, String title, String author, LocalDate date, Publisher publisher) {
        this.author = author;
        this.date = date;
        this.isbn = isbn;
        this.title = title;
        this.publisher = publisher;
        publisher.getBooks().add(this);
        this.categories = new HashSet<>();
    }

    public void addBookCategory(BookCategory bookCategory) {
        this.categories.add(bookCategory);
        bookCategory.getBookSet().add(this);
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
        publisher.getBooks().add(this);
    }
}
