package com.hubo.redis.test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis 连接池
 */
public class JedisPoolUtils {
    
    private static volatile JedisPool jedisPool;
    
    private JedisPoolUtils(){}

    /**
     * 获取redis连接池
     * @return
     */
    public static JedisPool getJedisPoolInstance(){
        if(jedisPool == null){
            synchronized (JedisPoolUtils.class){
                if (jedisPool == null){
                    JedisPoolConfig poolConfig=new JedisPoolConfig();
                    poolConfig.setMaxTotal(1000);
                    poolConfig.setMaxIdle(30);
                    poolConfig.setMaxWaitMillis(100*1000);
                    poolConfig.setTestOnBorrow(true);
                    jedisPool=new JedisPool(poolConfig,"192.168.123.60",6379);
                }
            }
        }
        return jedisPool;
    }

    /**
     * 回收redis连接
     * @param jedisPool
     * @param jedis
     */
    public static void release(JedisPool jedisPool,Jedis jedis){
        if (jedis != null){
            //jedisPool.returnResourceObject(jedis);
            jedisPool.returnResource(jedis);
        }
    }
}
