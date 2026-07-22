package ru.shift.userimporter.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import ru.shift.userimporter.api.dto.FileDetailsDto;
import ru.shift.userimporter.api.dto.FileIdResponseDto;
import ru.shift.userimporter.api.dto.FileStatisticsDto;
import ru.shift.userimporter.api.mapper.FileDetailsMapper;
import ru.shift.userimporter.api.mapper.FileStatisticsMapper;
import ru.shift.userimporter.core.model.UploadedFiles;
import ru.shift.userimporter.core.service.FileStatisctics;
import ru.shift.userimporter.core.service.FileUploader;
import ru.shift.userimporter.core.service.IdFileProcessing;
import ru.shift.userimporter.core.service.IdFileStatistics;

import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileUploader fileUploader;
    private final IdFileProcessing idFileProcessing;
    private final FileStatisctics fileStatisctics;
    private final IdFileStatistics idFileStatistics;
    private final FileStatisticsMapper fileStatisticsMapper;
    private final FileDetailsMapper fileDetailsMapper;

    public FileController(FileUploader fileUploader,
                          IdFileProcessing idFileProcessing,
                          FileStatisctics fileStatisctics,
                          IdFileStatistics idFileStatistics,
                          FileStatisticsMapper fileStatisticsMapper,
                          FileDetailsMapper fileDetailsMapper) {
        this.fileUploader = fileUploader;
        this.idFileProcessing = idFileProcessing;
        this.fileStatisctics = fileStatisctics;
        this.idFileStatistics = idFileStatistics;
        this.fileStatisticsMapper = fileStatisticsMapper;
        this.fileDetailsMapper = fileDetailsMapper;
    }

    @PostMapping
    public ResponseEntity<FileIdResponseDto> uploadFile(@RequestParam MultipartFile file) {
        Integer fileId = fileUploader.uploadFile(file);
        FileIdResponseDto response = new FileIdResponseDto();
        response.setFileId(String.valueOf(fileId));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{id}/processing")
    public ResponseEntity<Void> processFile(@PathVariable("id") Integer fileId) {
        idFileProcessing.fileUploadProcess(fileId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/statistics")
    public ResponseEntity<List<FileStatisticsDto>> getStatistics() {
        List<UploadedFiles> files = fileStatisctics.fileStatics();
        List<FileStatisticsDto> dtos = fileStatisticsMapper.toDtoList(files);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}/statistics")
    public ResponseEntity<FileDetailsDto> getStatisticsById(@PathVariable("id") Integer fileId) {
        UploadedFiles file = idFileStatistics.fileStatisticsById(fileId);
        FileDetailsDto dto = fileDetailsMapper.toDto(file);
        return ResponseEntity.ok(dto);
    }
}