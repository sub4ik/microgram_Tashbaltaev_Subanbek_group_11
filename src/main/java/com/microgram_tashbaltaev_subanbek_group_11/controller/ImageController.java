package com.microgram_tashbaltaev_subanbek_group_11.controller;
import com.microgram_tashbaltaev_subanbek_group_11.service.ImageService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<Resource> serveFile(@PathVariable Long imageId) {
           return imageService.getById(imageId);
    }
}
