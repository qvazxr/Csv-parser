package ru.shift.userimporter.api.dto;

import lombok.Data;

@Data
public class ProcessingErrorDto {
    private Integer lineNumber;
    private String errorCode;
    private String errorMessage;
}