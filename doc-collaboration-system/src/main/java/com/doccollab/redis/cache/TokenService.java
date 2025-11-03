package com.doccollab.redis.cache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class TokenService {

    private final JedisPool jedisPool;
    private static final int TTL_SECONDS = 60 * 60 * 10; // 10 hours

    public TokenService(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public void storeToken(String token, String subject) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.setex(token, TTL_SECONDS, subject);
        }
    }

    public String getSubject(String token) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.get(token);
        }
    }

    public void revokeToken(String token) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(token);
        }
    }
}