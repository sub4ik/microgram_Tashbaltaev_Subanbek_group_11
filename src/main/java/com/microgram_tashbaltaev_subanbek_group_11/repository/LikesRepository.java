package com.microgram_tashbaltaev_subanbek_group_11.repository;

import com.microgram_tashbaltaev_subanbek_group_11.entity.Like;
import com.microgram_tashbaltaev_subanbek_group_11.entity.Publication;
import com.microgram_tashbaltaev_subanbek_group_11.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends CrudRepository<Like, Long> {

    List<Like> findAllByPublicationOrderByDateAddedDesc(Publication publication);

    Optional<Like> findByLikeOwnerAndPublication(User likeOwner, Publication publication);

}
