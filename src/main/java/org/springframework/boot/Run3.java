package org.springframework.boot;

import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

public class Run3 {
    // 第四步
    public static void main(String[] args) throws IOException {
        ApplicationEnvironment applicationEnvironment = new ApplicationEnvironment();
        applicationEnvironment.getPropertySources().addLast(new ResourcePropertySource("step4",new ClassPathResource("step4.properties")));




        ConfigurationPropertySources.attach(applicationEnvironment);

        MutablePropertySources propertySources = applicationEnvironment.getPropertySources();
        for (PropertySource<?> propertySource : propertySources) {
            System.out.println(propertySource);
        }

        System.out.println(applicationEnvironment.getProperty("user.first-name"));
        System.out.println(applicationEnvironment.getProperty("user.middle-name"));
        System.out.println(applicationEnvironment.getProperty("user.last-name"));
    }
}
