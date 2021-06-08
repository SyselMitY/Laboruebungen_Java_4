package com.example.gutegadsen_backend.controller;

import com.example.gutegadsen_backend.db.ImageRepository;
import com.example.gutegadsen_backend.db.PostRepository;
import com.example.gutegadsen_backend.db.TagRepository;
import com.example.gutegadsen_backend.db.UserRepository;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestDataController {

    public TestDataController(UserRepository userRepository, PostRepository postRepository, ImageRepository imageRepository, TagRepository tagRepository) {

    }

}
