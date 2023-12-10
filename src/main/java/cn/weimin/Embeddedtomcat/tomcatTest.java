package cn.weimin.Embeddedtomcat;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.http11.Http11Nio2Protocol;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class tomcatTest {
    public static void main(String[] args) throws IOException, LifecycleException {
        // 1.创建tomcat 对象
        Tomcat tomcat = new Tomcat();
        // 设置BaseDir，存放tomcat运行过程中生成的文件
        tomcat.setBaseDir("tomcat");

        //2.创建项目文件夹，docBase
        File docBase = Files.createTempDirectory("boot.").toFile();
        docBase.deleteOnExit();

        // 3.创建tomcat项目，也就是tomcat的context
        Context context = tomcat.addContext("", docBase.getAbsolutePath());

        // tomcat和spring整合
        WebApplicationContext applicationContext = getApplicationContext();

        // 4.添加servlet
        context.addServletContainerInitializer(new ServletContainerInitializer() {
            @Override
            public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
                HelloServlet helloServlet = new HelloServlet();
                servletContext.addServlet("hello", helloServlet).addMapping("/hello");

//                DispatcherServlet dispatcherServlet = applicationContext.getBean(DispatcherServlet.class);
//                //
//                servletContext.addServlet("dispatcherServlet", dispatcherServlet).addMapping("/");

                for (ServletRegistrationBean registrationBean : applicationContext.getBeansOfType(ServletRegistrationBean.class).values()) {
                    registrationBean.onStartup(servletContext);
                }
            }
        }, new HashSet<>());

        // 5.启动
        tomcat.start();

        // 6.创建连接器，监听端口

        Connector connector = new Connector(new Http11Nio2Protocol());
        connector.setPort(8080);

        tomcat.setConnector(connector);
    }

    static class HelloServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().print("<h3>hello</h3>");
        }
    }

    public static WebApplicationContext getApplicationContext() {
        // 这里不用AnnotationConfigServletWebServerApplicationContext，因为它内置了tomcat
        // 我们自己实现内置tomcat
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(Config.class);
        applicationContext.refresh();
        return applicationContext;
    }

    @Configuration
    static class Config {

        @Bean
        public DispatcherServlet dispatcherServlet() {
            return new DispatcherServlet();
        }

        @Bean
        public DispatcherServletRegistrationBean dispatcherServletRegistrationBean(DispatcherServlet dispatcherServlet) {
            DispatcherServletRegistrationBean registrationBean = new DispatcherServletRegistrationBean(dispatcherServlet, "/");
            registrationBean.setLoadOnStartup(1);
            return registrationBean;
        }

        @Bean
        public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
            RequestMappingHandlerAdapter requestMappingHandlerAdapter = new RequestMappingHandlerAdapter();
            requestMappingHandlerAdapter.setMessageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter()));
            return requestMappingHandlerAdapter;
        }

        @RestController
        static class MyController {
            @GetMapping("hello2")
            public Map<String, Object> hello() {
                Map<String, Object> map = new HashMap<>();
                map.put("hello2", "hello world");
                return map;
            }
        }
    }
}
