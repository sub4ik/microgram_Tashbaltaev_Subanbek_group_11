package com.microgram_tashbaltaev_subanbek_group_11.service;

import com.microgram_tashbaltaev_subanbek_group_11.entity.Image;
import com.microgram_tashbaltaev_subanbek_group_11.exception.ResourceNotFoundException;
import com.microgram_tashbaltaev_subanbek_group_11.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public ResponseEntity<Resource> getById(Long imageId) {
        try {
            Image movieImage = imageRepository.findById(imageId)
                    .orElseThrow(ResourceNotFoundException::new);
            var resource = new ByteArrayResource(movieImage.getImageData());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                    .body(resource);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
