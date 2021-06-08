package com.example.gutegadsen_backend.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Image implements Serializable {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private @NotNull String imageDataString;

    public Image(@NotNull String imageDataString) {
        this.imageDataString = imageDataString;
    }
}
