package com.microgram_tashbaltaev_subanbek_group_11.dto;

import com.microgram_tashbaltaev_subanbek_group_11.entity.Comment;
import lombok.*;

@Data
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class CommentAddDTO {

    public static CommentAddDTO from(Comment comment) {
        return builder()
                .publicationId(comment.getPublication().getId())
                .commentText(comment.getCommentText())
                .build();
    }

    private Long publicationId;
    private String commentText;
}
