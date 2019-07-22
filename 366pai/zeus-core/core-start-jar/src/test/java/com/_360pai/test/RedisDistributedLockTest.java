package com._360pai.test;

import com._360pai.arch.core.redis.RedisLock;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.UUID;

public class RedisDistributedLockTest extends TestBase {

    //库存个数
    static int goodsCount = 900;

    //卖出个数
    static int saleCount = 0;

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Test
    public void name1() {
        String uuid = UUID.randomUUID().toString();
        System.out.println("uuid = " + uuid);
        RedisLock lock = new RedisLock(redisTemplate, uuid, 1000, 20000);
        try {
            if (lock.lock()) {
                //需要加锁的代码
                if (goodsCount > 0) {
                    goodsCount--;
                    System.out.println("剩余库存：" + goodsCount + " 卖出个数" + ++saleCount);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //为了让分布式锁的算法更稳键些，持有锁的客户端在解锁之前应该再检查一次自己的锁是否已经超时，再去做DEL操作，因为可能客户端因为某个耗时的操作而挂起，
            //操作完的时候锁因为超时已经被别人获得，这时就不必解锁了。 ————这里没有做
            lock.unlock();
        }
    }

//    @Test
//    public void name() throws InterruptedException {
//
//        for (int i = 0; i < 1000; i++) {
//            new Thread(() -> {
//                try {
//                    Thread.sleep(2);
//                } catch (InterruptedException e) {
//
//                }
//                boolean lock = false;
//
//                System.out.println("uuid = " + uuid);
//                while (!lock) {
//                    lock = redisDistributedLock.lock(uuid, 10000, 3, 100);
//                }
//                if (lock) {
//
//                }
//                redisDistributedLock.releaseLock(uuid);
//            }).start();
//        }
//        Thread.sleep(3000);
//    }
}