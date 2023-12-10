package org.springframework.boot;

import org.springframework.boot.context.config.ConfigDataEnvironmentPostProcessor;
import org.springframework.boot.context.event.EventPublishingRunListener;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.EnvironmentPostProcessorApplicationListener;
import org.springframework.boot.env.RandomValuePropertySourceEnvironmentPostProcessor;
import org.springframework.boot.logging.DeferredLog;
import org.springframework.boot.logging.DeferredLogs;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.io.IOException;
import java.util.List;

public class Run5 {
    // 第5步 EnvironmentPostProcessor
    // 
    public static void main(String[] args) throws IOException {
        SpringApplication springApplication = new SpringApplication();

        springApplication.addListeners(new EnvironmentPostProcessorApplicationListener());

        List<String> names = SpringFactoriesLoader.loadFactoryNames(EnvironmentPostProcessor.class, Run5.class.getClassLoader());
        for (String name : names) {
            System.out.println(name);
        }
        ApplicationEnvironment applicationEnvironment = new ApplicationEnvironment();

        System.out.println("增强前");
        MutablePropertySources propertySources = applicationEnvironment.getPropertySources();
        for (PropertySource<?> propertySource : propertySources) {
            System.out.println(propertySource);
        }


        EventPublishingRunListener eventPublishingRunListener = new EventPublishingRunListener(springApplication, args);

        eventPublishingRunListener.environmentPrepared(new DefaultBootstrapContext(),applicationEnvironment);

        System.out.println("增强后");
        MutablePropertySources propertySources1 = applicationEnvironment.getPropertySources();
        for (PropertySource<?> propertySource : propertySources1) {
            System.out.println(propertySource);
        }


    }
}
