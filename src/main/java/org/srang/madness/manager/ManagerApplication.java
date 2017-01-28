package org.srang.madness.manager;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import lombok.extern.java.Log;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableAutoConfiguration
@Log
public class ManagerApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }


    @Bean
    ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean servlet = new ServletRegistrationBean(
                new CamelHttpTransportServlet(), "/api/*", "/v2");
        servlet.setName("CamelServlet");
        return servlet;
    }
    @Bean
    JacksonJsonProvider jsonProvider() {
        return new JacksonJsonProvider();
    }

//    @Bean
//    public CommandLineRunner demo(BracketService service) {
//                return (args) -> {
//                    log.warning(service.generateMatchups().toString());
//                };
//            }
}
