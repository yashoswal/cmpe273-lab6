package edu.sjsu.cmpe.cache.client;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

//import com.google.common.hash.Hashing;

public class ConsistentHashing<DistributedCacheService> {

  //private final HashFunction hashFunction;
  //private final int numberOfReplicas;
  private final SortedMap<String, DistributedCacheService> circle =
    new TreeMap<String, DistributedCacheService>();

  public ConsistentHashing( Collection<DistributedCacheService> nodes) throws NoSuchAlgorithmException {

    //this.hashFunction = hashFunction;
    //this.numberOfReplicas = numberOfReplicas;

    for (DistributedCacheService node : nodes) {
      add(node);
    }
  }

  public void add(DistributedCacheService node) throws NoSuchAlgorithmException {
    
      circle.put(hash(node.toString()),node);
    
  }

  public void remove(DistributedCacheService node) throws NoSuchAlgorithmException 
     {
      circle.remove(hash(node.toString()));
    }
  

  public DistributedCacheService get(String key) throws NoSuchAlgorithmException {
    if (circle.isEmpty()) {
      return null;
    }
    String hash =hash(key.toString());
    if (!circle.containsKey(hash)) {
      SortedMap<String, DistributedCacheService> tailMap =
        circle.tailMap(hash);
      hash = tailMap.isEmpty() ?
             circle.firstKey() : tailMap.firstKey();
    }
    return circle.get(hash);
    //return null;
  }
  public String hash(String s) throws NoSuchAlgorithmException
  {
	  MessageDigest m = MessageDigest.getInstance("MD5");
	  m.update(s.getBytes(),0,s.length());
	  String string = toHexString(m.digest());
	 
	  return string;
  }
  public static String toHexString(byte[] bytes) {
	    StringBuilder sb = new StringBuilder();
	    for(byte b : bytes) {
	        sb.append(String.format("%02x", b));
	    }
	    return sb.toString();
	}
  } 
