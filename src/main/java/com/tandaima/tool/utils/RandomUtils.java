package com.tandaima.tool.utils;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author zbrcel@gmail.com
 * @description RandomUtils
 */
public class RandomUtils {

    /**
     * 双重校验锁获取一个Random单例
     */
    public static ThreadLocalRandom getRandom() {
        return ThreadLocalRandom.current();
    }

    /**
     * 获得一个[0,max)之间的随机整数。
     */
    public static int getRandomInt(int max) {
        return getRandom().nextInt(max);
    }

    /**
     * 获得一个[min, max]之间的随机整数
     */
    public static int getRandomInt(int min, int max) {
        return getRandom().nextInt(max-min+1) + min;
    }

    /**
     * 从list中随机取得一个元素
     */
    public static <E> E getList(List<E> list){
        return list.get(getRandomInt(list.size()));
    }

    /**
     * 从set中随机取得一个元素
     */
    public static <E> E getSet(Set<E> set){
        int rn = getRandomInt(set.size());
        int i = 0;
        for (E e : set) {
            if(i==rn){
                return e;
            }
            i++;
        }
        return null;
    }

}
