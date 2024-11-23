package br.com.start.meupet.common.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jakarta.mail.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    private String cachedConfirmAccountTemplate;

    private String cachedPasswordRecoveryTemplate;

    private final ExecutorService executor = Executors.newCachedThreadPool();

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmailConfirmAccountTemplate(String destiny, String about, String name, String token) {
        executor.submit(() -> {
            try {
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = createMessage(destiny, about, message);

                String template = loadEmailConfirmAccountTemplate(name, token);
                helper.setText(template, true);

                javaMailSender.send(message);
                log.info("Email enviado de {} para {}", from, destiny);
            } catch (MessagingException | IOException | MailException e) {
                log.error("Falha ao enviar o email para {}", destiny, e);
            }
        });
    }

    public void sendEmailPasswordRecoveryTemplate(String destiny, String about, String name, String token) {
        executor.submit(() -> {
            try {
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = createMessage(destiny, about, message);
                String template = loadEmailPasswordRecoveryTemplate(name, token);

                helper.setText(template, true);
                javaMailSender.send(message);

                log.info("Email enviado de {} para {}", from, destiny);
            } catch (MessagingException | IOException | MailException e) {
                log.error("Falha ao enviar o email para {}", destiny, e);
            }
        });
    }

    private String loadEmailConfirmAccountTemplate(String name, String token) throws IOException {
        if (cachedConfirmAccountTemplate == null) {
            ClassPathResource classPathResource = new ClassPathResource("templates/emailConfirmAccountTemplate.html");
            cachedConfirmAccountTemplate = new String(classPathResource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        }
        return cachedConfirmAccountTemplate.replace("{{NOME_DO_USUARIO}}", name)
                .replace("{{token}}", token);
    }

    private String loadEmailPasswordRecoveryTemplate(String name, String token) throws IOException {
        if (cachedPasswordRecoveryTemplate == null) {
            ClassPathResource classPathResource = new ClassPathResource("templates/emailPasswordRecoveryTemplate.html");
            cachedPasswordRecoveryTemplate = new String(classPathResource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        }
        return cachedPasswordRecoveryTemplate.replace("{{NOME_DO_USUARIO}}", name)
                .replace("{{token}}", token);
    }


    public MimeMessageHelper createMessage(String destiny, String about, MimeMessage message) throws MessagingException, IOException {
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(from);
        helper.setTo(destiny);
        helper.setSubject(about);
        return helper;
    }
}
