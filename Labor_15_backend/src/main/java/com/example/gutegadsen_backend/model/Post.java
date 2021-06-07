package com.example.gutegadsen_backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
public class Post implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(min = 4, max = 64)
    private String title;

    @ManyToOne(optional = false)
    private User user;

    @ManyToMany
    private Set<User> upvoteList;

    @ManyToMany
    private Set<Tag> tagList;

    @OneToOne(optional = false)
    private Image image;
}
