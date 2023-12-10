package com.example.springbootheima;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.boot.*;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebServerApplicationContext;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;

import java.util.Arrays;
import java.util.Set;

public class Run1 {
    @SuppressWarnings("all")
    public static void main(String[] args) throws Exception {
        SpringApplication springApplication = new SpringApplication();
        springApplication.addInitializers(new ApplicationContextInitializer<ConfigurableApplicationContext>() {
            @Override
            public void initialize(ConfigurableApplicationContext applicationContext) {
                System.out.println("执行初始化器");
            }
        });

        System.out.println("2.封装args");
        // --server.port=8080 debug
        // --server.port=8080 这种是选项参数
        // debug 这种是非选项参数
        DefaultApplicationArguments defaultApplicationArguments = new DefaultApplicationArguments(args);


        System.out.println("8.创建容器");
        WebApplicationType type = WebApplicationType.SERVLET;
        GenericApplicationContext applicationContext = createApplicationContext(type);
        System.out.println("applicationContext:" + applicationContext);

        System.out.println("9.准备容器;执行初始化器");
        Set<ApplicationContextInitializer<?>> initializers = springApplication.getInitializers();
        for (ApplicationContextInitializer initializer : initializers) {
            initializer.initialize(applicationContext);
        }

        System.out.println("10.加载bean定义");
        AnnotatedBeanDefinitionReader annotatedBeanDefinitionReader = new AnnotatedBeanDefinitionReader(applicationContext.getDefaultListableBeanFactory());
        annotatedBeanDefinitionReader.register(Config.class);

        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(applicationContext.getDefaultListableBeanFactory());
        xmlBeanDefinitionReader.loadBeanDefinitions(new ClassPathResource("bean4.xml"));

        ClassPathBeanDefinitionScanner classPathBeanDefinitionScanner = new ClassPathBeanDefinitionScanner(applicationContext.getDefaultListableBeanFactory());
        classPathBeanDefinitionScanner.scan("com.example.springbootheima.sub");

        System.out.println("11.refresh");
        applicationContext.refresh();
        for (String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName + "--->" + applicationContext.getBeanFactory().getBeanDefinition(beanDefinitionName).getResourceDescription());
        }


        System.out.println("12.执行runner");
        for (CommandLineRunner commandLineRunner : applicationContext.getBeansOfType(CommandLineRunner.class).values()) {
            commandLineRunner.run(args);
        }

        for (ApplicationRunner applicationRunner : applicationContext.getBeansOfType(ApplicationRunner.class).values()) {
            applicationRunner.run(defaultApplicationArguments);
        }

        applicationContext.close();

    }

    private static GenericApplicationContext createApplicationContext(WebApplicationType type) {
        GenericApplicationContext context = null;
        switch (type) {
            case SERVLET:
                context = new AnnotationConfigServletWebServerApplicationContext();
                break;
            case REACTIVE:
                context = new AnnotationConfigReactiveWebServerApplicationContext();
                break;
            case NONE:
                context = new AnnotationConfigApplicationContext();

        }
        return context;
    }

    @Configuration
    static class Config {
        @Bean
        public Bean5 bean5() {
            return new Bean5();
        }

//        @Bean
//        public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
//            return new TomcatServletWebServerFactory();
//        }

        @Bean
        public CommandLineRunner commandLineRunner(){
            return new CommandLineRunner() {
                @Override
                public void run(String... args) throws Exception {
                    System.out.println(Arrays.toString(args));
                    System.out.println("CommandLineRunner()...");
                }
            };
        }

        @Bean
        public ApplicationRunner applicationRunner(){
            return new ApplicationRunner() {
                @Override
                public void run(ApplicationArguments args) throws Exception {
                    String[] sourceArgs = args.getSourceArgs();
                    System.out.println(Arrays.toString(sourceArgs));
                    System.out.println(args.getOptionNames());
                    System.out.println(args.getOptionValues("server.port"));
                    System.out.println(args.getNonOptionArgs());
                    System.out.println("ApplicationRunner()...");
                }
            };
        }
    }

    static class Bean4 {
    }

    static class Bean5 {
    }

    static class Bean6 {
    }


}
