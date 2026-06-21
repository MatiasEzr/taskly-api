package com.matias.taskly;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;


@SpringBootApplication
@ConfigurationPropertiesScan
public class TasklyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TasklyApiApplication.class, args);

	}

	//Con el Environment podemos acceder a todos los recursos que guardamos como propiedades de distintas fuentes	@Bean
    ApplicationRunner printWelcomeMessage(Environment environment) {
		return args -> {
			String message = environment.getProperty("app.welcome-message");
			System.out.println(message);
		};
	}
}