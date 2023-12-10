package top.scopetest.scope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class E {

    @Autowired
    @Lazy
    private F f;

    public F getF() {
        return f;
    }

    public void setF(F f) {
        this.f = f;
    }


    @Autowired
    private F1 f1;

    public F1 getF1() {
        return f1;
    }

    public void setF1(F1 f1) {
        this.f1 = f1;
    }

    @Autowired
    private ObjectFactory<F2> f2;

    public F2 getF2() {
        return f2.getObject();
    }



    @Autowired
    private ApplicationContext applicationContext;

    public F3 getF3() {
        return applicationContext.getBean(F3.class);
    }
}
