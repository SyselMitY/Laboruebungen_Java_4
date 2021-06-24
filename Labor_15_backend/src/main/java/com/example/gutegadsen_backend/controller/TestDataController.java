package com.example.gutegadsen_backend.controller;

import com.example.gutegadsen_backend.db.ImageRepository;
import com.example.gutegadsen_backend.db.PostRepository;
import com.example.gutegadsen_backend.db.TagRepository;
import com.example.gutegadsen_backend.db.UserRepository;
import com.example.gutegadsen_backend.model.Image;
import com.example.gutegadsen_backend.model.Post;
import com.example.gutegadsen_backend.model.Tag;
import com.example.gutegadsen_backend.model.User;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class TestDataController {

    public TestDataController(UserRepository userRepository, PostRepository postRepository, ImageRepository imageRepository, TagRepository tagRepository) throws IOException {
        Tag testTag = tagRepository.save(new Tag("Liab"));
        InputStream resourceAsStream = TestDataController.class.getResourceAsStream("/exampe_pics/gadser.txt");
        String imageString = new BufferedReader(new InputStreamReader(resourceAsStream)).readLine();
        Image testImage = imageRepository.save(new Image(imageString));
        User testUser = userRepository.save(new User("Katzenfreund123", testImage));

        postRepository.save(new Post("Katzer", testUser, Set.of(testTag), testImage));

        tagRepository.save(new Tag("Katze"));
        tagRepository.save(new Tag("Kätzchen"));
        tagRepository.save(new Tag("Lustig"));
        tagRepository.save(new Tag("Schwarz"));
        tagRepository.save(new Tag("Weiß"));
        tagRepository.save(new Tag("Mehrfarbig"));
        tagRepository.save(new Tag("Gestreift"));
        tagRepository.save(new Tag("Orange"));
    }

}
