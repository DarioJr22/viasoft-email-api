package com.challangeviasoft.email.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record EmailLogResponseDTO(
            String id,
   @NotBlank String eventType,
   @NotBlank @Email String recipientEmail,
   @NotBlank @Email String senderEmail,
   @NotBlank String subject,
   @NotBlank String content) {
}
