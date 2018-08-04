package com.hubo.redis.test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * 测试redis事物
 */
public class TestRedisTx {
    public static void main(String[] args) {
        Jedis jedis=new Jedis("192.168.123.60",6379);
        Transaction transaction = jedis.multi();
        transaction.set("k4","v44");
        transaction.set("k5","v55");
        
        //transaction.exec();
        transaction.discard();
    }
}
