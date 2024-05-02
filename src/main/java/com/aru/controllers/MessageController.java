package com.aru.controllers;

import com.aru.models.Chat;
import com.aru.models.Message;
import com.aru.models.User;
import com.aru.requests.CreateMessageRequest;
import com.aru.services.MessageService;
import com.aru.services.ProjectService;
import com.aru.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @PostMapping("/send")
    public ResponseEntity <Message> sendMessage
            (@RequestBody CreateMessageRequest messageRequest)
            throws Exception {

        User user = userService.findUserById(messageRequest.getSenderId());

        if (user == null) {
            throw new Exception("User not found with " + messageRequest.getSenderId());
        }

        Chat chats = projectService.getProjectById(messageRequest.getProjectId()).getChat();

        if (chats == null) {
            throw new Exception("Chat is not found!");
        }

        Message sentMessage = messageService.sendMessage
                (
                messageRequest.getSenderId(),
                messageRequest.getProjectId(),
                messageRequest.getContent()
                );

        return ResponseEntity.ok(sentMessage);
    }

    @GetMapping("/chat/{projectId}")
    public ResponseEntity <List<Message>> getMessagesByChatId
            (@PathVariable Long projectId) throws Exception {

        List <Message> messages = messageService.getMessagesByProjectId(projectId);
        return ResponseEntity.ok(messages);
    }


}
