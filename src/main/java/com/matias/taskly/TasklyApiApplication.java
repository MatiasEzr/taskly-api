package com.matias.taskly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication
public class TasklyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TasklyApiApplication.class, args);
	}

}


