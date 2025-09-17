package io.github.tiagoiwamoto.apppersonalresumebackend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
@Slf4j
public class App {

    public static void main(String[] args) {
        var context = SpringApplication.run(App.class, args);
        var appName = context.getEnvironment().getProperty("spring.application.name");
        log.info("Aplicação {} iniciada com sucesso.", appName);
    }

}
