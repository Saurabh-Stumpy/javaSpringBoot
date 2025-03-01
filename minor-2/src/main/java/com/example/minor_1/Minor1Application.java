package com.example.minor_1;

import com.example.minor_1.models.Admin;
import com.example.minor_1.models.SecuredUser;
import com.example.minor_1.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Minor1Application implements CommandLineRunner {

	@Autowired
	AdminService adminService;

	public static void main(String[] args)  {
		SpringApplication.run(Minor1Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Admin admin = Admin.builder()
				.name("Ram")
				.email("ram@gmail.com")
				.securedUser(
						SecuredUser.builder()
								.username("ram")
								.password("ram123")
								.build()
				)
				.build();
//		adminService.create(admin);

	}
}
