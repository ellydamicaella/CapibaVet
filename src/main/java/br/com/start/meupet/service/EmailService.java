package br.com.start.meupet.service;

import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String remetente;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmailTemplate(String destiny, String about, String name, String uuid) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(remetente);
            helper.setTo(destiny);
            helper.setSubject(about);

            String template = loadEmailTemplate(uuid, name);

            helper.setText(template, true);

            javaMailSender.send(message);

            log.info("Email enviado de {} para {}", this.remetente, destiny);
        } catch (Exception e) {
            log.error("Falha ao enviar o email para {}", destiny);
        }
    }

    public String loadEmailTemplate(String uuid, String name) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("templates/emailTemplate.html");
        String template = new String(classPathResource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        template = template.replace("{{NOME_DO_USUARIO}}", name);
        template = template.replace("{{uuid}}", uuid);
        return template;
    }

}
