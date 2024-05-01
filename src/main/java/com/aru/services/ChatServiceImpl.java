package com.aru.services;

import com.aru.models.Chat;
import com.aru.repositories.ChatRepository;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService{

    private ChatRepository chatRepository;

    @Override
    public Chat createChat(Chat chat) {

        return chatRepository.save(chat);
    }
}
