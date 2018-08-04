package com.hubo.redis.test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class TestJedisPool {
    public static void main(String[] args) {
        JedisPool jedisPool = JedisPoolUtils.getJedisPoolInstance();
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.set("aa","bb");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JedisPoolUtils.release(jedisPool,jedis);
        }

    }
}
