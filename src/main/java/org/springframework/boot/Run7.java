package org.springframework.boot;

import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Run7 {
    // 第7步
    // 打印图标
    public static void main(String[] args) throws IOException {
        SpringApplication springApplication = new SpringApplication();
        ApplicationEnvironment applicationEnvironment = new ApplicationEnvironment();

        Map<String,Object> map = new HashMap<>();
        map.put("spring.banner.location","banner1.txt");
        //map.put("spring.banner.image.location","banner1.png");
        applicationEnvironment.getPropertySources().addLast(new MapPropertySource("custom",map));



        SpringApplicationBannerPrinter springApplicationBannerPrinter = new SpringApplicationBannerPrinter(new DefaultResourceLoader(), new SpringBootBanner());
        springApplicationBannerPrinter.print(applicationEnvironment,Run7.class,System.out);
        System.out.println(SpringBootVersion.getVersion());

    }
}
