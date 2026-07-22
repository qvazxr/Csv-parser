package ru.shift.userimporter.core.service;

import org.springframework.stereotype.Service;
import ru.shift.userimporter.core.model.UploadedFiles;
import ru.shift.userimporter.core.repository.UploadedFilesRepository;

@Service
public class IdFileStatistics {

    private final UploadedFilesRepository uploadedFilesRepository;

    public IdFileStatistics(UploadedFilesRepository uploadedFilesRepository) {
        this.uploadedFilesRepository = uploadedFilesRepository;
    }

    public UploadedFiles fileStatisticsById(Integer id) {
        return uploadedFilesRepository.findWithErrorsById(id)
                .orElseThrow(() -> new RuntimeException("Файл с ID " + id + " не найден"));
    }
}