package ru.shift.userimporter.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.shift.userimporter.api.dto.FileDetailsDto;
import ru.shift.userimporter.core.model.UploadedFiles;

@Mapper(componentModel = "spring", uses = FileErrorMapper.class)
public interface FileDetailsMapper {

    @Mapping(source = "validRows", target = "insertedLinesCount")
    @Mapping(target = "updatedLinesCount", constant = "0")
    @Mapping(source = "errors", target = "errors")
    FileDetailsDto toDto(UploadedFiles entity);
}