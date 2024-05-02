package com.aru.controllers;

import com.aru.models.Subscription;
import com.aru.services.SubscriptionService;
import com.aru.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserService userService;

    public ResponseEntity <Subscription> getUserSubscription(@RequestHeader ("Authorization") String jwt) {
        return new ResponseEntity<>(null);
    }



}
