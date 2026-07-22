package ru.shift.userimporter.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.shift.userimporter.api.dto.FileStatisticsDto;
import ru.shift.userimporter.core.model.UploadedFiles;
import java.util.List;

@Mapper(componentModel = "spring")
public interface FileStatisticsMapper {

    @Mapping(source = "id", target = "fileId")
    @Mapping(source = "validRows", target = "statistic.insertedLinesCount")
    @Mapping(target = "statistic.updatedLinesCount", constant = "0")
    @Mapping(source = "invalidRows", target = "statistic.errorProcessedLinesCount")
    FileStatisticsDto toDto(UploadedFiles entity);  // ← правильный DTO

    List<FileStatisticsDto> toDtoList(List<UploadedFiles> entities);
}