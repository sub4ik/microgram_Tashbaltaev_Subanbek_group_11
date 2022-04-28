package com.microgram_tashbaltaev_subanbek_group_11.service;

import com.microgram_tashbaltaev_subanbek_group_11.entity.Like;
import com.microgram_tashbaltaev_subanbek_group_11.entity.Publication;
import com.microgram_tashbaltaev_subanbek_group_11.entity.User;
import com.microgram_tashbaltaev_subanbek_group_11.exception.ResourceAlreadyExistsException;
import com.microgram_tashbaltaev_subanbek_group_11.exception.ResourceNotFoundException;
import com.microgram_tashbaltaev_subanbek_group_11.repository.LikesRepository;
import com.microgram_tashbaltaev_subanbek_group_11.repository.PublicationsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikesRepository likesRepository;
    private final PublicationsRepository publicationsRepository;

    public ResponseEntity<Void> addLike(User user, Long publicationId) {
        try {
            Publication publication = publicationsRepository.findById(publicationId).orElseThrow(ResourceNotFoundException::new);
            if (likesRepository.findByLikeOwnerAndPublication(user, publication).isPresent()) {
                throw new ResourceAlreadyExistsException();
            }
            likesRepository.save(Like.builder()
                    .likeOwner(user)
                    .publication(publication)
                    .dateAdded(LocalDateTime.now())
                    .build()
            );
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ResourceAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }


    }
}
