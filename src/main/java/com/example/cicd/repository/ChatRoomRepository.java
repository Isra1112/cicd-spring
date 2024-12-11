package com.example.cicd.repository;

import com.example.cicd.domain.ChatRoom;
import com.example.cicd.domain.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query(value = "SELECT * FROM room_chat r WHERE DATE_ADD(r.end_date, INTERVAL 7 DAY) = :today", nativeQuery = true)
    List<ChatRoom> findAllTodelete(@Param("today") LocalDate today);

    @Query("SELECT r FROM ChatRoom r LEFT JOIN FETCH r.chatMessages")
    List<ChatRoom> findAllWithChatMessages();

    List<ChatRoom> findByInitiatorAndEndDateAfter(User initiator, LocalDate endDate);

    @Query("SELECT c FROM ChatRoom c JOIN c.participants p WHERE p.id = :userId")
    List<ChatRoom> findByParticipantId(@Param("userId") Long userId);

}
