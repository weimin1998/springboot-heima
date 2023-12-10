package com.example.springbootheima;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;

@SpringBootApplication
public class SpringbootheimaApplication {

	public static void main(String[] args) throws IOException {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringbootheimaApplication.class, args);


		for (String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {

			System.out.println(beanDefinitionName);
		}

	}

}
