package com.challangeviasoft.email.model.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Set;

public class EmailAwsDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    @Test
    void emailAwsDTO_ValidData_NoViolations() {
        EmailAwsDTO dto = new EmailAwsDTO(
                "recipient@aws.com",
                "John AWS",
                "sender@aws.com",
                "A".repeat(120),
                "C".repeat(256)
        );

        Set<ConstraintViolation<EmailAwsDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void emailAwsDTO_ExceedMaxLength_ReturnsViolations() {
        EmailAwsDTO dto = new EmailAwsDTO(
                "r".repeat(46) + "@aws.com",
                "n".repeat(61),
                "s".repeat(46) + "@aws.com",
                "s".repeat(121),
                "c".repeat(257)
        );

        Set<ConstraintViolation<EmailAwsDTO>> violations = validator.validate(dto);
        assertEquals(5, violations.size());

        List<String> messages = violations.stream()
                .map(v -> v.getConstraintDescriptor().getMessageTemplate())
                .toList();

        assertTrue(messages.contains("O e-mail do destinatário AWS deve ter ≤ 45 caracteres"));
        assertTrue(messages.contains("O nome do destinatário AWS deve ter ≤ 60 caracteres"));
    }
}
