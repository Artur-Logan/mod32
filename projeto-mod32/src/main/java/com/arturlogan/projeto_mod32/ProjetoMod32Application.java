package com.arturlogan.projeto_mod32;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.arturlogan.projeto_mod32")
@EnableJpaRepositories(basePackages = "com.arturlogan.projeto_mod32.repositories")
public class ProjetoMod32Application {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoMod32Application.class, args);
	}
}
