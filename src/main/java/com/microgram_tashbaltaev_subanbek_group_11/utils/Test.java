package com.microgram_tashbaltaev_subanbek_group_11.utils;

import com.microgram_tashbaltaev_subanbek_group_11.repository.*;
import com.microgram_tashbaltaev_subanbek_group_11.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Test {

    @Bean
    CommandLineRunner testing(UsersRepository userR,
                              PublicationsRepository pubR,
                              CommentsRepository commR,
                              LikesRepository likeR,
                              SubscribeRepository subR,
                              UserService userService,
                              PublicationService publicationService,
                              CommentService commentService,
                              LikeService likeService,
                              SubscribeService subscribeService) {
        return (args) -> {

        };
    }
}
