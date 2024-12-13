package com.example.cicd.controller;

import com.example.cicd.domain.ChatMessage;
import com.example.cicd.domain.ChatRoom;
import com.example.cicd.domain.User;
import com.example.cicd.domain.dto.ChatMessageDTO;
import com.example.cicd.service.ChatMessageService;
import com.example.cicd.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class NotificationController {
    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;
    private final SimpMessagingTemplate messagingTemplate;


    @MessageMapping("/chat/init/livechat")
    @SendTo("/topic/livechat/notification")
    public ChatRoom initLiveChat(User request) {
        System.out.println("initChatCompany");
        return chatMessageService.liveChatInit(request);
    }

    @MessageMapping("/chat/roomchat/send/{chatRoomId}")
//    @SendTo("/chat/roomchat/{chatRoomId}")
    public void liveChatRoom(@DestinationVariable String chatRoomId,@Payload ChatMessage request) {
        System.out.println("liveChatRoom");
        System.out.println("chatRoomId : "+chatRoomId);

        ChatMessage response = chatMessageService.saveChatMessage(request);
        messagingTemplate.convertAndSend("/topic/chat/roomchat/" + chatRoomId, response);
    }

    @MessageMapping("/chat/read/{chatMessageId}")
    public void markReadChatMessage(@DestinationVariable String chatMessageId, User request) {
        System.out.println("markReadChatMessage");
    }

    @MessageMapping("/chat/read")
    public void bulkMarkReadChatMessage(List<Long> chatMessageIds) {
        System.out.println("markReadChatMessage bulk");
        chatMessageService.markReadChat(chatMessageIds);
    }

    @MessageMapping("/chat/roomchat/active") // get room chat active by user
    public void getChatActiveByUser(Long userId) {
        System.out.println("get room chat active by user");
        chatRoomService.getAllChatRoomsActiveByParticipant(userId);
    }

//    @MessageMapping("/chat/send")
//    @SendTo("/topic/chat/{chatRoomId}")
//    public ChatMessage sendMessage(ChatMessageRequest request, Principal principal) {
//        return chatService.sendMessage(request, principal.getName());
//    }

//    @MessageMapping("/chat/init/ordergroup")
//    public void initChatCompany(ChatInitOrderGroupRequest request, Principal principal) {
//        chatService.initChatAsCompany(request, principal.getName());
//    }

//    @MessageMapping("/chat/send")
//    @SendTo("/topic/chat/{chatRoomId}")
//    public ChatMessage sendMessage(ChatMessageRequest request, Principal principal) {
//        return chatService.sendMessage(request, principal.getName());
//    }


//    @MessageMapping("/send")
//    @SendTo("/topic/notifications")
//    public String sendNotification(String message) {
//        System.out.println("Message : "+message);
//        return message;
//    }
//
//    @MessageMapping("/notify")
//    @SendTo("/topic/notifications")
//    public Notification notify(Notification notification) {
//        return notification;
//    }
//
//    // Customer sends a message
//    @MessageMapping("/customer/send/{customerId}") // stompClient.send("/app/customer/send/id", {}, JSON.stringify({content: "send customer"}));
//    @SendTo("/topic/admin/{customerId}") // return value of this function will be send to this topic
//    public ChatMessage forwardToAdmin(@DestinationVariable String customerId, ChatMessage message) {
//        return message; // Forward the customer's message to the admin
//    }
//
//    // Admin replies
//    @MessageMapping("/admin/reply/{customerId}") // stompClient.send("/app/admin/reply/id", {}, JSON.stringify({content: "send admin"}));
//    @SendTo("/topic/customer/{customerId}") // return value of this function will be send to this topic
//    public ChatMessage replyToCustomer(@DestinationVariable String customerId, ChatMessage message) {
//        System.out.println("Replying to customerId: " + customerId);
//        System.out.println("Replying message: " + message.getContent());
//        return message; // Forward admin's reply to the customer
//    }
//
//    @MessageMapping("/ordergroup/room/{id}")
//    @SendTo("/topic/ordergroup/room/{id}") // return value of this function will be send to this topic
//    public ChatMessage replyToRoom(@DestinationVariable String id, ChatMessage message) {
//        System.out.println("Replying to room id : " + id);
//        System.out.println("Replying message: " + message.getContent());
////        message.setRoom(id);
//        chatMessageService.saveChatMessage(message);
//        return message;
//    }

//    @MessageMapping("/init/chat/admin")

    @GetMapping("/roomchat")
    public ResponseEntity<List<ChatRoom>> roomChat() {
        return ResponseEntity.ok(chatRoomService.getAllChatRoomsActiveByParticipant(2L));
    }
}