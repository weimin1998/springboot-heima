package com.example.springbootheima;

import org.springframework.boot.DefaultBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.lang.reflect.Constructor;
import java.time.Duration;
import java.util.List;

public class Run {
    public static void main(String[] args) throws Exception {
        SpringApplication springApplication = new SpringApplication();

        springApplication.addListeners(event -> System.out.println(event.getClass()));

        List<String> list = SpringFactoriesLoader.loadFactoryNames(SpringApplicationRunListener.class, Run.class.getClassLoader());
        for (String name : list) {
            System.out.println(name);

            Class<?> clazz = Class.forName(name);
            Constructor<?> constructor = clazz.getConstructor(SpringApplication.class, String[].class);
            SpringApplicationRunListener publisher = (SpringApplicationRunListener) constructor.newInstance(springApplication, args);


            DefaultBootstrapContext defaultBootstrapContext = new DefaultBootstrapContext();

            // 发布事件
            publisher.starting(defaultBootstrapContext); // springboot 开始启动

            publisher.environmentPrepared(defaultBootstrapContext,new StandardEnvironment()); // 环境信息准备完毕

            GenericApplicationContext context = new GenericApplicationContext();
            publisher.contextPrepared(context); // spring容器实例化完成，并且执行完所有的初始化器

            publisher.contextLoaded(context); // 所有bean definition加载完毕

            context.refresh();
            publisher.started(context,Duration.ZERO); // spring容器初始化完成，refresh方法执行完毕

            publisher.ready(context,Duration.ZERO);// spring boot启动完成

            publisher.failed(context, new Exception("something occurs")); // springboot 启动失败
        }



    }
}
