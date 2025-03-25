package com.challangeviasoft.email.service.emailadapter;

import com.challangeviasoft.email.model.dto.EmailRequestDTO;

public interface EmailAdapter<T> {
    T adapt(EmailRequestDTO request);
}
