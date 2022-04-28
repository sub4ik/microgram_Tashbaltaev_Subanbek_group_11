package com.microgram_tashbaltaev_subanbek_group_11.repository;

import com.microgram_tashbaltaev_subanbek_group_11.entity.Subscribe;
import com.microgram_tashbaltaev_subanbek_group_11.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface SubscribeRepository extends CrudRepository<Subscribe, Long> {
    boolean existsBySubscriberAndSubscription(User subscriber, User subscription);
}
