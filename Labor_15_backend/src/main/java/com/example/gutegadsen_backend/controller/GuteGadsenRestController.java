package com.example.gutegadsen_backend.controller;

import com.example.gutegadsen_backend.db.ImageRepository;
import com.example.gutegadsen_backend.db.PostRepository;
import com.example.gutegadsen_backend.db.TagRepository;
import com.example.gutegadsen_backend.db.UserRepository;
import com.example.gutegadsen_backend.exception.*;
import com.example.gutegadsen_backend.model.Image;
import com.example.gutegadsen_backend.model.Post;
import com.example.gutegadsen_backend.model.Tag;
import com.example.gutegadsen_backend.model.User;
import com.example.gutegadsen_backend.util.PostCreationRequestBody;
import com.example.gutegadsen_backend.util.UserCreationRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin
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

    @PostMapping("/users/create")
    public ResponseEntity<User> createUser(@RequestBody UserCreationRequestBody body) throws UserAlreadyExistsException {
        User existingUser = userRepository
                .findById(body.getUsername())
                .orElse(null);

        if (existingUser != null) {
            throw new UserAlreadyExistsException(body.getUsername());
        }

        User newUser = new User(body);
        imageRepository.save(newUser.getProfilePicture());
        User savedUser = userRepository.save(newUser);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .replacePath("/users/{id}")
                .build(savedUser.getUsername());
        return ResponseEntity.created(uri).body(savedUser);
    }

    @GetMapping("/posts/list")
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @GetMapping("/posts/user/{username}/")
    public List<Post> getPostsByUsername(@PathVariable String username) throws UserNotFoundException {
        userRepository
                .findById(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return postRepository.findAllByUserUsername(username);
    }

    @GetMapping("/posts/tag/{tagName}")
    public List<Post> getPostsByTag(@PathVariable String tagName) throws TagNotFoundException {
        Tag tag = tagRepository
                .findById(tagName)
                .orElseThrow(() -> new TagNotFoundException(tagName));
        return postRepository.findAllByTagListContaining(tag);
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
    public ResponseEntity<Post> createPost(@RequestBody PostCreationRequestBody body) throws UserNotFoundException {
        User user = userRepository
                .findById(body.getUserName())
                .orElseThrow(() -> new UserNotFoundException(body.getUserName()));
        Post newPost = new Post(body, user);
        imageRepository.save(newPost.getImage());
        Post savedPost = postRepository.save(newPost);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .replacePath("/posts/view/{id}")
                .build(savedPost.getId());
        return ResponseEntity.created(uri).body(savedPost);
    }

    @GetMapping("/images/{imageId}")
    public Image getImageById(@PathVariable Long imageId) throws ImageNotFoundException {
        return imageRepository.findById(imageId)
                .orElseThrow(() -> new ImageNotFoundException(imageId));
    }
}
