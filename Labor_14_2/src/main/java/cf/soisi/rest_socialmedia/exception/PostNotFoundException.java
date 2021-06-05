package cf.soisi.rest_socialmedia.exception;

public class PostNotFoundException extends Exception{
    public PostNotFoundException(Integer id) {
        super("Post not found with id=" + id);
    }
}
