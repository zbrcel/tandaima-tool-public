package com.tandaima.tool.core;

import org.redisson.RedissonMultiLock;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author zbrcel@gmail.com
 * @description redisson 工具锁
 */
@Component
public class RedissonService {

    @Resource(name = "redissonClient")
    private RedissonClient redissonClient;

    /**
     * 解锁 RLock
     * @param lock 锁
     */
    public void unlock(RLock lock){
        lock.unlock();
    }

    /**
     * 解锁 RedissonMultiLock
     * @param lock 锁
     */
    public void unlock(RedissonMultiLock lock){
        lock.unlock();
    }

    /**
     * 解锁 RedissonRedLock
     * @param lock 锁
     */
    public void unlock(RedissonRedLock lock){
        lock.unlock();
    }

    /**
     * redis可重入锁
     * Redisson的分布式可重入锁RLock Java对象实现了java.util.concurrent.locks.Lock接口，同时还支持自动过期解锁。
     * @param lockKey key
     */
    public RLock lock(String lockKey){
        RLock lock = redissonClient.getLock(lockKey);
        //最常见的使用方法
        lock.lock();
        return lock;
    }

    /**
     * 可重入锁
     * @param lockKey key 锁id
     * @param timeout 过期时间(秒)
     */
    public RLock lock(String lockKey,long timeout){
        RLock lock = redissonClient.getLock(lockKey);
        // 支持过期解锁功能,10秒钟以后自动解锁, 无需调用unlock方法手动解锁
        lock.lock(timeout, TimeUnit.SECONDS);
        return lock;

    }

    /**
     * Redisson同时还为分布式锁提供了异步执行的相关方法：
     * @param lockKey key
     */
    public RLock asyncLock(String lockKey){
        RLock lock = redissonClient.getLock(lockKey);
        lock.lockAsync();
        return lock;

    }

    /**
     * Redisson同时还为分布式锁提供了异步执行的相关方法：
     * @param lockKey key 锁id
     * @param timeout 过期时间(秒)
     */
    public RLock asyncLock(String lockKey,long timeout){
        RLock lock = redissonClient.getLock(lockKey);
        lock.lockAsync(timeout, TimeUnit.SECONDS);
        return lock;
    }

    /**
     * 公平锁（Fair Lock）
     * Redisson分布式可重入公平锁也是实现了java.util.concurrent.locks.Lock接口的一种RLock对象。在提供了自动过期解锁功能的同时，保证了当多个Redisson客户端线程同时请求加锁时，优先分配给先发出请求的线程
     * @param lockKey key
     */
    public RLock fairLock(String lockKey){
        RLock fairLock = redissonClient.getFairLock(lockKey);
        fairLock.lock();
        return fairLock;
    }

    /**
     * 公平锁（Fair Lock）
     * Redisson分布式可重入公平锁也是实现了java.util.concurrent.locks.Lock接口的一种RLock对象。在提供了自动过期解锁功能的同时，保证了当多个Redisson客户端线程同时请求加锁时，优先分配给先发出请求的线程
     * @param lockKey key 锁id
     * @param timeout 过期时间(秒)
     */
    public RLock fairLock(String lockKey,long timeout){
        RLock fairLock = redissonClient.getFairLock(lockKey);
        fairLock.lock(timeout, TimeUnit.SECONDS);
        return fairLock;
    }

    /**
     * Redisson同时还为分布式可重入公平锁提供了异步执行的相关方法：
     * @param lockKey key
     */
    public RLock fairLockAsync(String lockKey){
        RLock fairLock = redissonClient.getFairLock(lockKey);
        fairLock.lockAsync();
        return fairLock;
    }

    /**
     * Redisson同时还为分布式可重入公平锁提供了异步执行的相关方法：
     * @param lockKey key 锁id
     * @param timeout 过期时间(秒)
     */
    public RLock fairLockAsync(String lockKey,long timeout){
        RLock fairLock = redissonClient.getFairLock(lockKey);
        fairLock.lockAsync(timeout, TimeUnit.SECONDS);
        return fairLock;
    }

    /**
     * 联锁（MultiLock）
     * Redisson的RedissonMultiLock对象可以将多个RLock对象关联为一个联锁，每个RLock对象实例可以来自于不同的Redisson实例。
     * @param lockKeys keys
     */
    public RedissonMultiLock multiLock(String... lockKeys){
        RLock[] lockList = new RLock[lockKeys.length];
        for(int i=0;i<lockKeys.length;i++){
            lockList[i] = redissonClient.getLock(lockKeys[i]);
        }
        RedissonMultiLock lock = new RedissonMultiLock(lockList);
        // 同时加锁：lock1 lock2 lock3, 所有的锁都上锁成功才算成功。
        lock.lock();
        return lock;
    }

    /**
     * 红锁（RedLock）
     *
     * Redisson的RedissonRedLock对象实现了Redlock介绍的加锁算法。该对象也可以用来将多个RLock
     * 对象关联为一个红锁，每个RLock对象实例可以来自于不同的Redisson实例
     * @param lockKeys key
     */
    public RedissonRedLock testRedLock(String... lockKeys){
        RLock[] lockList = new RLock[lockKeys.length];
        for(int i=0;i<lockKeys.length;i++){
            lockList[i] = redissonClient.getLock(lockKeys[i]);
        }
        RedissonRedLock lock = new RedissonRedLock(lockList);
        // 同时加锁：lock1 lock2 lock3, 红锁在大部分节点上加锁成功就算成功。
        lock.lock();
        return lock;
    }

    /*
     * 另外还有几种锁：
     *
     * 读写锁（ReadWriteLock）
     * Redisson的分布式可重入读写锁RReadWriteLock Java对象实现了java.util.concurrent.locks.ReadWriteLock接口。同时还支持自动过期解锁。该对象允许同时有多个读取锁，但是最多只能有一个写入锁。
     *
     * 信号量（Semaphore）
     * Redisson的分布式信号量（Semaphore）Java对象RSemaphore采用了与java.util.concurrent.Semaphore相似的接口和用法。
     *
     * 可过期性信号量（PermitExpirableSemaphore）
     * Redisson的可过期性信号量（PermitExpirableSemaphore）实在RSemaphore对象的基础上，为每个信号增加了一个过期时间。每个信号可以通过独立的ID来辨识，释放时只能通过提交这个ID才能释放。
     *
     * 闭锁（CountDownLatch）
     * Redisson的分布式闭锁（CountDownLatch）Java对象RCountDownLatch采用了与java.util.concurrent.CountDownLatch相似的接口和用法。
     *
     */
}
