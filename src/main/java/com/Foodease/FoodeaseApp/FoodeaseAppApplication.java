package com.Foodease.FoodeaseApp;

import com.Foodease.FoodeaseApp.POJO.Admin;
import com.Foodease.FoodeaseApp.dao.AdminDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.management.relation.Role;
import java.util.Optional;

@SpringBootApplication
public class FoodeaseAppApplication implements CommandLineRunner {
	@Autowired
	AdminDao adminDao ;

	public static void main(String[] args) {
		SpringApplication.run(FoodeaseAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String roleName ="admin";
		Optional<Admin> adminOptional = adminDao.findByRole(roleName);
		if (adminOptional.isEmpty()){
			Admin admin = new Admin();
			admin.setName("admin");
			admin.setEmail("admindefault@gmail.com");
			admin.setPassword("password");
			admin.setContactNumber("1234567890");
			admin.setStatus("true");
			admin.setRole(roleName);
			adminDao.save(admin);
		}
	}
}
