package com.microgram_tashbaltaev_subanbek_group_11.repository;

import com.microgram_tashbaltaev_subanbek_group_11.entity.Publication;
import com.microgram_tashbaltaev_subanbek_group_11.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PublicationsRepository extends PagingAndSortingRepository<Publication, Long> {

    Page<Publication> findAllByAuthor(User author, Pageable pageable);

    @Query("SELECT p FROM Publication p " +
            "INNER JOIN Subscribe s ON p.author = s.subscription " +
            "WHERE s.subscriber = :user " +
            "ORDER BY p.dateAdded DESC")
    List<Publication> getFeed(User user);


}
