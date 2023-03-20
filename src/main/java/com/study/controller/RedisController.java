package com.study.controller;

import com.study.utils.RedisUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @author hbc
 * @version 1.0
 * @description 测试redis
 * @date 2023/2/23 17:11
 */
@Slf4j
@RestController
@RequestMapping("/redis")
public class RedisController {

    private final RedisTemplate redisTemplate;

    private RedisUtils redisUtils;


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
        log.info("save方法测试，{},{}",obj.key,obj.value);
        redisTemplate.opsForValue().set(obj.getKey(),obj.getValue());
    }
}
