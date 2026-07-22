package ru.shift.userimporter.api.dto;

import ru.shift.userimporter.api.dto.ProcessingErrorDto;

import java.util.List;
import lombok.Data;

@Data
public class FileDetailsDto {
    private Integer insertedLinesCount;
    private Integer updatedLinesCount;
    private List<ProcessingErrorDto> errors;
}