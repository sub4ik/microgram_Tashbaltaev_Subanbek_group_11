package com.microgram_tashbaltaev_subanbek_group_11.controller;

import com.microgram_tashbaltaev_subanbek_group_11.entity.User;
import com.microgram_tashbaltaev_subanbek_group_11.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class SubscribeController {
    private final SubscribeService subscribeService;

    @PostMapping
    public ResponseEntity<Void> subscribe(Authentication authentication, @RequestBody Long subscribeId) {
        User user = (User) authentication.getPrincipal();
        return subscribeService.subscribe(user, subscribeId);
        //TODO придумать нормальное назавние переменной
        //subscribeId id юзера на которого подписаешься.
    }
}
