package com.aru.repositories;

import com.aru.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository <Subscription,Long> {

    Subscription findByUserId (Long userId);
}
