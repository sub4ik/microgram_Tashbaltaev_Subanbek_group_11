package com.microgram_tashbaltaev_subanbek_group_11.controller;

import com.microgram_tashbaltaev_subanbek_group_11.dto.PublicationDTO;
import com.microgram_tashbaltaev_subanbek_group_11.entity.User;
import com.microgram_tashbaltaev_subanbek_group_11.service.PublicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/publications")
@RequiredArgsConstructor
public class PublicationController {
    private final PublicationService publicationService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> addPublication(
            Authentication authentication,
            @RequestPart(value = "obj") PublicationDTO publicationDTO,
            @RequestPart("file") MultipartFile file
    ) {
        User user = (User) authentication.getPrincipal();
        return publicationService.addPublication(user, publicationDTO, file);
    }

    @DeleteMapping("{publicationId}")
    public ResponseEntity<Void> deletePublication(Authentication authentication, @PathVariable Long publicationId /*, From Spring Security! User user, */) {
        User user = (User) authentication.getPrincipal();
        return publicationService.deletePublication(user, publicationId);
    }

    @GetMapping("/feed")
    Page<PublicationDTO> getFeed(Authentication authentication, Pageable pageable) {
        User user = (User) authentication.getPrincipal();
        return publicationService.getFeed(user, pageable);
    }

}
