package com.aru.services;

import com.aru.models.PlanType;
import com.aru.models.Subscription;
import com.aru.models.User;

public interface SubscriptionService {

    Subscription createSubscription (User user);

    Subscription getUserSubscription(Long userId) throws Exception;

    Subscription upgradeSubscription(Long userId, PlanType planType);

    boolean isValid(Subscription subscription);
}
