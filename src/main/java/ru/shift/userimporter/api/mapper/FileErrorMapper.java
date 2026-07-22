package ru.shift.userimporter.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.shift.userimporter.api.dto.ProcessingErrorDto;
import ru.shift.userimporter.core.model.FileProcessingErrors;
import java.util.List;

@Mapper(componentModel = "spring")
public interface FileErrorMapper {

    @Mapping(source = "rowNumber", target = "lineNumber")
    @Mapping(source = "errorMessage", target = "errorMessage")
    @Mapping(target = "errorCode", constant = "VALIDATION_ERROR") // Добавляем понятный код ошибки для фронтенда/клиента
    ProcessingErrorDto toDto(FileProcessingErrors entity);

    List<ProcessingErrorDto> toDtoList(List<FileProcessingErrors> entities);
}