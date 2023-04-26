package com.example.demo;

import java.util.Collections;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;

@SpringBootApplication(scanBasePackages={
		"es.codeurjc.web.service", "com.example"})
@EnableScheduling
@EnableHazelcastHttpSession
public class TravelFriendlyApplication {

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

		return config;
	}
}
