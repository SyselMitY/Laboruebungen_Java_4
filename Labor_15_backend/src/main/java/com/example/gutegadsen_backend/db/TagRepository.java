package com.example.gutegadsen_backend.db;

import com.example.gutegadsen_backend.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface TagRepository extends JpaRepository<Tag, String> {
}
