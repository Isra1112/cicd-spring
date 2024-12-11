package com.example.cicd.config;

import com.example.cicd.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Configuration
@RequiredArgsConstructor
public class DeleteRoomChatScheduling {
    private final ChatRoomService chatRoomService;

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteRoomChat() {
        System.out.println("Run Delete Room Chat Scheduling Date : " + new Date());
        chatRoomService.deleteRoomsChat();
    }
}
