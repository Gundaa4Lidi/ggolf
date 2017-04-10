package com.galaxy.ggolf.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericCache<K,V> {
	
	private ConcurrentHashMap<K,V> cache;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public GenericCache(){
		this.cache= new ConcurrentHashMap<K,V>();
	}

	public void put(K key, V value){
		logger.debug("putting key [{}] into cache", key);
		this.cache.put(key, value);
	}
	
	public V get(K key){
		logger.debug("getting key [{}] from cache", key);
		return this.cache.get(key);
	}
	
	public void remove(K key){
		logger.debug("removing key [{}] from cache", key);
		this.cache.remove(key);
	}
	
	public Collection<V> values(){
		ArrayList<V> result = new ArrayList<V>();
		for(K key : cache.keySet()){
			result.add(cache.get(key));
		}
		return result;
	}
	
}
