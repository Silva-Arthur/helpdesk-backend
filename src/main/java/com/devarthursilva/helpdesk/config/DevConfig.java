package com.devarthursilva.helpdesk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.devarthursilva.helpdesk.services.DBService;

/**
 * Criação de uma classe de profile de teste
 * 
 * */
@Configuration
@Profile("dev")
public class DevConfig {
	
	@Autowired
	private DBService dbService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String value;
	
	@Bean
	public boolean instanciaDB() {
		if (value.equals("create")) {
			this.dbService.instanciaDB();
			return true;
		}
		return false;
	}
	

}
