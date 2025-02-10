package com.gabo.kchika;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gabo.kchika.dtos.PageRequest;
import com.gabo.kchika.services.PageService;

@SpringBootApplication
public class KchikaApplication{
	public static void main(String[] args) {
		SpringApplication.run(KchikaApplication.class, args);
	}
}
