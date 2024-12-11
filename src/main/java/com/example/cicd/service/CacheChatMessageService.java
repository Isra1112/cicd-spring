package com.example.cicd.service;

import com.example.cicd.domain.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CacheChatMessageService {
    private final RedisTemplate<String,Object> redisTemplate;

    private static final String CHAT_MESSAGE_PREFIX = "CM:";

    public void storeAllChatMessage(List<ChatMessage> chatMessages) {
        for (ChatMessage chatMessage : chatMessages) {
            String key = CHAT_MESSAGE_PREFIX + chatMessage.getId();
            redisTemplate.opsForValue().set(key , chatMessage, 15, TimeUnit.MINUTES);
        }
    }

    public List<ChatMessage> getAllCacheChatMessages() {
        Set<String> keys = redisTemplate.keys(CHAT_MESSAGE_PREFIX + "*");

        if (keys == null || keys.isEmpty()) {
            return null;
        }

        return keys.stream()
                .map(key -> (ChatMessage) redisTemplate.opsForValue().get(key))
                .collect(Collectors.toList());
    }
}
