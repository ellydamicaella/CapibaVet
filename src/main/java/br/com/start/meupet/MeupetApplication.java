package br.com.start.meupet;

import br.com.start.meupet.domain.entities.User;
import br.com.start.meupet.domain.factory.UserFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class MeupetApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MeupetApplication.class, args);
        Integer port = context.getEnvironment().getProperty("server.port", Integer.class);

        System.out.println("Servidor iniciado na porta: " + port);

        Map<String, Object> teste = new HashMap<>();

        teste.put("id", 1L);
        teste.put("nome_completo", "Thiago Andrade");
        teste.put("email", "thiago@example.com"); // Exemplo de String
        teste.put("senha", "senhaSecreta"); // Exemplo de String
        teste.put("telefone", "(55) 81 98599-6672"); // Exemplo de String

        User usuario = UserFactory.create(teste);

        System.out.println("ID: " + usuario.getId());
        System.out.println("Nome: " + usuario.getName());
        //System.out.println("Email: " + usuario.getEmail());
        //System.out.println("Telefone: " + usuario.getCellPhoneNumber());
        //System.out.println("createdAt: " + usuario.getCreatedAt());

    }

}
