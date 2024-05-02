package com.aru.controllers;

import com.aru.models.Message;
import com.aru.services.MessageService;
import com.aru.services.ProjectService;
import com.aru.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    public ResponseEntity <Message> sendMessage (@RequestBody CreateMessageRequest createMessageRequest) throws Exception {
        return null;
    }
}
