package com.example.demo_security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DemoSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoSecurityApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		System.out.println("nitish1234: "+passwordEncoder.encode("nitish1234"));
//		System.out.println("pankaj1234: "+passwordEncoder.encode("pankaj1234"));
//		System.out.println("karan1234: "+passwordEncoder.encode("karan1234"));
//	}
}
