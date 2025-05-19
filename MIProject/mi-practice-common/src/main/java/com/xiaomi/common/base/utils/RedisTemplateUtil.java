package com.xiaomi.common.base.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisTemplateUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 设置带过期时间的键值对
     */
    public void set(String key, String value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 根据key获取值
     */
    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    /**
     * 根据key删除值
     */
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 尝试获取分布式锁，最多重试指定次数
     *
     * @param lockKey     锁的 key（不能为空）
     * @param lockValue   锁的值（如 UUID，用于释放锁时校验）
     * @param expireTime  锁的过期时间
     * @param timeUnit    时间单位
     * @param maxRetries  最大重试次数（0 表示不重试）
     * @param retryIntervalMillis 每次重试间隔（毫秒）
     * @return 是否成功获取锁
     */
    public boolean tryLock(String lockKey, String lockValue, long expireTime, TimeUnit timeUnit, int maxRetries, long retryIntervalMillis) {
        if (StringUtils.isBlank(lockValue)) {
            log.warn("尝试获取锁失败：lockKey 为空");
            return false;
        }

        int attempt = 0;
        while (attempt <= maxRetries) {
            try {
                Boolean success = redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, expireTime, timeUnit);
                if (Boolean.TRUE.equals(success)) {
                    log.info("成功获取锁: {}", lockKey);
                    return true;
                } else {
                    attempt++;
                    if (attempt <= maxRetries) {
                        log.info("获取锁失败，第 {} 次重试: {}", attempt, lockKey);
                        Thread.sleep(retryIntervalMillis);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("获取锁时线程被中断", e);
                return false;
            }
        }

        log.warn("获取锁失败，已达最大重试次数 {}: {}", maxRetries, lockKey);
        return false;
    }

    /**
     * 释放锁
     * @param lockKey 锁的key
     */
    public boolean releaseLock(String lockKey) {
         return Boolean.TRUE.equals(redisTemplate.delete(lockKey));
    }


    /**
     * 删除指定前缀的key
     */
    public void deleteKeysByPrefix(String keyPrefix) {
        // 打印实际扫描的模式
        String pattern = keyPrefix + "*";
        log.info("尝试扫描 Redis key 模式: {}", pattern);

        Set<String> keys = redisTemplate.keys(pattern);
        // 打印匹配结果
        log.info("匹配到 {} 个 key", keys != null ? keys.size() : 0);
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
            log.info("已删除匹配前缀 {} 的 {} 个 key", keyPrefix, keys.size());
        } else {
            log.info("未找到匹配前缀 {} 的 key", keyPrefix);
        }
    }
}
