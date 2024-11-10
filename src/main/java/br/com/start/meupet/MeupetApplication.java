package br.com.start.meupet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MeupetApplication {

    private final Logger log = LoggerFactory.getLogger(MeupetApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MeupetApplication.class, args);
        Integer port = context.getEnvironment().getProperty("server.port", Integer.class);

        System.out.println("Servidor iniciado na porta: " + port);
    }

}
