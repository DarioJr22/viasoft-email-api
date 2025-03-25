package com.challangeviasoft.email.model.dto;

import jakarta.validation.constraints.Size;

public record EmailOciDTO(
        @Size(max = 40, message = "O e-mail do destinatário OCI deve ter ≤ 40 caracteres") String recipientEmail,
        @Size(max = 50, message = "O nome do destinatário OCI deve ter ≤ 50 caracteres") String recipientName,
        @Size(max = 40, message = "O e-mail do remetente OCI deve ter ≤ 40 caracteres") String senderEmail,
        @Size(max = 100, message = "O assunto OCI deve ter ≤ 100 caracteres") String subject,
        @Size(max = 250, message = "O conteúdo do e-mail OCI deve ter ≤ 250 caracteres") String body
) {}
