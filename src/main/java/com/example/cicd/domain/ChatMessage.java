package com.example.cicd.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    private String senderId;
    private String receiverId;
    private String content;

    private String timestamp;
    private String isRead;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "chat_room_id", referencedColumnName = "id")
//    @JsonIgnore
    private ChatRoom chatRoom;

    @ManyToOne
    @JsonIgnore
//    @JsonBackReference
    private User sender;
}
