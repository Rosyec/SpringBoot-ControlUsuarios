package com.example.miproyectowebspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.miproyectowebspringboot.models.entity.service.IUploadService;

@SpringBootApplication
public class MiproyectowebSpringbootApplication implements CommandLineRunner{

	@Autowired
	private IUploadService uploadService;

	public static void main(String[] args) {
		SpringApplication.run(MiproyectowebSpringbootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		uploadService.borrarDirectorio();
		uploadService.crearDirectorio();	
	}

}
