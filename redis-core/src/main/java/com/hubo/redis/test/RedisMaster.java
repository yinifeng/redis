package com.hubo.redis.test;

import redis.clients.jedis.Jedis;

/**
 * redis主从复制
 */
public class RedisMaster {
    public static void main(String[] args) {
        Jedis jedis_M=new Jedis("192.168.123.60",6379);
        Jedis jedis_S=new Jedis("192.168.123.60",6380);
        
        //把另外台机器当作master
        jedis_S.slaveof("192.168.123.60",6379);
        
        //master写
        jedis_M.set("k1","v1");
        
        //第一次可能取不出来，内存速度太快，主机数据为同步到从机上去
        System.out.println(jedis_S.get("k1"));
    }
}
