package com.microgram_tashbaltaev_subanbek_group_11.controller;

import com.microgram_tashbaltaev_subanbek_group_11.dto.UserSimpleDTO;
import com.microgram_tashbaltaev_subanbek_group_11.dto.UserPublicProfileDTO;
import com.microgram_tashbaltaev_subanbek_group_11.dto.UserRegDTO;
import com.microgram_tashbaltaev_subanbek_group_11.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @CrossOrigin
    @PostMapping("/registration")
    public ResponseEntity<String> register(@RequestBody UserRegDTO userRegDTO) {
        return userService.register(userRegDTO);
    }

    @GetMapping("/search")
    ResponseEntity<List<UserSimpleDTO>> search(@RequestParam String text, String type) {
        return userService.search(text, type);
    }

    //Просмотр лент других пользователей. Мы можем просмотреть публикации, которые сделал
    //этот пользователь.
    @CrossOrigin
    @GetMapping("/{userId}")
    ResponseEntity<UserPublicProfileDTO> getUser(@PathVariable Long userId){
        return userService.getUser(userId);
    }

}
