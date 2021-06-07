package com.example.gutegadsen_backend.controller;

import com.example.gutegadsen_backend.db.ImageRepository;
import com.example.gutegadsen_backend.db.PostRepository;
import com.example.gutegadsen_backend.db.TagRepository;
import com.example.gutegadsen_backend.db.UserRepository;
import com.example.gutegadsen_backend.exception.UserNotFoundException;
import com.example.gutegadsen_backend.model.Post;
import com.example.gutegadsen_backend.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GuteGadsenRestController {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final ImageRepository imageRepository;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{username}")
    public User getUserByUsername(@PathVariable String username) throws UserNotFoundException {
        return userRepository
                .findById(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    @GetMapping("/posts")
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @GetMapping("/users/{username}/posts")
    public List<Post> getPostsByUsername(@PathVariable String username) throws UserNotFoundException {
        userRepository
                .findById(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return postRepository.findAllByUserUsername(username);
    }
}
