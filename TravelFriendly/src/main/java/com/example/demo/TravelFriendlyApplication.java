package com.example.demo;

import java.util.Collections;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.MapConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
@SpringBootApplication(scanBasePackages={
		"es.codeurjc.web.service", "com.example"})
@EnableScheduling
@EnableHazelcastHttpSession
@EnableCaching
public class TravelFriendlyApplication {
	
	private static final Log LOG = LogFactory.getLog(TravelFriendlyApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(TravelFriendlyApplication.class, args);
		
	}
	@Bean
	public Queue myQueue() {
    	return new Queue("messages", false);
	}
	
	@Bean
	public Config config() {

		Config config = new Config();

		JoinConfig joinConfig = config.getNetworkConfig().getJoin();

		joinConfig.getMulticastConfig().setEnabled(false);
		joinConfig.getTcpIpConfig().setEnabled(true).setMembers(Collections.singletonList("192.168.12.254"));
		
		MapConfig cacheUsers = new MapConfig();
		cacheUsers.setTimeToLiveSeconds(180);
		//cacheUsers.setEvictionConfig(EvictionPolicy.LFU);
		config.getMapConfigs().put("viajes", cacheUsers);
		config.getMapConfigs().put("opiniones", cacheUsers);
		
		return config;
	}
	
	 @Bean
	 public CacheManager cacheManager() {
	   LOG.info("Activating cache...");
	    return new ConcurrentMapCacheManager("viajes","opiniones");
	 }
}
