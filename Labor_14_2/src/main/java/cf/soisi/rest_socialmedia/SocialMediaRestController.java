package cf.soisi.rest_socialmedia;

import cf.soisi.rest_socialmedia.model.Post;
import cf.soisi.rest_socialmedia.repository.PostRepository;
import cf.soisi.rest_socialmedia.model.User;
import cf.soisi.rest_socialmedia.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;
import java.util.List;

@RestController
public class SocialMediaRestController {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public SocialMediaRestController(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @GetMapping(path = "/users")
    public Collection<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping(path = "/users")
    public ResponseEntity<User> saveUser(@RequestBody @Valid User user) {
        User saved = userRepository.save(user);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .replacePath("/users/{id}")
                .build(saved.getId());
        return ResponseEntity.created(uri).body(saved);
    }

    @GetMapping(path = "/users/{id}")
    public User getUserById(@PathVariable Integer id) {
        return userRepository.findById(id).orElseThrow();
    }

    @DeleteMapping(path = "/users/{id}")
    public void deleteUserById(@PathVariable Integer id) {
        userRepository.deleteById(id);
    }

    @GetMapping(path = "/users/{id}/posts")
    public List<Post> getPostsByUserId(@PathVariable Integer id) {
        return postRepository.findPostsByUser_Id(id);
    }

    @PostMapping(path = "/users/{userId}/posts")
    public ResponseEntity<Post> createPost(@PathVariable Integer userId, @RequestBody Post unsafePost) {
        User user = userRepository.findById(userId).orElseThrow();
        Post post = new Post(unsafePost.getMessage(), user);

        Post saved = postRepository.save(post);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .replacePath("/posts({id}")
                .build(saved.getId());
        return ResponseEntity.created(uri).body(saved);
    }

    @GetMapping(path = "/posts")
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @GetMapping(path = "/posts/{id}")
    public Post getPostById(@PathVariable Integer id) {
        return postRepository.findById(id).orElseThrow();
    }
}
