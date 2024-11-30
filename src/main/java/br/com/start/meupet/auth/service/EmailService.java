package br.com.start.meupet.auth.services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import br.com.start.meupet.common.templates.TemplateNameEnum;
import jakarta.annotation.PreDestroy;
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

    // Cache para armazenar templates carregados
    private final Map<String, String> templateCache = new ConcurrentHashMap<>();

    private final ExecutorService executor = Executors.newCachedThreadPool();

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * Envia um e-mail baseado em um template.
     *
     * @param destiny     Destinatário do e-mail.
     * @param about       Assunto do e-mail.
     * @param templateNameEnum Nome do arquivo de template.
     */
    public void sendEmailTemplate(String destiny, String about, TemplateNameEnum templateNameEnum, String name, String token) {
        executor.submit(() -> {
            try {
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = createMessage(destiny, about, message);

                String template = loadEmailTemplate(templateNameEnum, name, token);
                helper.setText(template, true);

                javaMailSender.send(message);
                log.info("Email enviado de {} para {}", from, destiny);
            } catch (MessagingException | IOException | MailException e) {
                log.error("Falha ao enviar o email para {}", destiny, e);
            }
        });
    }

    /**
     * Carrega um template de e-mail e substitui os placeholders.
     *
     * @param templateNameEnum Nome do arquivo de template.
     * @return String contendo o conteúdo final do template com os valores substituídos.
     * @throws IOException Em caso de erro ao carregar o template.
     */
    private String loadEmailTemplate(TemplateNameEnum templateNameEnum, String name, String token) throws IOException {
        // Verifica se o template já está no cache
        String template = templateCache.computeIfAbsent(templateNameEnum.getFileName(), this::loadTemplateFromFile);

        return template.replace("{{NOME_DO_USUARIO}}", name)
                .replace("{{token}}", token);
    }

    /**
     * Lê o conteúdo do arquivo do template.
     *
     * @param templateName Nome do arquivo de template.
     * @return O conteúdo do template.
     */
    private String loadTemplateFromFile(String templateName) {
        try {
            ClassPathResource classPathResource = new ClassPathResource("templates/" + templateName);
            return new String(classPathResource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Erro ao carregar o template: {}", templateName, e);
            throw new RuntimeException("Não foi possível carregar o template: " + templateName, e);
        }
    }


    /**
     * Cria a mensagem base do e-mail.
     */
    private MimeMessageHelper createMessage(String destiny, String about, MimeMessage message) throws MessagingException {
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(from);
        helper.setTo(destiny);
        helper.setSubject(about);
        return helper;
    }

    /**
     * Finaliza o executor service durante o encerramento da aplicação.
     */
    @PreDestroy
    public void shutdownExecutor() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
