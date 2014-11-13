package edu.sjsu.cmpe.cache.client;


import java.util.ArrayList;
import java.util.List;

public class Client {

    public static void main(String[] args) throws Exception {
        //System.out.println("Starting Cache Client...");
        String v[]={"a","b","c","d","e","f","g","h","i","j"};
        
        List<DistributedCacheService> dcs = new ArrayList<DistributedCacheService>();
       dcs.add( new DistributedCacheService("http://localhost:3000"));
       dcs.add( new DistributedCacheService("http://localhost:3001"));
       dcs.add( new DistributedCacheService("http://localhost:3002"));
       ConsistentHashing ch = new ConsistentHashing(dcs);
       //DistributedCacheService cache = new DistributedCacheService;
       DistributedCacheService cache = null;
       for(int i=0;i<10;i++)
       {
       cache = (DistributedCacheService) ch.get(v[i]);
	System.out.println("Starting Cache Client..." + cache.getUrl());
       cache.put(i+1, v[i]);
        System.out.println("put("+(i+1)+") =>" + v[i]);
       }
       for(int j=0;j<10;j++)
       {
    	cache = (DistributedCacheService) ch.get(v[j]);
    	String value = cache.get(j+1);
        System.out.println("get("+(j+1)+") =>" + value);
        System.out.println("Existing Cache Client..." + cache.getUrl());
       }
        
    }

}
