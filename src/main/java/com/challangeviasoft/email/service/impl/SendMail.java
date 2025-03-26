package com.challangeviasoft.email.service.impl;

import com.challangeviasoft.email.config.MailConfig;
import com.challangeviasoft.email.model.dto.EmailRequestDTO;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class SendMail {
    public static final String NEW_CONTACT_CLIENT = "Um cliente entrou em contato pelo site!";
    public static final String UTF_8_ENCODING = "UTF-8";
    public static final String EMAIL_TEMPLATE = "emailtemplate";

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private TemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String fromEmail;
    @Value("${mail.integracao}")
    private String integrationSource;

    private MimeMessage getMimeMessage() {
        return emailSender.createMimeMessage();
    }

    @Async
    public void sendHtmlEmail(EmailRequestDTO request, String to) {
        try {

            Context context = new Context();
            context.setVariable("recipientEmail", request.recipientEmail());
            context.setVariable("recipientName", request.recipientName());
            context.setVariable("senderEmail", request.senderEmail());
            context.setVariable("subject", request.subject());
            context.setVariable("content", request.content());
            context.setVariable("integrationSource",integrationSource.toUpperCase());
            String text = templateEngine.process(EMAIL_TEMPLATE, context);
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject(NEW_CONTACT_CLIENT);
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setText(text, true);
            emailSender.send(message);
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }
}
