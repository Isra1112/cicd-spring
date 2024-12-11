package com.example.cicd.service;

import com.example.cicd.domain.ChatRoom;
import com.example.cicd.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    public List<ChatRoom> getAll() {
        return chatRoomRepository.findAllWithChatMessages();
    }

    public List<ChatRoom> getAllChatRoomsActiveByParticipant(Long participantId) {
        return chatRoomRepository.findByParticipantId(participantId);
    }

    public List<ChatRoom> findAllTodelete() {
        LocalDate today = LocalDate.now();
        return chatRoomRepository.findAllTodelete(today);
    }

    public void deleteRoomsChat() {
        try {
            List<ChatRoom> roomsToDelete = findAllTodelete();
            roomsToDelete.forEach(roomChat -> {
                try {
                    chatRoomRepository.deleteById(roomChat.getId());
                } catch (Exception e) {
                    log.error("Error deleting RoomChat with ID: {}", roomChat.getId(), e);
                }
            });
        } catch (Exception e) {
            log.error("Error in deleteRoomsChat method.", e);
        }
    }


}
