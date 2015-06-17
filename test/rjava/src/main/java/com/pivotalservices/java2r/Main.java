package com.pivotalservices.java2r;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@EnableAutoConfiguration
public class Main {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(new Object[] {Main.class, R.class, RController.class}, args);
	}

}
