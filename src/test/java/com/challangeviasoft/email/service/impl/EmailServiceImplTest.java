package com.challangeviasoft.email.service.impl;
import com.challangeviasoft.email.model.dto.EmailLogRequestDTO;
import com.challangeviasoft.email.model.dto.EmailRequestDTO;
import com.challangeviasoft.email.model.dto.ReturnMessage;
import com.challangeviasoft.email.model.dto.Status;
import com.challangeviasoft.email.service.emailadapter.EmailAdapter;
import com.challangeviasoft.email.service.emailadapterfactory.EmailAdapterFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class EmailServiceImplTest {

    @Mock
    private EmailAdapterFactory adapterFactory;

    @Mock
    private EmailAdapter<Object> emailAdapter; // Usando wildcard <?>

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private Validator validator;

    @Mock
    private SendMail sendMail;

    @Mock
    private EmailLogServiceImpl emailLogService;

    @InjectMocks
    private EmailServiceImpl emailService;

    private final EmailRequestDTO request = new EmailRequestDTO(
            "user@test.com",
            "Maria Silva",
            "sender@test.com",
            "Assunto Teste",
            "Conteúdo Teste"
    );

    @BeforeEach
    void setup() {
        when(adapterFactory.getAdapter()).thenReturn((EmailAdapter) emailAdapter);
        when(emailAdapter.adapt(any(EmailRequestDTO.class))) // Fechamento correto do any()
                .thenReturn(new Object()); // Retorna um objeto genérico
    }

    @Test
    void testProcessEmail_Success() throws Exception {
        // Arrange
        when(validator.validate(any())).thenReturn(Set.of());
        when(objectMapper.writeValueAsString(any())).thenReturn("{}");

        // Act
        ReturnMessage result = emailService.processEmail(request);

        // Assert
        assertEquals(Status.SUCCESS, result.status());
        assertEquals("Email processado com sucesso !", result.message());

        verify(sendMail).sendHtmlEmail(eq(request), eq(request.recipientEmail()));

        ArgumentCaptor<EmailLogRequestDTO> logCaptor = ArgumentCaptor.forClass(EmailLogRequestDTO.class);
        verify(emailLogService).logEmailEvent(logCaptor.capture());

        EmailLogRequestDTO logRequest = logCaptor.getValue();
        assertEquals("SENT", logRequest.eventType());
        assertEquals(request.recipientEmail(), logRequest.recipientEmail());
    }

    @Test
    void testProcessEmail_ValidationFailure() {
        // Arrange
        ConstraintViolation<Object> violation = mock(ConstraintViolation.class);
        when(validator.validate(any())).thenReturn(Set.of(violation));

        // Act & Assert
        assertThrows(ConstraintViolationException.class, () ->
                emailService.processEmail(request)
        );

        verify(sendMail, never()).sendHtmlEmail(any(), anyString());
        verify(emailLogService, never()).logEmailEvent(any());
    }


}