package com.example.cicd.domain.dto;

import com.example.cicd.domain.ChatRoom;
import lombok.Data;

@Data
public class ChatMessageDTO {
    private Long id;
    private String content;


    private ChatRoom chatRoom;

}
