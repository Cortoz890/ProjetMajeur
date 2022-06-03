package com.project;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjetPompier {
	
	public static void main(String[] args) {
		System.getProperties().put("server.port",3080);
		SpringApplication.run(ProjetPompier.class,args);
	}
}
