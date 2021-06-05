package cf.soisi.rest_socialmedia.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(Integer id) {
        super("User not found with id=" + id);
    }
}
