package com.hy.lang.mercury;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MercuryApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        new MercuryApplication()
                .configure(new SpringApplicationBuilder(MercuryApplication.class))
                .run(args);
    }

}
