package cf.soisi.rest_socialmedia;

import cf.soisi.rest_socialmedia.model.Post;
import cf.soisi.rest_socialmedia.model.User;
import cf.soisi.rest_socialmedia.repository.PostRepository;
import cf.soisi.rest_socialmedia.repository.UserRepository;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataInitializer {
    public DataInitializer(UserRepository userRepository, PostRepository postRepository) {
        List<User> users = new ArrayList<>();
        List<Post> posts = new ArrayList<>();

        users.add(new User("Hugo", LocalDate.of(2002, 11, 24)));
        users.add(new User("Litunde", LocalDate.of(2003, 5, 17)));
        users.add(new User("SkankHunt42", LocalDate.of(2001, 12, 3)));

        posts.add(new Post("Woow omg die neue Seite is ja voll oag, bam oida!", users.get(0)));
        posts.add(new Post("Huhu leute, wilkommen bei dem ersten Post (haha vastehst weil schon einer gepostet " +
                "worden ist ich fang mit 0 an zum zählen wie ein echter Programmierer :D", users.get(1)));
        posts.add(new Post("Mmh ich hab Gadse gestreichelt war weich und schnurrig", users.get(2)));

        posts.add(new Post("Hoi ik ben Hugo, ik spreek Nederlands, maar niet goed.", users.get(0)));
        posts.add(new Post("Hoi Hugo. Wo zijn de Postzegels?", users.get(1)));
        posts.add(new Post("Servus Habschkis, wos am going onnen? Es oagen schlawiner?", users.get(2)));

        posts.add(new Post("Wow now Ick bin sprechen British yes mmh i drinke meinen Tea yes yes, very well", users.get(0)));
        posts.add(new Post("Ik eet de Postzegels, maar ik liek geene postzegels", users.get(1)));
        posts.add(new Post("Bist du depperd in litunde hods grod hin und her, auffe und owe, hi und zruck gschlagltl, dem hund gehts ah nimma guad", users.get(2)));

        userRepository.saveAll(users);
        postRepository.saveAll(posts);

    }
}
