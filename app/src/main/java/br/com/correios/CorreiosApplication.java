package br.com.correios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CorreiosApplication {

    private static ConfigurableApplicationContext applicationContext;
    public static void main(String[] args) {
        applicationContext = SpringApplication.run(CorreiosApplication.class, args);
    }

    public static void close(int code) {
        SpringApplication.exit(applicationContext, () -> code);
    }

}