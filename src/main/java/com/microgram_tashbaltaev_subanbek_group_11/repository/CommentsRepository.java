package com.microgram_tashbaltaev_subanbek_group_11.repository;

import com.microgram_tashbaltaev_subanbek_group_11.entity.Comment;
import com.microgram_tashbaltaev_subanbek_group_11.entity.Publication;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentsRepository extends CrudRepository<Comment, Long> {

    List<Comment> findAllByPublicationOrderByDateAddedDesc(Publication publication);

}
