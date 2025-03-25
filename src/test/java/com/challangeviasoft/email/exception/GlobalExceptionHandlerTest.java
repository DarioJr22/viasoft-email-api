package com.challangeviasoft.email.exception;

// ... imports necessários ...

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.challangeviasoft.email.model.dto.ReturnMessage;
import com.challangeviasoft.email.model.dto.Status;
import com.challangeviasoft.email.service.util.EmailServiceConstants;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    private final HttpServletRequest request = mock(HttpServletRequest.class);

    @BeforeEach
    void setup() {
        when(request.getRequestURI()).thenReturn("/api/endpoint");
    }

    // ========== Testes para MethodArgumentNotValidException ==========
    @Test
    void handleValidation_ReturnsBadRequestWithDetails() {
        // Simula exceção de validação
        FieldError fieldError = new FieldError("object", "field", "Campo obrigatório");
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(
                null,
                new BindException(new Object(), "object")
        );
        ex.getBindingResult().addError(fieldError);

        // Executa o handler
        ResponseEntity<StandardError> response = handler.handleValidation(ex, request);

        // Verificações
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        StandardError error = response.getBody();
        assertNotNull(error);
        assertEquals("Campo obrigatório", error.getMessage().message());
        assertEquals(EmailServiceConstants.VALIDATION_ERROR, error.getError());
    }

    // ========== Testes para IllegalArgumentException ==========
    @Test
    void handleIllegalArgument_ReturnsBadRequestWithMessage() {
        IllegalArgumentException ex = new IllegalArgumentException("Valor inválido");

        ResponseEntity<StandardError> response = handler.handleIllegalArgument(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Valor inválido", response.getBody().getMessage().message());
    }

    // ========== Testes para RuntimeException ==========
    @Test
    void handleRuntimeException_ReturnsInternalServerError() {
        RuntimeException ex = new RuntimeException("Erro inesperado");

        ResponseEntity<StandardError> response = handler.handleRuntime(ex, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(EmailServiceConstants.RUNTIME_EXCEPTION, response.getBody().getError());
    }

    // ========== Testes para Exception genérica ==========
    @Test
    void handleGenericException_ReturnsInternalServerError() {
        Exception ex = new Exception("Erro genérico");

        ResponseEntity<StandardError> response = handler.handleGeneric(ex, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(EmailServiceConstants.INTERNAL_SERVER_ERROR, response.getBody().getError());
    }

    // ========== Testes para MethodArgumentTypeMismatchException ==========
    @Test
    void handleTypeMismatch_ReturnsFormattedMessage() {
        MethodArgumentTypeMismatchException ex = new MethodArgumentTypeMismatchException(
                "abc",
                Integer.class,
                "age",
                null,
                null
        );

        ResponseEntity<StandardError> response = handler.handleMethodArgumentTypeMismatch(ex, request);

        String expectedMessage = String.format(
                EmailServiceConstants.ARGUMENT_TYPE_MISMATCH,
                "age",
                "abc",
                "Integer"
        );

        assertEquals(expectedMessage, response.getBody().getMessage().message());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}