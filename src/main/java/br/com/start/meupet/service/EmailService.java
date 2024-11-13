package br.com.start.meupet.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String remetente;

    private String cachedTemplate;

    private final ExecutorService executor = Executors.newCachedThreadPool();

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmailTemplate(String destiny, String about, String name, String uuid) {
        executor.submit(() -> {
            try {
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
                helper.setFrom(remetente);
                helper.setTo(destiny);
                helper.setSubject(about);

                String template = loadEmailTemplate(name, uuid);
                helper.setText(template, true);

                javaMailSender.send(message);
                log.info("Email enviado de {} para {}", remetente, destiny);
            } catch (Exception e) {
                log.error("Falha ao enviar o email para {}", destiny, e);
            }
        });
    }

    private String loadEmailTemplate(String name, String uuid) throws IOException {
        if (cachedTemplate == null) {
            ClassPathResource classPathResource = new ClassPathResource("templates/emailTemplate.html");
            cachedTemplate = new String(classPathResource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        }

        return cachedTemplate.replace("{{NOME_DO_USUARIO}}", name)
                .replace("{{uuid}}", uuid);
    }


}
