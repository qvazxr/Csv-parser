package ru.shift.userimporter.core.service;

import org.springframework.stereotype.Service;
import ru.shift.userimporter.core.model.UploadedFiles;
import ru.shift.userimporter.core.repository.UploadedFilesRepository;

import java.util.List;

@Service
public class FileStatisctics {

    private final UploadedFilesRepository uploadedFilesRepository;

    public FileStatisctics(UploadedFilesRepository uploadedFilesRepository){
        this.uploadedFilesRepository = uploadedFilesRepository;
    }

    public List<UploadedFiles> fileStatics(){
        return uploadedFilesRepository.findAll();
    }
}
