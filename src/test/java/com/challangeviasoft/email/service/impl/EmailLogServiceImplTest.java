package com.challangeviasoft.email.service.impl;

// ... imports necessários ...

import com.challangeviasoft.email.exception.handler.LogNotFoundException;
import com.challangeviasoft.email.model.dto.EmailLogRequestDTO;
import com.challangeviasoft.email.model.entity.EmailLog;
import com.challangeviasoft.email.repository.EmailLogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class EmailLogServiceImplTest {

    @Mock
    private EmailLogRepository emailLogRepository;

    @InjectMocks
    private EmailLogServiceImpl emailLogService;

    // Dados de teste reutilizáveis
    private final EmailLogRequestDTO sampleRequest = new EmailLogRequestDTO(
            "SENT",
            "user@test.com",
            "sender@test.com",
            "Test Subject",
            "Test Content"
    );

    private final EmailLog mockLog = new EmailLog(
            "12345",
            "SENT",
            "user@test.com",
            "sender@test.com",
            "Test Subject",
            "Test Content"
    );

    // ========== Testes para logEmailEvent() ==========
    @Test
    void testLogEmailEvent_Success() {
        // Arrange
        when(emailLogRepository.save(any(EmailLog.class))).thenReturn(mockLog);

        // Act
        EmailLog result = emailLogService.logEmailEvent(sampleRequest);

        // Assert
        assertNotNull(result);
        assertEquals("12345", result.getId());
        verify(emailLogRepository, times(1)).save(any(EmailLog.class));
    }

    @Test
    void testLogEmailEvent_EntityConversion() {
        // Arrange
        ArgumentCaptor<EmailLog> captor = ArgumentCaptor.forClass(EmailLog.class);
        when(emailLogRepository.save(captor.capture())).thenReturn(mockLog);

        // Act
        emailLogService.logEmailEvent(sampleRequest);

        // Assert
        EmailLog savedLog = captor.getValue();
        assertNotNull(savedLog.getId());
        assertEquals(sampleRequest.eventType(), savedLog.getEventType());
        assertEquals(sampleRequest.recipientEmail(), savedLog.getRecipientEmail());
    }

    // ========== Testes para getAllLogs() ==========
    @Test
    void testGetAllLogs_Pagination() {
        // Arrange
        List<EmailLog> mockLogs = List.of(
                new EmailLog("1", "SENT", "a@test.com", "b@test.com", "Subj1", "Cont1"),
                new EmailLog("2", "FAILED", "c@test.com", "d@test.com", "Subj2", "Cont2"),
                new EmailLog("3", "SENT", "e@test.com", "f@test.com", "Subj3", "Cont3")
        );

        when(emailLogRepository.findAll()).thenReturn(mockLogs);

        // Act
        List<EmailLog> result = emailLogService.getAllLogs(0, 2);

        // Assert
        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("2", result.get(1).getId());
    }



    // ========== Testes para deleteLog() ==========
    @Test
    void testDeleteLog_Success() {
        // Arrange
        String testId = "12345";
        when(emailLogRepository.existsById(testId)).thenReturn(true);

        // Act
        emailLogService.deleteLog(testId);

        // Assert
        verify(emailLogRepository, times(1)).deleteById(testId);
    }

    @Test
    void testDeleteLog_NotFound() {
        // Arrange
        String testId = "99999";
        when(emailLogRepository.existsById(testId)).thenReturn(false);

        // Act & Assert
        assertThrows(LogNotFoundException.class, () ->
                emailLogService.deleteLog(testId)
        );
        verify(emailLogRepository, never()).deleteById(anyString());
    }
}