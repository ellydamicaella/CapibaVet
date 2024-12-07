package br.com.start.meupet;

import br.com.start.meupet.common.utils.DocumentValidator;
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
//        Integer port = context.getEnvironment().getProperty("server.port", Integer.class, 8080);
//
//        System.out.println("Servidor iniciado na porta: " + port);
        System.out.println(DocumentValidator.isValidCPF("123.456.789-09")); //true
        System.out.println(DocumentValidator.isValidCPF("12345678909")); //false

        System.out.println(DocumentValidator.isValidCNPJ("12.345.678/0001-95")); //true
        System.out.println(DocumentValidator.isValidCNPJ("12345678000195")); //false
    }
}
