package com.noticiacerta.bot;

import org.springframework.boot.SpringApplication;

public class TestNoticiaCertaBotApplication {

	public static void main(String[] args) {
		SpringApplication.from(NoticiaCertaBotApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
