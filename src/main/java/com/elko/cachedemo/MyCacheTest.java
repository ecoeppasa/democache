/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elko.cachedemo;

/**
 *
 * @author elko
 */
public class MyCacheTest {
    
        // Test with age of cache = 2 seconds
        // Time interval = 5 seconds
        // maxItems = 100
    Cache<Integer, String> cache  = new Cache<Integer, String>(2, 5, 100);
    public String getDocument(int key,String request) {
    cache.put(key, document_producer.getDocument(request));                
    return cache.get(key);        
    }
    
    public int cacheSize (){
        return cache.size();
    }
    
     public static void main(String[] args) throws InterruptedException {
        // instantiate MyCacheClass to object   
        MyCacheTest CacheTest = new MyCacheTest();
    
        // testing with insert 10000 cached data,
        // you can change the number however much you want. 
        // as long as it's within an integer range
       for(int i=0; i<9999; i++) {
           // put cache memory with "hello" and "world" string
           // cache memory will auto clean when the cache is expired and time interval is done 
           // so i want you to notice in every output  to ensure cleanup are running --> will print "cleanup is running..."
           System.out.println(CacheTest.getDocument(i, "hello"));           
           System.out.println(CacheTest.getDocument(i, "world"));

       
       }
          // show size of cache memory  after inserted cached data
          System.out.println("cache size: " + CacheTest.cacheSize());
          // hold process for 10 seconds, so cached will be removed by thread in Cache Class
          Thread.sleep(10000);
          // show size of cache memory  after removing expired cached data
          System.out.println("cache size after 10 seconds: " + CacheTest.cacheSize());
          
          /*          
           =============================== SUMMARY==============================
         
          */
          
    }
     
}
