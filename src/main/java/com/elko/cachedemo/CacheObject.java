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
public class CacheObject<T> {
    // create lastAccess variable
    public long lastAccessed = System.currentTimeMillis();
    // create variable value of object it's with generic ata type
    public T value;
        // create constructor of CacheObject class
        public CacheObject(T value) {
            // return the value of parameter to value of class
            this.value = value;
        }
}
