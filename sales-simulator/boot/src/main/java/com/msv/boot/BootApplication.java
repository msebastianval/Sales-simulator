package com.msv.boot;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.msv")
@EntityScan("com.msv.infrastructure.entity")
@EnableJpaRepositories("com.msv.infrastructure.repository")
public class BootApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(BootApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
