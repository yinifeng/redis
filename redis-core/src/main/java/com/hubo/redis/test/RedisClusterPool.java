package com.hubo.redis.test;

import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * redis 集群
 */
public class RedisClusterPool {
    private volatile static ShardedJedisPool jedisPool;
    
    private volatile static JedisCluster jedisCluster;
    
    private RedisClusterPool(){}
    
    public static ShardedJedisPool getJedisPoolInstance(){
        if (jedisPool == null){
            synchronized (RedisClusterPool.class){
                if (jedisPool == null){
                    JedisPoolConfig poolConfig=getPoolConfig();
                    jedisPool = new ShardedJedisPool(poolConfig,getJedisShardInfos());
                }
            }
        }
        return jedisPool;
    }
    
    public static JedisCluster getRedisClusterPool(){
        if (jedisCluster == null){
            synchronized (RedisClusterPool.class){
                if (jedisCluster == null){
                    JedisPoolConfig poolConfig=getPoolConfig();
                    jedisCluster=new JedisCluster(getNodes(),poolConfig);
                }
            }
        }
        return jedisCluster;
    }
    
    private static Set<HostAndPort> getNodes(){
        Set<HostAndPort> nodes=new HashSet<>();
        nodes.add(new HostAndPort("192.168.123.60",6379));
        nodes.add(new HostAndPort("192.168.123.60",6380));
        return nodes;
    }
    
    private static JedisPoolConfig getPoolConfig(){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(1000);
        poolConfig.setMaxIdle(30);
        poolConfig.setMaxWaitMillis(100*1000);
        poolConfig.setTestOnBorrow(true);
        return poolConfig;
    }
    
    
    private static List<JedisShardInfo> getJedisShardInfos(){
        List<JedisShardInfo> infos=new ArrayList<>();
        infos.add(new JedisShardInfo("192.168.123.60",6379));
        infos.add(new JedisShardInfo("192.168.123.60",6380));
        return infos;
    }
}
