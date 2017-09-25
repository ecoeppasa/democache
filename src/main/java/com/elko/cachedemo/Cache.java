/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elko.cachedemo;

import java.util.ArrayList;
import org.apache.commons.collections.MapIterator;
import org.apache.commons.collections.map.LRUMap;

/**
 *
 * @author elko
 */
public class Cache <K, T> {
    
    private long age;
    private LRUMap cacheMap;
    
    /*
    procedure for create cache memory
    parameter will be filled by age of cache, time interval for clean up cache memory  --> will check is caches are expired  and then delete it,
    and max item of cache memory
    note : if the age is zero then it lives forever!
    */
    public Cache(long age, final long timeInterval, int maxItems) {
    // set age from millisecond to second
        this.age = age * 1000;
        // instantiate cache map
        cacheMap = new LRUMap(maxItems);
 
        // check if age and interval has value > 0
        if (age > 0 && timeInterval > 0) {
            
            // creating thread...
            Thread t = new Thread(new Runnable() {
                public void run() {
                   // execute while thread is alive
                    while (true) {
                        try {
                            // if alive thread will sleep for "timeInterval" seconds
                            Thread.sleep(timeInterval * 1000);
                        } catch (InterruptedException ex) {
                        }
                        // execute clean up the cache
                        cleanup();
                    }
                }
            });
            // A daemon thread is a thread that does not prevent the JVM from exiting 
            // when the program finishes but the thread is still running.
            // so jvm will exit when program finishes , if it set false, jvm still running until we stop it manually
            t.setDaemon(true);
            // starting thread
            t.start();
        }
     }  
        // procedure for put a cache value into cache map
        public void put(K key, T value) {
        synchronized (cacheMap) {
           // put the cache data to cache map
            cacheMap.put(key, new CacheObject<T>(value));
        }
    }
        
 // procedure to get cached
    @SuppressWarnings("unchecked")
    public T get(K key) {
        synchronized (cacheMap) {
            // create object for CacheObject class
            CacheObject<T> cacheObject = (CacheObject<T>) cacheMap.get(key);
            // checking if cacheobject is null
            if (cacheObject == null)
                // if object null will show nothing, 
                return null;
            else {
                // if object has value set lastaccess with the time of system (now)
                cacheObject.lastAccessed = System.currentTimeMillis();
                // then return the value of object
                return cacheObject.value;
            }
        }
    }
 
    // procedure for remove the cache from cache memory
    public void remove(K key) {
        synchronized (cacheMap) {
            // remove the cache from cache memory
            cacheMap.remove(key);
        }
    }
    
    // procedure get  size of cacheMap
    public int size() {
        synchronized (cacheMap) {
            // this code will return the number of cacheMap is used
            return cacheMap.size();
        }
    }
 // cleanup procedure
    @SuppressWarnings("unchecked")
    public void cleanup() {
        // show message when cleanup procedure is running
        System.out.println("cleanup is running...");
        // get time of system  (yyyy-MM-dd hh:mm:ss.SSS) --> set to var "now" 
        long now = System.currentTimeMillis();
        // initializing deleteKey as array of list
        ArrayList<K> deleteKey = null;
        
        // reading cacheMap from the same memory of other thread, so we use synchronized methods
        synchronized (cacheMap) {
           // create map iterator and initialized to mapiterator
            MapIterator mapIterator = cacheMap.mapIterator();
            
            // creating object of array list
            deleteKey = new ArrayList<K>((cacheMap.size() / 2) + 1);
            // creating key variable
            K key = null;
            // creating object of CacheObject
            CacheObject<T> cacheObject = null;
            // looping while mapiterator has a cache
            while (mapIterator.hasNext()) {
                // get key of cache
                key = (K) mapIterator.next();
                // set object of cacheObject to "cacheObject" variable
                cacheObject = (CacheObject<T>) mapIterator.getValue();
                // checking is cacheObject null and age is expired
                 // Remember if the age is zero then it lives forever!!!
                if (cacheObject != null && (now > (age + cacheObject.lastAccessed))) {
                    // if object are not null and age is expired the cache will be removed.
                    // the key of caches are collecting in list of deleted key
                    deleteKey.add(key);
                }
            }
        }
        
        // looping as much of deletedKey list
        for (K key : deleteKey) {
            synchronized (cacheMap) {
                // removing the cache from cache memory as per key of cache
                cacheMap.remove(key);
            }
            //  tells the jvm it's willing to let other threads be scheduled in its stead
            Thread.yield();
        }
    }
            
   
    

    
}
