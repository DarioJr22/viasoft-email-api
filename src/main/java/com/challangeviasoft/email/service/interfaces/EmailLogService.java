package com.challangeviasoft.email.service.interfaces;

import com.challangeviasoft.email.model.dto.EmailLogRequestDTO;
import com.challangeviasoft.email.model.entity.EmailLog;

import java.util.List;

public interface EmailLogService {
    EmailLog logEmailEvent(EmailLogRequestDTO emailLog);
    List<EmailLog> getAllLogs(int page, int size);
   void deleteLog(String id);
 }
