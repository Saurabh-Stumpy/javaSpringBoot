package com.example.demo_security_db;

import com.example.demo_security_db.models.Constants;
import com.example.demo_security_db.models.DemoUser;
import com.example.demo_security_db.repositories.DemoSecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@SpringBootApplication
public class DemoSecurityDbApplication implements CommandLineRunner {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	DemoSecurityRepository demoSecurityRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoSecurityDbApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

//		DemoUser demoUser = DemoUser.builder()
//				.username("saurabh@securitydb.com")
//				.password(passwordEncoder.encode("saurabh123"))
//				.authorities(Constants.CEO_DETAILS_AUTHORITY+Constants.DELIMIER+Constants.SCHEDULE_APPOINTMENTS)
//				.build();
//
//		DemoUser demoUser1 = DemoUser.builder()
//				.username("rashi@securitydb.com")
//				.password(passwordEncoder.encode("rashi123"))
//				.authorities(Constants.DOCTOR_DETAILS_AUTHORITY)
//				.build();
//
//		demoSecurityRepository.saveAll(Arrays.asList(demoUser,demoUser1));

	}
}
