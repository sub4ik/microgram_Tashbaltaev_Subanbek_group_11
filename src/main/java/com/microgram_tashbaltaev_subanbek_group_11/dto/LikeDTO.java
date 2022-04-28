package com.microgram_tashbaltaev_subanbek_group_11.dto;

import com.microgram_tashbaltaev_subanbek_group_11.entity.Like;
import lombok.*;


@Data
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class LikeDTO {

    public static LikeDTO from(Like like) {
        return builder()
                .id(like.getId())
                .likeOwner(like.getLikeOwner().getAccountName())
                .build();
    }

    private Long id;
    private String likeOwner;
}
