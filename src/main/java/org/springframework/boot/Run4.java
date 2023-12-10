package org.springframework.boot;

import org.springframework.boot.context.config.ConfigDataEnvironmentPostProcessor;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.env.RandomValuePropertySourceEnvironmentPostProcessor;
import org.springframework.boot.logging.DeferredLog;
import org.springframework.boot.logging.DeferredLogs;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

public class Run4 {
    // 第5步 EnvironmentPostProcessor
    //
    public static void main(String[] args) throws IOException {
        SpringApplication springApplication = new SpringApplication();
        ApplicationEnvironment applicationEnvironment = new ApplicationEnvironment();

        System.out.println("增强前");
        MutablePropertySources propertySources = applicationEnvironment.getPropertySources();
        for (PropertySource<?> propertySource : propertySources) {
            System.out.println(propertySource);
        }
        // 将application.properties添加到applicationEnvironment
        ConfigDataEnvironmentPostProcessor configDataEnvironmentPostProcessor = new ConfigDataEnvironmentPostProcessor(new DeferredLogs(),new DefaultBootstrapContext());
        configDataEnvironmentPostProcessor.postProcessEnvironment(applicationEnvironment,springApplication);

        // java.util.Random, random源
        RandomValuePropertySourceEnvironmentPostProcessor randomValuePropertySourceEnvironmentPostProcessor = new RandomValuePropertySourceEnvironmentPostProcessor(new DeferredLog());
        randomValuePropertySourceEnvironmentPostProcessor.postProcessEnvironment(applicationEnvironment, springApplication);
        System.out.println("增强后");
        MutablePropertySources propertySources1 = applicationEnvironment.getPropertySources();
        for (PropertySource<?> propertySource : propertySources1) {
            System.out.println(propertySource);
        }

        System.out.println(applicationEnvironment.getProperty("age"));

        System.out.println(applicationEnvironment.getProperty("random.int"));
        System.out.println(applicationEnvironment.getProperty("random.int"));
        System.out.println(applicationEnvironment.getProperty("random.int"));
        System.out.println(applicationEnvironment.getProperty("random.uuid"));
        System.out.println(applicationEnvironment.getProperty("random.uuid"));
        System.out.println(applicationEnvironment.getProperty("random.uuid"));
    }
}
