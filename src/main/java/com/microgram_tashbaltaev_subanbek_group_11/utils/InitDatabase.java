package com.microgram_tashbaltaev_subanbek_group_11.utils;

import com.microgram_tashbaltaev_subanbek_group_11.entity.*;
import com.microgram_tashbaltaev_subanbek_group_11.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
@RequiredArgsConstructor
public class InitDatabase {
    private final UsersRepository userR;
    private final PublicationsRepository pubR;
    private final CommentsRepository commR;
    private final LikesRepository likeR;
    private final SubscribeRepository subR;
    private final ImageRepository imgR;
    private final PasswordEncoder encoder;

    @Bean
    CommandLineRunner init(

    ) {
        return (args) -> {
            generateAndInsertData();
        };
    }

    private void generateAndInsertData() {

        subR.deleteAll();
        likeR.deleteAll();
        commR.deleteAll();
        pubR.deleteAll();
        userR.deleteAll();
//-----------------------------------
        Random r = new Random();
        List<User> users = List.of(new User[]{
                User.builder().name("Nolan").accountName("nolan").email("nolan@gmail.com").password(encoder.encode("123")).countPublications(0).countSubscribers(0).countSubscribes(0).build(),
                User.builder().name("Jonatan").accountName("Jonatan").email("Jonatan@mail.com").password(encoder.encode("123")).countPublications(0).countSubscribers(0).countSubscribes(0).build(),
                User.builder().name("Cooper").accountName("Cooper").email("Cooper@mail.com").password(encoder.encode("123")).countPublications(0).countSubscribers(0).countSubscribes(0).build(),
                User.builder().name("godfather").accountName("godfather").email("godfather@mail.com").password(encoder.encode("123")).countPublications(0).countSubscribers(0).countSubscribes(0).build(),
                User.builder().name("Abdur").accountName("abdy").email("abdur@mail.com").password(encoder.encode("123")).countPublications(0).countSubscribers(0).countSubscribes(0).build(),
        });

//-----------------------------------
        List<Subscribe> subs = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            List<User> tmp = new ArrayList<>();
            tmp.addAll(users);
            tmp.remove(i);
            for (int j = r.nextInt(2) + 1; j > 0; j--) {
                var s = tmp.remove(r.nextInt(tmp.size()));
                subs.add(Subscribe.builder()
                        .subscriber(u)
                        .subscription(s)
                        .dateAdded(LocalDateTime.now().plusMinutes(r.nextInt(10)))
                        .build()
                );
                u.setCountSubscribes(u.getCountSubscribes() + 1);
                s.setCountSubscribers(s.getCountSubscribers() + 1);
            }
        }

//-----------------------------------
        byte[] data = new byte[0];
        try {
            Path path = Paths.get("src/main/resources/dota2.jpg");
            data = Files.readAllBytes(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Publication> publications = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            User author = users.get(r.nextInt(users.size()));
            Image noImage = Image.builder().imageData(data).build();
            imgR.save(noImage);
            publications.add(Publication.builder()
                    .image(noImage)
                    .description(Generator.makeDescription())
                    .author(author)
                    .dateAdded(LocalDateTime.now().plusMinutes(i))
                    .build()
            );
            author.setCountPublications(author.getCountPublications() + 1);
        }

//-----------------------------------
        List<Like> likes = new ArrayList<>();
        for (User u : users) {
            List<Publication> tmp = new ArrayList<>();
            tmp.addAll(publications);
            for (int i = r.nextInt(5); i > 0; i--) {
                Publication p = tmp.remove(r.nextInt(tmp.size()));
                likes.add(Like.builder()
                        .likeOwner(u)
                        .publication(p)
                        .dateAdded(LocalDateTime.now().plusMinutes(10 + i))
                        .build());
            }
        }

//-----------------------------------
        List<Comment> comments = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            comments.add(
                    Comment.builder()
                            .author(users.get(r.nextInt(users.size())))
                            .publication(publications.get(r.nextInt(publications.size())))
                            .commentText(Generator.makeDescription())
                            .dateAdded(LocalDateTime.now().plusMinutes(60 + i))
                            .build()
            );
        }

//-----------------------------------
        userR.saveAll(users);
        subR.saveAll(subs);
        pubR.saveAll(publications);
        likeR.saveAll(likes);
        commR.saveAll(comments);

    }

}
