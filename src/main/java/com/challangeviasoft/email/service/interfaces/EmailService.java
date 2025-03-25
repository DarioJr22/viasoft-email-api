package com.challangeviasoft.email.service.interfaces;
import com.challangeviasoft.email.model.dto.EmailRequestDTO;
import com.challangeviasoft.email.model.dto.ReturnMessage;

public interface EmailService {

    ReturnMessage processEmail(EmailRequestDTO request)throws Exception;
}
