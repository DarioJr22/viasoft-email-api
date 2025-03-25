package com.challangeviasoft.email.controller;


import com.challangeviasoft.email.exception.StandardError;
import com.challangeviasoft.email.model.dto.EmailLogRequestDTO;
import com.challangeviasoft.email.model.dto.EmailLogResponseDTO;
import com.challangeviasoft.email.model.dto.ReturnMessage;
import com.challangeviasoft.email.model.dto.Status;
import com.challangeviasoft.email.model.entity.EmailLog;
import com.challangeviasoft.email.service.impl.EmailLogServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Email Log", description = "Email logging operations")
@RestController
@RequestMapping("/api/email-logs")
@RequiredArgsConstructor
public class EmailLogController {

    @Autowired
    private EmailLogServiceImpl emailLogService;

    @PostMapping
    @Operation(summary = "Process email request", description = "Endpoint responsible to set logs")
    @ApiResponses(value = {

            @ApiResponse(
                    responseCode = "204",
                    description = "Email processed successfully",
                    content = @Content(schema = @Schema(implementation = ResponseEntity.class
                    )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error in the request body",
                    content = @Content(schema = @Schema(implementation = StandardError.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = StandardError.class))
            )
    })
    public ResponseEntity<EmailLogResponseDTO> logEvent(@RequestBody @Valid EmailLogRequestDTO request) {
        EmailLog log = emailLogService.logEmailEvent(request);
        return ResponseEntity.ok(convertToResponse(log));
    }


    @GetMapping
    @Operation(summary = "Process email request", description = "Endpoint responsible get application logs")
    @ApiResponses(value = {

            @ApiResponse(
                    responseCode = "204",
                    description = "Email processed successfully",
                    content = @Content(schema = @Schema(implementation = ResponseEntity.class
                    )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error in the request body",
                    content = @Content(schema = @Schema(implementation = StandardError.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = StandardError.class))
            )
    })
    public ResponseEntity<List<EmailLogResponseDTO>> getAllLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<EmailLog> logs = emailLogService.getAllLogs(page, size);
        return ResponseEntity.ok(
                logs.stream()
                        .map(this::convertToResponse)
                        .toList()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReturnMessage> deleteLog(@PathVariable String id) {
        emailLogService.deleteLog(id);
        return ResponseEntity.ok(new ReturnMessage("Log deletado com sucesso", Status.SUCCESS));
    }

    private EmailLogResponseDTO convertToResponse(EmailLog log) {
        return new EmailLogResponseDTO(
                log.getId(),
                log.getEventType(),
                log.getRecipientEmail(),
                log.getSenderEmail(),
                log.getSubject(),
                log.getContent()
                );
    }


}
