package org.springframework.boot;

import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

public class Run2 {
    // 第三步，添加命令行参数，到ApplicationEnvironment
    public static void main(String[] args) throws IOException {
        // 包括：系统环境变量，application.properties， yaml ，命令行参数
        ApplicationEnvironment applicationEnvironment = new ApplicationEnvironment();

        applicationEnvironment.getPropertySources().addLast(new ResourcePropertySource(new ClassPathResource("application.properties")));
        applicationEnvironment.getPropertySources().addFirst(new SimpleCommandLinePropertySource(args)); // --server.port=7070

        MutablePropertySources propertySources = applicationEnvironment.getPropertySources();
        for (PropertySource<?> propertySource : propertySources) {
            System.out.println(propertySource);
        }
        System.out.println(applicationEnvironment.getProperty("JAVA_HOME"));
        System.out.println(applicationEnvironment.getProperty("user"));
        System.out.println(applicationEnvironment.getProperty("server.port"));
    }
}
