package com.example.gutegadsen_backend.model;

import com.example.gutegadsen_backend.util.UserCreationRequestBody;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User implements Serializable {
    @Id
    @NotBlank
    @Pattern(regexp = "^[A-Za-z][A-Za-z1-9_]{3,15}$")
    @Size(min = 4, max = 16)
    @EqualsAndHashCode.Include
    private String username;

    @OneToMany
    private List<Post> postList;

    @ManyToMany(mappedBy = "upvoteList")
    private List<Post> upvoteList;

    @OneToOne
    private Image profilePicture;

    public User(String username, Image profilePicture) {
        this.username = username;
        this.profilePicture = profilePicture;
    }

    public User(UserCreationRequestBody requestBody) {
        this(requestBody.getUsername(), new Image(requestBody.getImageDataString()));
    }
}
