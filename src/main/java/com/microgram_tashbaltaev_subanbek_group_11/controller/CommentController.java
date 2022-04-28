package com.microgram_tashbaltaev_subanbek_group_11.controller;

import com.microgram_tashbaltaev_subanbek_group_11.dto.CommentAddDTO;
import com.microgram_tashbaltaev_subanbek_group_11.entity.User;
import com.microgram_tashbaltaev_subanbek_group_11.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> addComment(Authentication authentication, @RequestBody CommentAddDTO commentAddDTO) {
        User user = (User) authentication.getPrincipal();
        return commentService.addComment(user, commentAddDTO);
    }

    @DeleteMapping("{commentId}")
    public ResponseEntity<Void> deleteComment(Authentication authentication, @PathVariable Long commentId) {
        User user = (User) authentication.getPrincipal();
        return commentService.deleteComment(user, commentId);
    }
}
