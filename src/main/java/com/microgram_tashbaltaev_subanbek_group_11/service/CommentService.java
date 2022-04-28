package com.microgram_tashbaltaev_subanbek_group_11.service;

import com.microgram_tashbaltaev_subanbek_group_11.dto.CommentAddDTO;
import com.microgram_tashbaltaev_subanbek_group_11.entity.Comment;
import com.microgram_tashbaltaev_subanbek_group_11.entity.Publication;
import com.microgram_tashbaltaev_subanbek_group_11.entity.User;
import com.microgram_tashbaltaev_subanbek_group_11.exception.ResourceNotFoundException;
import com.microgram_tashbaltaev_subanbek_group_11.repository.CommentsRepository;
import com.microgram_tashbaltaev_subanbek_group_11.repository.PublicationsRepository;
import com.microgram_tashbaltaev_subanbek_group_11.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentsRepository commentsRepository;
    private final UsersRepository usersRepository;
    private final PublicationsRepository publicationsRepository;

    public ResponseEntity<Void> addComment(User user, CommentAddDTO commentAddDTO /* User user */) {
        Optional<Publication> optPublication = publicationsRepository.findById(commentAddDTO.getPublicationId());
        if (optPublication.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        commentsRepository.save(Comment.builder()
                .commentText(commentAddDTO.getCommentText())
                .dateAdded(LocalDateTime.now())
                .author(user)
                .publication(optPublication.get())
                .build()
        );
        user.setCountPublications(user.getCountPublications() + 1);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> deleteComment(User user, Long commentId) {
        try {
            Comment comment = commentsRepository.findById(commentId).orElseThrow(ResourceNotFoundException::new);
            Publication publication = comment.getPublication();
            if (!publication.getAuthor().equals(user)) throw new AccessDeniedException("access denied");
            commentsRepository.delete(comment);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
