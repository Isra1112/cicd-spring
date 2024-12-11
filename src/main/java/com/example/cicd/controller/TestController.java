package com.example.cicd.controller;


import com.example.cicd.domain.ChatMessage;
import com.example.cicd.service.ChatMessageService;
import com.example.cicd.service.NotificationService;
import com.example.cicd.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final NotificationService notificationService;
    private final RedisService redisService;
    private final ChatMessageService chatMessageService;

    @GetMapping("/test")
    public String test() {
        return "Hello World";
    }

    @GetMapping("/test2")
    public String test2() {
        notificationService.sendNotification("sss");
        return "Hello World";
    }

    @GetMapping("/redis")
    public String redis() {
        redisService.checkRedisConnection();
        return "Check Redis Connection";
    }

    @GetMapping("/redis/add")
    public String redisAdd() {
        redisService.createVerification("666666666666",0,"Success");
        return "Add Verification";
    }

    @GetMapping("/redis/get")
    public Object redisGet() {
        return redisService.getVerification("");
    }

    @GetMapping("/redis/edit")
    public Object redisEdit() {
        redisService.updateVerification("666666666666",0,"Success edit");
        return "Edit Verification";

    }

    @GetMapping("/redis/test")
    public ResponseEntity<List<ChatMessage>> testRedis() {
        return ResponseEntity.ok(chatMessageService.getCacheAllChatMessages());
    }
}