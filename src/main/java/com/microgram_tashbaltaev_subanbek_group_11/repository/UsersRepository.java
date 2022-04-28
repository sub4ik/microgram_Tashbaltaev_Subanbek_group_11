package com.microgram_tashbaltaev_subanbek_group_11.repository;

import com.microgram_tashbaltaev_subanbek_group_11.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends CrudRepository<User, Long> {

    Optional<User> findByAccountName(String accountName);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByAccountName(String accountName);

    List<User> findByNameContainsIgnoringCase(String name);

}
