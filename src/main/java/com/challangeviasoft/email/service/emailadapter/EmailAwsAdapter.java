package com.challangeviasoft.email.service.emailadapter;
import com.challangeviasoft.email.model.dto.EmailAwsDTO;
import com.challangeviasoft.email.model.dto.EmailRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class EmailAwsAdapter implements EmailAdapter<EmailAwsDTO>{
    @Override
    public EmailAwsDTO adapt(EmailRequestDTO request) {
        return new EmailAwsDTO(
                request.recipientEmail(),
                request.recipientName(),
                request.senderEmail(),
                request.subject(),
                request.content()
        ) ;
    }
}
