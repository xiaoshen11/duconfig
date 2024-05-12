package com.bruce.duconfig.demo;

import com.bruce.duconfig.client.annotation.EnableDuConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@SpringBootApplication
@EnableConfigurationProperties({DuDemoConfig.class})
@EnableDuConfig
@RestController
class DuconfigDemoApplication {

    @Value("${du.a}")
    private String a;

    @Value("${du.b}")
    private String b;

    @Autowired
    private DuDemoConfig demoConfig;

    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(DuconfigDemoApplication.class, args);
    }

    @GetMapping("/demo")
    public String demo(){
        return "du.a = " + a + "\n"
                + "du.b = " + b + "\n"
                + "demo.a = " + demoConfig.getA() + "\n"
                + "demo.b = " + demoConfig.getB();
    }

    @Bean
    ApplicationRunner applicationRunner(){
        System.out.println(Arrays.toString(environment.getActiveProfiles()));
        return args -> {
            System.out.println(a);
            System.out.println(demoConfig.getA()    );
        };
    }

}

