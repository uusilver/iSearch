package com.tmind.qrcode.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.util.Map;

/**
 * 利用范型在构造缓存对象时来指定存放的数据类型
 *
 * Created by lijunying on 16/3/19.
 */
public class Ehcache<T> {

    private static  CacheManager manager = null;

    private static Cache cache1 = null;

    //初始化缓存对象
    static {
        manager =  CacheManager.create();
        // 取出所有的cacheName
        String names[] = manager.getCacheNames();
//        System.out.println("----all cache names----");
//        for (int i = 0; i < names.length; i++) {
//            System.out.println(names[i]);
//        }
//        System.out.println("----------------------");
        // 得到一个cache对象
        cache1 = manager.getCache(names[0]);
    }

    public static<T> void setCache1(String key, T value){
        cache1.put(new Element(key, value));
    }

    public static<T> T getCache1(String key){
        Element element = cache1.get(key);
        if(element!=null)
            return (T)element.getObjectValue();
        else
            return null;

    }


}
