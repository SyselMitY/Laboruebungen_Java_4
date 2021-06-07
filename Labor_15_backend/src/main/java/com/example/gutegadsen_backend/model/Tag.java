package com.example.gutegadsen_backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Getter
public class Tag implements Serializable {
    @Id
    @Size(min = 2,max = 16)
    private String name;
}
