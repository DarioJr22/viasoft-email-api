package com.challangeviasoft.email.service.util;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EmailServiceConstants {

  public static final String VALIDATION_ERROR = "Validation Error";
    public static final String PROCESSING_ERROR = "Processing Error";
    public static final String INVALID_ARGUMENT = "Invalid Argument";
    public static final String RUNTIME_EXCEPTION = "Runtime Exception";
    public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
    public static final String DATA_INTEGRITY_VIOLATION = "Data Integrity Violation";
    public static final String EMAIL_AUDIT_SUCCESS = "Email successfully processed: {}";
    public static final String EMAIL_AUDIT_FAILURE = "Email processing failed";
    public static final String DATA_INTEGRITY_VIOLATION_WHILE_SAVING_AUDIT = "Data integrity violation while saving audit: {}";
    public static final String ARGUMENT_TYPE_MISMATCH = "Invalid value '%s' for parameter '%s'. Expected type: '%s'. Please provide a valid value.";
}
