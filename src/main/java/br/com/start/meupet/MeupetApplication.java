package br.com.start.meupet;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "MeuPet", version = "1", description = "Api desenvolvida para o funcionamento da plataforma MeuPet"))
public class MeupetApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MeupetApplication.class, args);
        Integer port = context.getEnvironment().getProperty("server.port", Integer.class, 8080);

        System.out.println("Servidor iniciado na porta: " + port);
    }
}
