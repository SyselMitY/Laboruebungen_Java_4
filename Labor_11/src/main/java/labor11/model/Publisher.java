package labor11.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "publisher")
public class Publisher {
    @Id
    @Column(name = "pub_name",length = 100)
    private String name;

    @Column(name = "pub_description", length = 255)
    private String description;

    @OneToMany(mappedBy = "publisher")
    private List<Book> books;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Address> addresses;

    public Publisher(String name, String description) {
        this.name = name;
        this.description = description;
        this.books = new ArrayList<>();
        this.addresses = new ArrayList<>();
    }

    void addBook(Book book) {
        book.setPublisher(this);
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
