package com.nerdery.snafoo;


import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


import org.slf4j.Logger;


/**
 * Configuration object for the application's root context.
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class SnackFoodApplication implements EmbeddedServletContainerCustomizer {


    private static final Logger log = LoggerFactory.getLogger(SnackFoodApplication.class);


    /**
     * Add support for serving JSON data with the proper MIME type to the servlet container. This is used for fixture demo data and can be
     * safely removed if you won't be using any JSON fixture data in your NAT submission.
     *
     * @param container The embedded Spring Boot Servlet container
     */
    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
        mappings.add("json","application/json");
        container.setMimeMappings(mappings);
    }




    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SnackFoodApplication.class, args);
    }
}
