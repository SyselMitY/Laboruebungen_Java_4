package cf.soisi.rest_socialmedia.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
public class Post implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    @NotBlank
    private String message;

    @ManyToOne(optional = false)
    @Setter
    private User user;


    public Post(String message, User user) {
        this.message = message;
        this.user = user;
        this.user.getPostList().add(this);
    }
}
