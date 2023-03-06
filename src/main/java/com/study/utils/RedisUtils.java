package com.study.Utils;


import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * spring redis 工具类
 *
 * @author hbc
 **/
@SuppressWarnings(value = { "unchecked", "rawtypes" })
@Component
public class RedisUtils {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     */
    public <T> void setCacheObject(final String key, final T value)
    {
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(value));
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     * @param timeout 时间
     * @param timeUnit 时间颗粒度
     */
    public <T> void setCacheObject(final String key, final T value, final Long timeout, final TimeUnit timeUnit)
    {
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(value), timeout, timeUnit);
    }

    /**
     * 设置有效时间
     *
     * @param key Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout)
    {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key Redis键
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit)
    {
        return stringRedisTemplate.expire(key, timeout, unit);
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(final String key, Class<T> cls)
    {
        ValueOperations<String, String> operation = stringRedisTemplate.opsForValue();
        return JSON.parseObject(operation.get(key),cls);
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheSlashObject(final String key, Class<T> cls)
    {
        ValueOperations<String, String> operation = redisTemplate.opsForValue();
        return JSON.parseObject(operation.get(key),cls);
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> List<T> getCacheArray(final String key, Class<T> cls)
    {
        ValueOperations<String, String> operation = stringRedisTemplate.opsForValue();
        return JSON.parseArray(operation.get(key),cls);
    }

    /**
     * 删除单个对象
     *
     * @param key
     */
    public boolean deleteObject(final String key)
    {
        return stringRedisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     * @return
     */
    public long deleteObject(final Collection collection)
    {
        return stringRedisTemplate.delete(collection);
    }

    /**
     * 缓存Set
     *
     * @param key 缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> BoundSetOperations<String, String> setCacheSet(final String key, final Set<T> dataSet)
    {
        BoundSetOperations<String, String> setOperation = stringRedisTemplate.boundSetOps(key);
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext())
        {
            setOperation.add(JSON.toJSONString(it.next()));
        }
        return setOperation;
    }

    /**
     * 获得缓存的set
     *
     * @param key
     * @return
     */
    public <T> Set<T> getCacheSet(final String key, Class<T> cls)
    {
        Set<String> set = stringRedisTemplate.opsForSet().members(key);
        if(set != null){
            return set.stream().map(o-> JSON.parseObject(o,cls)).collect(Collectors.toSet());
        }
        return null;
    }

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap)
    {
        if (dataMap != null) {
            Map<String, String> map = new HashMap<>();
            for (Map.Entry<String, T> o : dataMap.entrySet()) {
                map.put(o.getKey(), JSON.toJSONString(o.getValue()));
            }
            stringRedisTemplate.opsForHash().putAll(key, map);
        }
    }

    /**
     * 缓存Map
     *
     * @param key
     * @param value
     */
    public <T> void setCacheMapFor(final String key, final String hKey, T value)
    {
        Map<Object, Object> map = stringRedisTemplate.opsForHash().entries(key);
        map.put(hKey, JSON.toJSONString(value));
        stringRedisTemplate.opsForHash().putAll(key, map);
    }


    /**
     * 获得缓存的Map
     *
     * @param key
     * @return
     */
    public <T> Map<String, T> getCacheMap(final String key, Class<T> cls)
    {
        Map<String, T> resultMap = new HashMap<>();
        Map<Object, Object> map = stringRedisTemplate.opsForHash().entries(key);
        if(map.size() > 0){
            for (Map.Entry<Object, Object> o : map.entrySet()) {
                resultMap.put(String.valueOf(o.getKey()), JSON.parseObject(String.valueOf(o.getValue()),cls));
            }
        }
        return resultMap;
    }

    /**
     * 往Hash中存入数据
     *
     * @param key Redis键
     * @param hKey Hash键
     * @param value 值
     */
    public <T> void setCacheMapValue(final String key, final String hKey, final T value)
    {
        stringRedisTemplate.opsForHash().put(key, hKey, JSON.toJSONString(value));
    }

    /**
     * 获取Hash中的数据
     *
     * @param key Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    public <T> T getCacheMapValue(final String key, final String hKey, Class<T> cls)
    {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        return JSON.parseObject(opsForHash.get(key, hKey),cls);
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys, Class<T> cls)
    {
        return stringRedisTemplate.opsForHash().multiGet(key, hKeys).stream().map(o-> JSON.parseObject(String.valueOf(o),cls)).collect(Collectors.toList());
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(final String pattern)
    {
        return stringRedisTemplate.keys(pattern);
    }


}
