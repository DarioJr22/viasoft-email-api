package com.challangeviasoft.email.exception;

import com.challangeviasoft.email.model.dto.ReturnMessage;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class StandardError {
    private final LocalDateTime timestamp;
    private final Integer status;
    private final String error;
    private final ReturnMessage message;
    private final String path;
}