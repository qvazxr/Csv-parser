package ru.shift.userimporter.core.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import ru.shift.userimporter.core.model.StatusError;
import ru.shift.userimporter.core.model.UploadedFiles;
import ru.shift.userimporter.core.model.Users;
import ru.shift.userimporter.core.util.CsvErrorsAndValidator;
import ru.shift.userimporter.core.util.CsvParser;
import ru.shift.userimporter.core.repository.FileProcessingErrorsRepository;
import ru.shift.userimporter.core.repository.UsersRepository;
import ru.shift.userimporter.core.repository.UploadedFilesRepository;

@Slf4j
@Async
@Service
public class IdFileProcessing {

    private final CsvParser csvParser;
    private final CsvErrorsAndValidator csvErrorsAndValidator;
    private final UsersRepository usersRepository;
    private final FileProcessingErrorsRepository fileProcessingErrorsRepository;
    private final UploadedFilesRepository uploadedFilesRepository;

    public IdFileProcessing(CsvErrorsAndValidator csvErrorsAndValidator,
                            CsvParser csvParser,
                            UsersRepository usersRepository,
                            FileProcessingErrorsRepository fileProcessingErrorsRepository,
                            UploadedFilesRepository uploadedFilesRepository) {
        this.csvErrorsAndValidator = csvErrorsAndValidator;
        this.csvParser = csvParser;
        this.usersRepository = usersRepository;
        this.fileProcessingErrorsRepository = fileProcessingErrorsRepository;
        this.uploadedFilesRepository = uploadedFilesRepository;
    }

    @Transactional
    public void fileUploadProcess(Integer id) {
        UploadedFiles uploadedFile = uploadedFilesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Файл не найден с ID: " + id));

        try {
            // Переводим статус в IN_PROGRESS
            uploadedFile.setStatus(StatusError.IN_PROGRESS);
            uploadedFilesRepository.saveAndFlush(uploadedFile);

            // Чтение строк из файла
            List<String[]> usersRows = csvParser.parse(uploadedFile.getStoragePath());
            uploadedFile.setTotalRows(usersRows.size());

            // Валидация данных (передаем объект файла для связывания ошибок)
            CsvErrorsAndValidator.ValidationResult validationResult =
                    csvErrorsAndValidator.processErrorFile(usersRows, uploadedFile);

            int validCount = validationResult.getUserDone().size();
            int invalidCount = validationResult.getFileErrors().size();
            log.info("Обработка файла ID {}: Валидных строк: {}, Ошибочных: {}", id, validCount, invalidCount);

            // Проставляем даты создания и обновления для валидных юзеров
            for (Users user : validationResult.getUserDone()) {
                user.setCreatedAt(LocalDateTime.now());
                user.setUpdatedAt(LocalDateTime.now());
            }

            // Сохраняем пользователей в базу данных
            if (!validationResult.getUserDone().isEmpty()) {
                usersRepository.saveAll(validationResult.getUserDone());
            }

            // Сохраняем ошибки обработки в базу данных
            if (!validationResult.getFileErrors().isEmpty()) {
                fileProcessingErrorsRepository.saveAll(validationResult.getFileErrors());
            }

            // Заполняем поля статистики в сущности UploadedFiles
            uploadedFile.setValidRows(validCount);
            uploadedFile.setInvalidRows(invalidCount);
            uploadedFile.setProcessedRows(usersRows.size());

            // Успешное завершение -статус DONE
            uploadedFile.setStatus(StatusError.DONE);
            uploadedFilesRepository.save(uploadedFile);

            log.info("Обработка файла ID {} успешно завершена.", id);

        } catch (Exception e) {
            log.error("Критическая ошибка при обработке файла ID {}: {}", id, e.getMessage(), e);

            // В случае падения - статус FAILED
            uploadedFile.setStatus(StatusError.FAILED);
            uploadedFilesRepository.save(uploadedFile);

            throw new RuntimeException("Ошибка при обработке файла с ID " + id, e);
        }
    }
}