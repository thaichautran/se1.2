package com.memorise.memorise_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class MemoriseBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemoriseBackendApplication.class, args);
		System.out.println("Hello World!");
		System.out.println(new Date());
	}

}
