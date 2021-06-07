package cf.soisi.rest_socialmedia;

import cf.soisi.rest_socialmedia.exception.PostNotFoundException;
import cf.soisi.rest_socialmedia.exception.UserNotFoundException;
import cf.soisi.rest_socialmedia.exception.UsernameAlreadyExistsException;
import cf.soisi.rest_socialmedia.model.Post;
import cf.soisi.rest_socialmedia.model.User;
import cf.soisi.rest_socialmedia.repository.PostRepository;
import cf.soisi.rest_socialmedia.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
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

    @GetMapping("/users")
    public Collection<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/users")
    public ResponseEntity<User> saveUser(@RequestBody @Valid User unsafeUser) throws UsernameAlreadyExistsException {
        User safeUser = new User(unsafeUser);
        try {
            User savedUser = userRepository.save(safeUser);

            URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .replacePath("/users/{id}")
                    .build(savedUser.getId());

            return ResponseEntity
                    .created(uri)
                    .body(savedUser);

        } catch (DataIntegrityViolationException e) {
            throw new UsernameAlreadyExistsException(safeUser.getName());
        }
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Integer id) throws UserNotFoundException {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable Integer id) throws UserNotFoundException {
        userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        postRepository.removeAllByUserId(id);
        userRepository.deleteById(id);
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> getPostsByUserId(@PathVariable Integer id) throws UserNotFoundException {
        userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return postRepository.findPostsByUser_Id(id);
    }

    @PostMapping("/users/{userId}/posts")
    public ResponseEntity<Post> createPost(@PathVariable Integer userId, @RequestBody @Valid Post unsafePost) throws UserNotFoundException {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Post safePost = new Post(unsafePost.getMessage(), user);

        Post savedPost = postRepository.save(safePost);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .replacePath("/posts/{id}")
                .build(savedPost.getId());
        return ResponseEntity.created(uri).body(savedPost);
    }

    @GetMapping("/posts")
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @GetMapping("/posts/{id}")
    public Post getPostById(@PathVariable Integer id) throws PostNotFoundException {
        return postRepository
                .findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
    }
}
