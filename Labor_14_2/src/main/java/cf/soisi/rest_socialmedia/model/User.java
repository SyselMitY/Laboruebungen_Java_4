package cf.soisi.rest_socialmedia.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class User implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    @NotNull
    @Size(min = 3)
    private String name;

    @Past
    @NotNull
    private LocalDate birthday;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Post> postList;

    public User(String name, LocalDate birthday) {
        this.name = name;
        this.birthday = birthday;
        this.postList = new ArrayList<>();
    }
}
