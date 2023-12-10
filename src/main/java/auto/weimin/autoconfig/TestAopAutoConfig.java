package auto.weimin.autoconfig;

import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.type.AnnotationMetadata;

public class TestAopAutoConfig {
    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();

        AnnotationConfigUtils.registerAnnotationConfigProcessors(context.getDefaultListableBeanFactory());
        context.registerBean("config", Config.class);
        context.refresh();

        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }

        /*
        *
        *
        * org.springframework.boot.autoconfigure.aop.AopAutoConfiguration$ClassProxyingConfiguration
forceAutoProxyCreatorToUseClassProxying
org.springframework.boot.autoconfigure.aop.AopAutoConfiguration
org.springframework.aop.config.internalAutoProxyCreator
        *
        * */
    }

    @Configuration
    @Import(MyImportSelector.class)
    static class Config{
    }

    static class MyImportSelector implements DeferredImportSelector {
        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {

            return new String[]{AopAutoConfiguration.class.getName()};
        }
    }
}
