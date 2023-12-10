package org.springframework.boot;

import org.springframework.boot.context.event.EventPublishingRunListener;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.EnvironmentPostProcessorApplicationListener;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.io.IOException;
import java.util.List;

public class Run6 {
    // 第6步
    // bind spring.main前缀的key value到springApplication
    // debug查看
    public static void main(String[] args) throws IOException {
        SpringApplication springApplication = new SpringApplication();
        ApplicationEnvironment applicationEnvironment = new ApplicationEnvironment();

        applicationEnvironment.getPropertySources().addLast(new ResourcePropertySource("step6",new ClassPathResource("step6.properties")));

        //applicationEnvironment.getPropertySources().addLast(new ResourcePropertySource("step4",new ClassPathResource("step4.properties")));
//        User user = Binder.get(applicationEnvironment).bind("user", User.class).get();
//        System.out.println(user);
//
//        User user1 = new User();
//        Binder.get(applicationEnvironment).bind("user", Bindable.ofInstance(user1)).get();
//        System.out.println(user1);

        System.out.println(springApplication);
        Binder.get(applicationEnvironment).bind("spring.main",Bindable.ofInstance(springApplication));
        System.out.println(springApplication);
    }

    static class User{
        private String firstName;
        private String middleName;
        private String lastName;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getMiddleName() {
            return middleName;
        }

        public void setMiddleName(String middleName) {
            this.middleName = middleName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        @Override
        public String toString() {
            return "User{" +
                    "firstName='" + firstName + '\'' +
                    ", middleName='" + middleName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    '}';
        }
    }
}
