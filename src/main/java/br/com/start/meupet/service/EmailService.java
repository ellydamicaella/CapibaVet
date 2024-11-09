package br.com.start.meupet.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String remetente;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmailText(String destiny, String about, String message) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(remetente);
            simpleMailMessage.setTo(destiny);
            simpleMailMessage.setSubject(about);
            simpleMailMessage.setText(message);
            javaMailSender.send(simpleMailMessage);
            log.info("Email enviado de {} para {}", this.remetente, destiny);
        } catch (Exception e) {
            log.error("Falha ao enviar o email para {}", destiny);
        }

    }

}
