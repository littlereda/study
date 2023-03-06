package com.study.Controller;

import lombok.Data;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @author hbc
 * @version 1.0
 * @description 测试redis
 * @date 2023/2/23 17:11
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    private final RedisTemplate redisTemplate;


    public RedisController(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Data
    static class RedisObj{
        String key;
        String value;
    }

    @PostMapping(value="/save")
    public void save(@RequestBody RedisObj obj ){
        redisTemplate.opsForValue().set(obj.getKey(),obj.getValue());
    }
}
