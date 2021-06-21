package com.example.gutegadsen_backend.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Image implements Serializable {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;

    @Lob
    @NotNull
    @Basic(fetch = FetchType.LAZY)
    private String imageDataString;

    public Image(@NotNull String imageDataString) {
        this.imageDataString = imageDataString;
    }
}
