package com.microgram_tashbaltaev_subanbek_group_11.service;

import com.microgram_tashbaltaev_subanbek_group_11.entity.Subscribe;
import com.microgram_tashbaltaev_subanbek_group_11.entity.User;
import com.microgram_tashbaltaev_subanbek_group_11.exception.ResourceAlreadyExistsException;
import com.microgram_tashbaltaev_subanbek_group_11.exception.ResourceNotFoundException;
import com.microgram_tashbaltaev_subanbek_group_11.repository.SubscribeRepository;
import com.microgram_tashbaltaev_subanbek_group_11.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SubscribeService {
    private final SubscribeRepository subscribeRepository;
    private final UsersRepository usersRepository;

    public ResponseEntity<Void> subscribe(User user, Long subscribeId) {
        try {
            User subscription = usersRepository.findById(subscribeId).orElseThrow(ResourceNotFoundException::new);
            if (subscribeRepository.existsBySubscriberAndSubscription(user, subscription)) {
                throw new ResourceAlreadyExistsException();
            }
            subscribeRepository.save(Subscribe.builder()
                    .subscriber(user)
                    .subscription(subscription)
                    .dateAdded(LocalDateTime.now())
                    .build()
            );
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ResourceAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
