package com.aru.services;

import com.aru.models.PlanType;
import com.aru.models.Subscription;
import com.aru.models.User;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionServiceImpl implements SubscriptionService{
    @Override
    public Subscription createSubscription(User user) {
        return null;
    }

    @Override
    public Subscription getUserSubscription(Long userId) throws Exception {
        return null;
    }

    @Override
    public Subscription upgradeSubscription(Long userId, PlanType planType) {
        return null;
    }

    @Override
    public boolean isValid(Subscription subscription) {
        return false;
    }
}
