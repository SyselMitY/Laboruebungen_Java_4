package cf.soisi.rest_socialmedia.repository;

import cf.soisi.rest_socialmedia.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findPostsByUser_Id(Integer userId);

    @Transactional
    int removeAllByUserId(Integer userId);
}
