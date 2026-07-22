package ru.shift.userimporter.api.dto;  // ← исправлен пакет

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ClientResponseDto {
    private Long phone;
    private String name;
    private String lastName;
    private String middleName;
    private String email;
    private LocalDate birthdate;
    private LocalDateTime creationTime;
    private LocalDateTime updateTime;
}