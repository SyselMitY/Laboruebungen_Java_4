package com.example.gutegadsen_backend.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Post implements Serializable {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull
    private LocalDate postDate;

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

    public Post(String title, User user, Set<Tag> tagList, Image image) {
        this.title = title;
        this.user = user;
        this.tagList = tagList;
        this.image = image;
        this.postDate = LocalDate.now();

        this.upvoteList = new HashSet<>();
        this.tagList = new HashSet<>();
        this.tagList.addAll(tagList);
    }
}
