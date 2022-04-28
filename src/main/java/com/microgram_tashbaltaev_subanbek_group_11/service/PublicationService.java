package com.microgram_tashbaltaev_subanbek_group_11.service;

import com.microgram_tashbaltaev_subanbek_group_11.dto.CommentDTO;
import com.microgram_tashbaltaev_subanbek_group_11.dto.LikeDTO;
import com.microgram_tashbaltaev_subanbek_group_11.dto.PublicationDTO;
import com.microgram_tashbaltaev_subanbek_group_11.entity.Image;
import com.microgram_tashbaltaev_subanbek_group_11.entity.Publication;
import com.microgram_tashbaltaev_subanbek_group_11.entity.User;
import com.microgram_tashbaltaev_subanbek_group_11.exception.ResourceNotFoundException;
import com.microgram_tashbaltaev_subanbek_group_11.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

import static java.util.stream.Collectors.toList;


@Service
@RequiredArgsConstructor
public class PublicationService {
    private final PublicationsRepository publicationsRepository;
    private final UsersRepository usersRepository;
    private final LikesRepository likesRepository;
    private final CommentsRepository commentRepository;
    private final ImageRepository imageRepository;

    public Page<PublicationDTO> getFeed(User user, Pageable pageable) {
        var feed = publicationsRepository.getFeed(user).stream().map(this::createPublicationDto).collect(toList());
        Page<PublicationDTO> page = new PageImpl<>(feed, pageable, feed.size());
        return page;
    }

    public Page<PublicationDTO> getUserPublications(Long userId, Pageable pageable) {
        User user = usersRepository.findById(userId).orElseThrow(ResourceNotFoundException::new);
        return publicationsRepository.findAllByAuthor(user, pageable).map(this::createPublicationDto);
    }

    public ResponseEntity<Void> addPublication(User user, PublicationDTO publicationDTO, MultipartFile file) {
        byte[] data = new byte[0];
        try {
            data = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image image = Image.builder().imageData(data).build();
        imageRepository.save(image);

        publicationsRepository.save(Publication.builder()
                .description(publicationDTO.getDescription())
                .image(image)
                .author(user)
                .dateAdded(publicationDTO.getDateAdded())
                .build()
        );
        user.setCountPublications(user.getCountPublications() + 1);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> deletePublication(User user, Long publicationId) {
        try {
            Publication publication = publicationsRepository.findById(publicationId).orElseThrow(ResourceNotFoundException::new);
            if (!publication.getAuthor().equals(user)) {
                throw new AccessDeniedException("AccessDenied");
            }
            var likes = likesRepository.findAllByPublicationOrderByDateAddedDesc(publication);
            likesRepository.deleteAll(likes);
            var comments = commentRepository.findAllByPublicationOrderByDateAddedDesc(publication);
            commentRepository.deleteAll(comments);
            publicationsRepository.deleteById(publicationId);
            user.setCountPublications(user.getCountPublications() - 1);
            return ResponseEntity.noContent().build();
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    public PublicationDTO createPublicationDto(Publication p) {
        var likes = likesRepository.findAllByPublicationOrderByDateAddedDesc(p);
        var comments = commentRepository.findAllByPublicationOrderByDateAddedDesc(p);
        return PublicationDTO.from(p, likes, comments);
    }

}
