package com.challangeviasoft.email.model.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailRequestDTO(
        @NotBlank(message = "O email do destinatário não pode estar em branco.") @Email(message = "O Email do destinatário deve ser formatado corretamente") String recipientEmail,
        @NotBlank(message = "O nome do destinatário não pode estar em branco.") String recipientName,
        @NotBlank(message = "O email do remetente não pode estar em branco.") @Email(message = "O Email do remetente deve ser formatado corretamente") String senderEmail,
        @NotBlank(message = "O assunto do email não pode estar em branco.") String subject,
        @NotBlank(message = "O conteúdo do email não pode estar em branco.") String content
) {
}
