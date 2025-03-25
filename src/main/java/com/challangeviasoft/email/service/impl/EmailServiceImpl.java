package com.challangeviasoft.email.service.impl;
import com.challangeviasoft.email.model.dto.EmailLogRequestDTO;
import com.challangeviasoft.email.service.interfaces.EmailService;
import com.challangeviasoft.email.service.emailadapter.EmailAdapter;
import com.challangeviasoft.email.model.dto.EmailRequestDTO;
import com.challangeviasoft.email.model.dto.ReturnMessage;
import com.challangeviasoft.email.model.dto.Status;
import com.challangeviasoft.email.service.emailadapterfactory.EmailAdapterFactory;
import com.challangeviasoft.email.service.util.EmailServiceConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.validation.Validator;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {


    private final EmailAdapterFactory adapterFactory;
    private final ObjectMapper objectMapper ;
    private final Validator validator;
    private final SendMail sendMail;
    private final EmailLogServiceImpl emailLogService;

    @Autowired
    public EmailServiceImpl(EmailAdapterFactory adapterFactory, ObjectMapper objectMapper, Validator validator, SendMail sendMail, EmailLogServiceImpl emailLogService){
        this.adapterFactory = adapterFactory;
        this.objectMapper = objectMapper;
        this.validator = validator;
        this.sendMail = sendMail;
        this.emailLogService = emailLogService;
    }


    @Transactional
    public ReturnMessage processEmail(EmailRequestDTO request) throws Exception {

        EmailAdapter<?> adapter = adapterFactory.getAdapter();

        EmailLogRequestDTO logRequest = mapToLog(request);

        Object targetDto = adapter.adapt(request);

        validateRequest(targetDto);

        sendMail.sendHtmlEmail(request, request.recipientEmail());

        emailLogService.logEmailEvent(logRequest);

        logToConsole(request);

        return new ReturnMessage(
                "Email processado com sucesso !",
                Status.SUCCESS
        );

    }

    private EmailLogRequestDTO mapToLog(EmailRequestDTO request){
        return new EmailLogRequestDTO(
                "SENT",
                request.recipientEmail(),
                request.senderEmail(),
                request.subject(),
                request.content()
        );
    }


    private void validateRequest(Object targetDto) {
        Set<ConstraintViolation<Object>> violations = validator.validate(targetDto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    private void logToConsole(Object dto) throws JsonProcessingException {
        log.info(EmailServiceConstants.EMAIL_AUDIT_SUCCESS, objectMapper.writeValueAsString(dto));
    }
}
