package com.aru.controllers;

import com.aru.models.PlanType;
import com.aru.models.Subscription;
import com.aru.models.User;
import com.aru.services.SubscriptionService;
import com.aru.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public ResponseEntity <Subscription> getUserSubscription(
            @RequestHeader ("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        Subscription subscription = subscriptionService.getUserSubscription(user.getId());

        return new ResponseEntity<>(subscription, HttpStatus.OK);
    }

    @PatchMapping ("/upgrade")
    public ResponseEntity <Subscription> upgradeSubscription(
            @RequestHeader ("Authorization") String jwt,
            @RequestParam PlanType planType) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        Subscription subscription = subscriptionService.upgradeSubscription(user.getId(), planType);

        return new ResponseEntity<>(subscription, HttpStatus.OK);
    }





}
