package com.aru.controllers;

import com.aru.models.Chat;
import com.aru.models.Message;
import com.aru.models.User;
import com.aru.requests.CreateMessageRequest;
import com.aru.services.MessageService;
import com.aru.services.ProjectService;
import com.aru.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

        Chat chat = projectService.getChatByProjectId(projectId);

        if (chat == null) {
            // Log error or handle case where chat is null
            System.out.println("Chat object is null for project ID: " + projectId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<Message> messages = messageService.getMessagesByProjectId(projectId);
        return ResponseEntity.ok(messages);
    }
}
