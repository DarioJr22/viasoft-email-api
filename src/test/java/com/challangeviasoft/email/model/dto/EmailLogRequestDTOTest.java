package com.challangeviasoft.email.model.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
public class EmailLogRequestDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void emailLogResponseDTO_ValidData_NoViolations() {
        EmailLogResponseDTO dto = new EmailLogResponseDTO(
                "123",
                "SENT",
                "user@test.com",
                "sender@test.com",
                "Subject",
                "Content"
        );
        Set<ConstraintViolation<EmailLogResponseDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }
}
