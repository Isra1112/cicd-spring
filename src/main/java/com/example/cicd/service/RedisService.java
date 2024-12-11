package com.example.cicd.service;

import com.example.cicd.config.RedisConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class RedisService {
    private final RedisConfig redisConfig;

    private final RedisTemplate<String, Object> redisTemplate;

    public void checkRedisConnection() {
        boolean isConnected = redisConfig.isRedisConnected();
        if (isConnected) {
            System.out.println("Redis is connected.");
        } else {
            System.out.println("Failed to connect to Redis.");
        }
    }

    public void updateVerification(String id, int errorCode, String errorMsg) {
        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();

        hashOps.put("verification:" + id, "ErrorCode", errorCode);
        hashOps.put("verification:" + id, "ErrorMsg", errorMsg);
    }

    public void createVerification(String id, int errorCode, String errorMsg) {
        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();

        hashOps.put("verification:" + id, "ErrorCode", errorCode);
        hashOps.put("verification:" + id, "ErrorMsg", errorMsg);

        redisTemplate.expire("verification:" + id,10, TimeUnit.MINUTES);
    }

    public Map<String, Object> getVerification(String id) {
        id = "666666666666";
        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();

        // Retrieve the hash values for the specified ID
        Map<String, Object> verificationData = hashOps.entries("verification:" + id);

        // Check if the data exists
        if (verificationData.isEmpty()) {
            System.out.println("No verification found for ID: " + id);
            return null;
        }

        verificationData.put("TTL", redisTemplate.getExpire("verification:" + id));

        return verificationData;
    }
}
