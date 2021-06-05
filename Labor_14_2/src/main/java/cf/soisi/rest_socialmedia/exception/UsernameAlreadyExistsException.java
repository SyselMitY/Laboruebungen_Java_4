package cf.soisi.rest_socialmedia.exception;

public class UsernameAlreadyExistsException extends Exception {
    public UsernameAlreadyExistsException(String username) {
        super("The username \"" + username + "\" already exists");
    }
}
