package com.msv.boot;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(scanBasePackages = "com.msv")
public class BootApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(BootApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

}
