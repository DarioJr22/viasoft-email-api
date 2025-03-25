package com.challangeviasoft.email.service.impl;

import com.challangeviasoft.email.exception.handler.LogNotFoundException;
import com.challangeviasoft.email.model.dto.EmailLogRequestDTO;
import com.challangeviasoft.email.model.entity.EmailLog;
import com.challangeviasoft.email.repository.EmailLogRepository;
import com.challangeviasoft.email.service.interfaces.EmailLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmailLogServiceImpl implements EmailLogService {

    private final EmailLogRepository emailLogRepository;

    @Autowired
    public EmailLogServiceImpl(
            EmailLogRepository emailLogRepository
    ) {
        this.emailLogRepository = emailLogRepository;
    }


    @Override
    public EmailLog logEmailEvent(EmailLogRequestDTO request) {
        EmailLog log = convertToEntity(request);
        return emailLogRepository.save(log);
    }



    @Override
    public List<EmailLog> getAllLogs(int page, int size) {
        List<EmailLog> allLogs = (List<EmailLog>) emailLogRepository.findAll();
        int start = page * size;
        int end = Math.min(start + size, allLogs.size());
        return allLogs.subList(start, end);
    }


    @Override
    public void deleteLog(String id) {
        if (!emailLogRepository.existsById(id)) {
            throw new LogNotFoundException("Log não encontrado com ID: " + id);
        }
        emailLogRepository.deleteById(id);
    }


    private EmailLog convertToEntity(EmailLogRequestDTO request) {
        return new EmailLog(
                UUID.randomUUID().toString(),
                request.eventType(),
                request.recipientEmail(),
                request.senderEmail(),
                request.subject(),
                request.content()
        );
    }
}
