package com.example.springbootheima;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("1.获取bean definition 源");
        SpringApplication springApplication = new SpringApplication(Main.class);
        Set<String> set = new HashSet<>();
        set.add("classpath:ioc.xml");
        springApplication.setSources(set);


        System.out.println("2.推断应用类型");
        Method deduceFromClasspath = WebApplicationType.class.getDeclaredMethod("deduceFromClasspath");
        deduceFromClasspath.setAccessible(true);
        Object invoke = deduceFromClasspath.invoke(null);
        System.out.println("application type: " + invoke);


        System.out.println("3.添加application context 初始化器，作用是在application context refresh之前，扩展application context");
        springApplication.addInitializers(new ApplicationContextInitializer() {
            @Override
            public void initialize(ConfigurableApplicationContext applicationContext) {
                if (applicationContext instanceof GenericApplicationContext) {
                    ((GenericApplicationContext) applicationContext).registerBean("bean3", Bean3.class);
                }
            }
        });

        System.out.println("4.添加监听器");
        // springboot在启动和运行和停止的时候会发布一些事件，并对这些事件做出响应，其实spring framework就已经提供了事件监听机制；

        springApplication.addListeners(new ApplicationListener<ApplicationEvent>() {
            @Override
            public void onApplicationEvent(ApplicationEvent event) {
                System.out.println("event: " + event);
            }
        });

        System.out.println("5.主类推断");
        Method deduceMainApplicationClass = SpringApplication.class.getDeclaredMethod("deduceMainApplicationClass");
        deduceMainApplicationClass.setAccessible(true);
        Object invoke1 = deduceMainApplicationClass.invoke(springApplication);
        System.out.println("main class: "+invoke1);

        ConfigurableApplicationContext context = springApplication.run(args);
        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName + "--->" + context.getBeanFactory().getBeanDefinition(beanDefinitionName).getResourceDescription());
        }
        context.close();
    }

    static class Bean1 {
    }

    static class Bean2 {
    }

    static class Bean3 {
    }

    @Bean
    public Bean2 bean2() {
        return new Bean2();
    }

    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
        return new TomcatServletWebServerFactory();
    }
}
