package com.gabo.kchika;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gabo.kchika.dtos.PageRequest;
import com.gabo.kchika.services.PageService;

@SpringBootApplication
public class KchikaApplication implements CommandLineRunner{

	@Autowired
	private PageService pageService;
	public static void main(String[] args) {
		SpringApplication.run(KchikaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		this.pageService.delete("User3 Page");
	}
}
