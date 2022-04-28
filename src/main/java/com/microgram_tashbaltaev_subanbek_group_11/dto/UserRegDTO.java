package com.microgram_tashbaltaev_subanbek_group_11.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegDTO {
    private String name;
    private String accountName;
    private String email;
    private String password;
}
