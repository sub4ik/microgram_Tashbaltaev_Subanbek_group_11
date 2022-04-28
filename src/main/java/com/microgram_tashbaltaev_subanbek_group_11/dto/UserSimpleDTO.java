package com.microgram_tashbaltaev_subanbek_group_11.dto;

import com.microgram_tashbaltaev_subanbek_group_11.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSimpleDTO {

    public static UserSimpleDTO from(User user) {
        return UserSimpleDTO.builder()
                .accountName(user.getAccountName())
                .profileLink("/users/" + user.getId())
                .build();
    }

    private String accountName;
    private String profileLink;
}
