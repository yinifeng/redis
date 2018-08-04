package com.hubo.redis.test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class RedisTransaction {
    
    public boolean execMethod() throws InterruptedException {
        Jedis jedis=new Jedis("192.168.123.60",6379);
        int balance;//可用余额
        int debt;//欠额
        int amtToSubtract=10;//实刷金额
        jedis.watch("balance");
        Thread.sleep(7000);//睡眠几秒，同时去修改这个key的值，事物会回滚
        balance=Integer.parseInt(jedis.get("balance"));
        if(balance < amtToSubtract){
            jedis.unwatch();
            System.out.println("modify...");
            return false;
        }else{
            System.out.println("***********transaction");
            Transaction transaction = jedis.multi();
            transaction.decrBy("balance",amtToSubtract);
            transaction.incrBy("debt",amtToSubtract);
            transaction.exec();
            balance=Integer.parseInt(jedis.get("balance"));
            debt=Integer.parseInt(jedis.get("debt"));
            System.out.println("**********balance:"+balance);
            System.out.println("**********debt:"+debt);
            return true;
        }
    }

    /**
     * 通俗的讲，watch命令就是标记一个键，如果标记了一个键
     * 在提交事物前如果该键被别人修改过，那么事物就会失败，这种情况通常
     * 可以在程序中尝试一次
     * 首先标记键balance，然后检查余额足够，不足就取消标记，并不做扣减
     * 足够的话，就启动事物进行更新操作
     * 如果在此期间键balance被其它人修改，那在提交事物（执行exec）时就会报错
     * 程序中通常可以捕获这类错误再重新执行一次，直到成功。
     * 
     * set balance 100
     * set debt 0
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        RedisTransaction rt=new RedisTransaction();
        boolean retValue = rt.execMethod();
        System.out.println("main retValue.........: "+retValue);
    }
}
