package com.microgram_tashbaltaev_subanbek_group_11.dto;

import com.microgram_tashbaltaev_subanbek_group_11.entity.Comment;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class CommentDTO {

    public static CommentDTO from(Comment comment) {
        return builder()
                .id(comment.getId())
                .author(comment.getAuthor().getAccountName())
                .publicationId(comment.getPublication().getId())
                .commentText(comment.getCommentText())
                .dateAdded(comment.getDateAdded())
                .build();
    }

    private Long id;
    private String author;
    private Long publicationId;
    private String commentText;
    private LocalDateTime dateAdded;
}
