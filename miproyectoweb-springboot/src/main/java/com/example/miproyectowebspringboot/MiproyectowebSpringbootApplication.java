package com.example.miproyectowebspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.miproyectowebspringboot.models.entity.service.IUploadService;

@SpringBootApplication
public class MiproyectowebSpringbootApplication implements CommandLineRunner{

	@Autowired
	private IUploadService uploadService;

	@Autowired
    private BCryptPasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(MiproyectowebSpringbootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		uploadService.borrarDirectorio();
		uploadService.crearDirectorio();
		
		String password = "12345";
		for(int i = 0; i<2; i++){
			String bCryptPassword = this.passwordEncoder.encode(password);
			System.out.println(bCryptPassword);
		}

	}

}
