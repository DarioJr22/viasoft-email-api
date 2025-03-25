package com.challangeviasoft.email.model.dto;

import jakarta.validation.constraints.Size;

public record EmailAwsDTO(
        @Size(max = 45, message = "O e-mail do destinatário AWS deve ter ≤ 45 caracteres") String recipient,
        @Size(max = 60, message = "O nome do destinatário AWS deve ter ≤ 60 caracteres") String recipientName,
        @Size(max = 45, message = "O e-mail do remetente AWS deve ter ≤ 45 caracteres") String sender,
        @Size(max = 120, message = "O assunto AWS deve ter ≤ 120 caracteres") String subject,
        @Size(max = 256, message = "O conteúdo do e-mail AWS deve ter ≤ 256 caracteres") String content
) {}
