package ru.shift.userimporter.core.util;

import ru.shift.userimporter.core.model.FileProcessingErrors;
import ru.shift.userimporter.core.model.Users;
import ru.shift.userimporter.core.repository.UsersRepository;
import ru.shift.userimporter.core.repository.FileProcessingErrorsRepository;

import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class UserAndProcessFileSaver {

    private final UsersRepository usersRepository;
    private final FileProcessingErrorsRepository fileProcessingErrorsRepository;

    public UserAndProcessFileSaver(UsersRepository usersRepository,
                         FileProcessingErrorsRepository fileProcessingErrorsRepository){
        this.usersRepository = usersRepository;
        this.fileProcessingErrorsRepository = fileProcessingErrorsRepository;
    }

    public void userSaver(List<Users> csvUserDone){
        if (csvUserDone != null && !csvUserDone.isEmpty()) {
            usersRepository.saveAll(csvUserDone);
        }
        // кинуть ошибку
    }

    public void processingErrorSaver(List<FileProcessingErrors> csvFileErrors){
        if (csvFileErrors != null && !csvFileErrors.isEmpty()) {
            fileProcessingErrorsRepository.saveAll(csvFileErrors);
        }// кинуть ошибку
    }
}
