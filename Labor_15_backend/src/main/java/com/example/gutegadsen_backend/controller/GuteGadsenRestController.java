package com.example.gutegadsen_backend.controller;

import com.example.gutegadsen_backend.db.ImageRepository;
import com.example.gutegadsen_backend.db.PostRepository;
import com.example.gutegadsen_backend.db.TagRepository;
import com.example.gutegadsen_backend.db.UserRepository;
import com.example.gutegadsen_backend.exception.PostNotFoundException;
import com.example.gutegadsen_backend.exception.UserNotFoundException;
import com.example.gutegadsen_backend.model.Post;
import com.example.gutegadsen_backend.model.Tag;
import com.example.gutegadsen_backend.model.User;
import com.example.gutegadsen_backend.util.PostCreationRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/posts/view")
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

    @GetMapping("/posts/view/{postId}")
    public Post getPostById(@PathVariable Long postId) throws PostNotFoundException {
        return postRepository
                .findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
    }

    @GetMapping("/tags")
    public List<String> getAllTags() {
        return tagRepository
                .findAll()
                .stream()
                .map(Tag::getName)
                .collect(Collectors.toList());
    }


    @PostMapping("/posts/create")
    public ResponseEntity<Post> createNewPost(@RequestBody PostCreationRequestBody body) {
        Post newPost = new Post(body,new User());
        Post savedPost = postRepository.save(newPost);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .replacePath("/posts/view/{id}")
                .build(savedPost.getId());
        return ResponseEntity.created(uri).body(savedPost);
    }
}
