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
		var req = PageRequest.builder()
			.userId(4L)
			.title("Gabo")
			.build();

		var res = this.pageService.readByTitle("User2 Page");

		System.out.println(res);
	}
}
