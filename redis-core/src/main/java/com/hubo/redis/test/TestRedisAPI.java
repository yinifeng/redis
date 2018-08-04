package com.hubo.redis.test;

import redis.clients.jedis.Jedis;

import java.util.Set;

public class TestRedisAPI {
    public static void main(String[] args) {
        Jedis jedis=new Jedis("192.168.123.60",6379);
        String result = jedis.set("k1", "v1");
        System.out.println(result);
        jedis.set("k2","v2");
        jedis.set("k3", "v3");

        System.out.println(jedis.get("k3"));

        Set<String> keySet = jedis.keys("*");
        System.out.println(keySet.size());
        
    }
}
