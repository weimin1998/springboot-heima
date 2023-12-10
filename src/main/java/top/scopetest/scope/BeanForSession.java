package top.scopetest.scope;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
@Scope("session")
public class BeanForSession {

    @PreDestroy
    public void destroy(){
        System.out.println("destroy in BeanForSession");
    }
}
