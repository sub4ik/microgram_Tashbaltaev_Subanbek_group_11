package com.microgram_tashbaltaev_subanbek_group_11.dto;

import com.microgram_tashbaltaev_subanbek_group_11.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPublicProfileDTO {

    public static UserPublicProfileDTO from(User user, List<PublicationDTO> publications){
        return UserPublicProfileDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .accountName(user.getAccountName())
                .countPublications(user.getCountPublications())
                .countSubscribers(user.getCountSubscribers())
                .countSubscribes(user.getCountSubscribes())
                .publications(publications)
                .build();
    }

    private Long id;
    private String name;
    private String accountName;
    private Integer countPublications;
    private Integer countSubscribers;
    private Integer countSubscribes;
    private List<PublicationDTO> publications;
}
