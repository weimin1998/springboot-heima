package weimin.tx;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import weimin.tx.config.TxConfig;
import weimin.tx.service.AccountService;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(TxConfig.class);

        AccountService accountService1 = applicationContext.getBean(AccountService.class);

        System.out.println(accountService1.getClass());// class weimin.tx.service.AccountService$$EnhancerBySpringCGLIB$$b82adbc3

        accountService1.transfer();
    }
}
