package com.example.gutegadsen_backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Getter
public class Image implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Lob
    @NotNull
    private byte[] imageByteArray;
}
