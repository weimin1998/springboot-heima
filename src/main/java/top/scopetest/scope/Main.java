package top.scopetest.scope;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Main.class, args);


        E bean = applicationContext.getBean(E.class);

//        System.out.println(bean.getF().getClass());
//        System.out.println(bean.getF());
//        System.out.println(bean.getF());
//        System.out.println(bean.getF());

//        System.out.println(bean.getF1().getClass());
//        System.out.println(bean.getF1());
//        System.out.println(bean.getF1());
//        System.out.println(bean.getF1());
//
//        System.out.println(bean.getF2().getClass());
//        System.out.println(bean.getF2());
//        System.out.println(bean.getF2());
//        System.out.println(bean.getF2());
//
        System.out.println(bean.getF3().getClass());
        System.out.println(bean.getF3());
        System.out.println(bean.getF3());
        System.out.println(bean.getF3());

        applicationContext.close();
    }
}
