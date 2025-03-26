package com.challangeviasoft.email.service.impl;

import com.challangeviasoft.email.model.dto.EmailRequestDTO;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;
import org.thymeleaf.TemplateEngine;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import org.thymeleaf.context.Context;

@ExtendWith(MockitoExtension.class)
public class SendMailTest {

    @Mock
    private JavaMailSender emailSender;

    @Mock
    private TemplateEngine templateEngine;

    @Mock
    private MimeMessage mimeMessage;

    @Mock
    private EmailRequestDTO request;

    @InjectMocks
    private SendMail sendMail;



    @BeforeEach
    void setUp() {
        this.request = new EmailRequestDTO(
                "dariojr1233@gmail.com",
                "João Silva",
                "sender@example.com",
                "Assunto do E-mail",
                "Conteúdo do e-mail"
        );
        ReflectionTestUtils.setField(sendMail, "fromEmail", "dev.dario.rocha@gmail.com");
    }

    @Test
    void testSendHtmlEmail_Success() throws Exception {
        // Arrange
        String to = request.recipientEmail();
        String htmlTemplate = "<html>Conteúdo HTML gerado</html>";

        // Configura o TemplateEngine para retornar HTML mockado
        when(templateEngine.process(eq(SendMail.EMAIL_TEMPLATE), any(Context.class)))
                .thenReturn(htmlTemplate);

        // Mock do MimeMessage e JavaMailSender
        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);

        // Act
        sendMail.sendHtmlEmail(request, to);

        // Assert
        verify(templateEngine, times(1))
                .process(eq(SendMail.EMAIL_TEMPLATE), any(Context.class));
        verify(emailSender, times(1)).send(mimeMessage);
    }

    @Test
    void testSendHtmlEmail_ContextVariables() throws Exception {
        // Arrange
        ArgumentCaptor<Context> contextCaptor = ArgumentCaptor.forClass(Context.class);

        when(templateEngine.process(anyString(), contextCaptor.capture()))
                .thenReturn("<html></html>");
        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);

        // Act
        sendMail.sendHtmlEmail(request, "destinatario@empresa.com");

        // Assert
        Context context = contextCaptor.getValue();
        assertEquals(request.recipientEmail(), context.getVariable("recipientEmail"));
        assertEquals(request.recipientName(), context.getVariable("recipientName"));
        assertEquals(request.senderEmail(), context.getVariable("senderEmail"));
        assertEquals(request.subject(), context.getVariable("subject"));
        assertEquals(request.content(), context.getVariable("content"));
    }


    @Test
    void testSendHtmlEmail_ThrowsException() {
        // Arrange
        when(emailSender.createMimeMessage()).thenThrow(new RuntimeException("Erro simulado"));

        // Act & Assert
        assertThrows(RuntimeException.class, () ->
                sendMail.sendHtmlEmail(request, "destinatario@empresa.com")
        );
    }



}
