package com.microgram_tashbaltaev_subanbek_group_11.service;

import com.microgram_tashbaltaev_subanbek_group_11.dto.*;
import com.microgram_tashbaltaev_subanbek_group_11.entity.Publication;
import com.microgram_tashbaltaev_subanbek_group_11.entity.User;
import com.microgram_tashbaltaev_subanbek_group_11.repository.PublicationsRepository;
import com.microgram_tashbaltaev_subanbek_group_11.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final PublicationService publicationService;
    private final UsersRepository usersRepository;
    private final PublicationsRepository publicationsRepository;
    private final PasswordEncoder encoder;

    public ResponseEntity<String> register(UserRegDTO userRegDTO) {
        if (usersRepository.existsByAccountName(userRegDTO.getAccountName()) ||
                usersRepository.existsByEmail(userRegDTO.getEmail())) {
            return new ResponseEntity<>("Such a user already exists.", HttpStatus.CONFLICT);
        }
        usersRepository.save(User.builder()
                .name(userRegDTO.getName())
                .accountName(userRegDTO.getAccountName())
                .email(userRegDTO.getEmail())
                .password(encoder.encode(userRegDTO.getPassword()))
                .countPublications(0)
                .countSubscribers(0)
                .countSubscribes(0)
                .build()
        );
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    public ResponseEntity<List<UserSimpleDTO>> search(String text, String type) {
        List<UserSimpleDTO> list = new ArrayList<>();
        Optional<User> optUser = Optional.empty();
        if (type.equals("email")) {
            optUser = usersRepository.findByEmail(text);
        } else if (type.equals("account")) {
            optUser = usersRepository.findByAccountName(text);
        } else if (type.equals("name")) {
            list.addAll(usersRepository.findByNameContainsIgnoringCase(text).stream()
                    .map(UserSimpleDTO::from)
                    .collect(Collectors.toList())
            );
        } else {
            return ResponseEntity.badRequest().build();
        }
        if (optUser.isPresent()) {
            list.add(UserSimpleDTO.from(optUser.get()));
        }
        if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }

    public Optional<User> userSearch(String text){
        return usersRepository.findByEmail(text);
    }

    public ResponseEntity<UserPublicProfileDTO> getUser(Long userId) {
        Optional<User> optUser = usersRepository.findById(userId);
        if (optUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Publication> publications = publicationsRepository.findAllByAuthor(optUser.get(), Pageable.unpaged()).stream().collect(Collectors.toList());
        List<PublicationDTO> publicationDTOList = publications.stream()
                .map(publicationService::createPublicationDto)
                .collect(Collectors.toList());
        var userDTO = UserPublicProfileDTO.from(optUser.get(), publicationDTOList);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Not found"));
    }


}


