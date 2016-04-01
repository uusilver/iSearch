package com.tmind.qrcode.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * Created by lijunying on 16/3/19.
 */
public class Ehcache {

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

    public static void setCache1(String key, String value){
        cache1.put(new Element(key, value));
    }

    public static String getCache1(String key){
        Element element = cache1.get(key);
        if(element!=null)
            return element.getObjectValue().toString();
        else
            return null;

    }

    public static void main(String[] args) throws InterruptedException {

        Ehcache.setCache1("test","lijunying");
        System.out.println(Ehcache.getCache1("test"));
        if(Ehcache.getCache1("aaa") == null){
            System.out.println("Empty");

        }
    }
}
