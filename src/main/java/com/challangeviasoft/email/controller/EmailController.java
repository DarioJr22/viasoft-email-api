package com.challangeviasoft.email.controller;

import com.challangeviasoft.email.model.dto.EmailRequestDTO;
import com.challangeviasoft.email.model.dto.ReturnMessage;
import com.challangeviasoft.email.exception.StandardError;
import com.challangeviasoft.email.service.impl.EmailServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j(topic = "EMAIL_CONTROLLER")
@Tag(name = "Email API", description = "Email sending operations")
@RequestMapping("/api/emails")
@RequiredArgsConstructor
@RestController
public class EmailController {
    @Autowired
    private EmailServiceImpl emailService;

    @Operation(summary = "Process email request", description = "Endpoint responsible to send email's")
    @ApiResponses(value = {

            @ApiResponse(
                    responseCode = "204",
                    description = "Email processed successfully",
                    content = @Content(schema = @Schema(implementation = ReturnMessage.class
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
    @PostMapping
    public ResponseEntity<ReturnMessage> sendEmail(@RequestBody @Valid EmailRequestDTO request) throws Exception {
        ReturnMessage returnMessage = emailService.processEmail(request);
        return ResponseEntity.ok(returnMessage);
    }
}
