package auto.weimin.autoconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.GenericApplicationContext;

public class TestAutoConfig {
    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();


        context.registerBean("config",Config.class);
        context.registerBean(ConfigurationClassPostProcessor.class);
        context.refresh();

        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
    }

    static class Bean1 {
    }

    static class Bean2 {
    }

    @Configuration
    @Import(Bean2.class)
    static class Config{
        @Bean
        public Bean1 bean1() {
            return new Bean1();
        }
    }
}
