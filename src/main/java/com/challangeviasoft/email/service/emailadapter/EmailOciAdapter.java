package com.challangeviasoft.email.service.emailadapter;

import com.challangeviasoft.email.model.dto.EmailOciDTO;
import com.challangeviasoft.email.model.dto.EmailRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class EmailOciAdapter implements EmailAdapter<EmailOciDTO> {

    @Override
    public EmailOciDTO adapt(EmailRequestDTO request){
      return new EmailOciDTO(
              request.recipientEmail(),
              request.recipientName(),
              request.senderEmail(),
              request.subject(),
              request.content()
      );
    };

}
