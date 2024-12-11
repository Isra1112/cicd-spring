package com.example.cicd.service;

import com.example.cicd.domain.ChatMessage;
import com.example.cicd.domain.ChatRoom;
import com.example.cicd.domain.User;
import com.example.cicd.repository.ChatMessageRepository;
import com.example.cicd.repository.ChatRoomRepository;
import com.example.cicd.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final CacheChatMessageService cacheChatMessageService;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    public ChatMessage saveChatMessage(ChatMessage chatMessage) {
        return chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> getCacheAllChatMessages() {
        List<ChatMessage> cacheAllChatMessages = cacheChatMessageService.getAllCacheChatMessages();
        if (cacheAllChatMessages == null) {
            cacheAllChatMessages = chatMessageRepository.findAll();
            cacheChatMessageService.storeAllChatMessage(cacheAllChatMessages);
        }
        return cacheAllChatMessages;

    }

    public ChatRoom liveChatInit(User request) {
        if (request.getRole().equals("admin")){
            System.out.println("User cannot admin");
            return null;
        }

        Optional<User> user = userRepository.findById(request.getId());

        if (user.isEmpty()) {
            System.out.println("User not found");
            return null;
        }

        LocalDate today = LocalDate.now();
        List<ChatRoom> roomList = chatRoomRepository.findByInitiatorAndEndDateAfter(user.get(), LocalDate.now());
        if (!roomList.isEmpty()) {
            System.out.println("There is room active");
            return null;
        }

        Long id = 2L;
        Optional<User> admin = userRepository.findById(id);

        List<User> participants = new ArrayList<>();
        participants.add(admin.get());
        participants.add(user.get());

        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setTitle("Live Chat");
        chatRoom.setInitiator(user.get());
        chatRoom.setStartDate(today);
        chatRoom.setEndDate(today.plusDays(1));
        chatRoom.setIsDeleted(false);
        chatRoom.setParticipants(participants);
        chatRoomRepository.save(chatRoom);

        return chatRoomRepository.save(chatRoom);
    }

    public void markReadChat(List<Long> chatMessageIds) {

    }
}


