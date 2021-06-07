package com.example.gutegadsen_backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class User implements Serializable {
    @Id
    @NotBlank
    @Pattern(regexp = "^[A-Za-z][A-Za-z1-9_]{3,15}$")
    @Size(min = 4, max = 16)
    private String id;

    @OneToMany
    private List<Post> postList;

    @ManyToMany(mappedBy = "upvoteList")
    private List<Post> upvoteList;

    @OneToOne
    private Image profilePicture;
}
