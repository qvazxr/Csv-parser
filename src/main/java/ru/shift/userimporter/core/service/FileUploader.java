package ru.shift.userimporter.core.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.shift.userimporter.core.model.StatusError;
import ru.shift.userimporter.core.model.UploadedFiles;
import ru.shift.userimporter.core.repository.UploadedFilesRepository;
import ru.shift.userimporter.core.util.FileSaver;
import ru.shift.userimporter.core.util.UserAndProcessFileSaver;

@Service
public class FileUploader {

    private final FileSaver fileSaver;
    private final UploadedFilesRepository uploadedFilesRepository;

    public FileUploader(FileSaver fileSaver, UploadedFilesRepository uploadedFilesRepository) {
        this.fileSaver = fileSaver;
        this.uploadedFilesRepository = uploadedFilesRepository;
    }

    public Integer uploadFile(MultipartFile file) {
        String directory = fileSaver.fileSaver(file);

        UploadedFiles uploadedFile = new UploadedFiles();
        uploadedFile.setStoragePath(directory);
        uploadedFile.setOriginalFilename(file.getOriginalFilename());
        uploadedFile.setStatus(StatusError.NEW);

        UploadedFiles savedFile = uploadedFilesRepository.save(uploadedFile);
        return savedFile.getId();
    }
}