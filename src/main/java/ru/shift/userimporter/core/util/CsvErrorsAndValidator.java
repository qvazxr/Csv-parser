package ru.shift.userimporter.core.util;

import org.springframework.stereotype.Component;
import ru.shift.userimporter.core.model.FileProcessingErrors;
import ru.shift.userimporter.core.model.UploadedFiles;
import ru.shift.userimporter.core.model.Users;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class CsvErrorsAndValidator {

    private static final Pattern NAME = Pattern.compile("^[А-ЯЁ][а-яёА-ЯЁ'\\- ]{2,49}$");
    private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9._%+-]+@shift\\.(com|ru)$");
    private static final Pattern PHONE = Pattern.compile("^7[0-9]{10}$");
    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public class ValidationResult {
        private List<FileProcessingErrors> csvFileErrors = new ArrayList<>();
        private List<Users> csvUserDone = new ArrayList<>();

        public List<FileProcessingErrors> getFileErrors() { return csvFileErrors; }
        public List<Users> getUserDone() { return csvUserDone; }
    }

    public ValidationResult processErrorFile(List<String[]> rows, UploadedFiles uploadedFile) {
        ValidationResult parseResult = new ValidationResult();
        int rowNumber = 0;

        for (String[] el : rows) {
            boolean stringValid = true;
            rowNumber++;

            // Защита: если в строке меньше 6 колонок или строка пустая
            if (el == null || el.length < 6) {
                FileProcessingErrors error = new FileProcessingErrors();
                error.setUploadedFile(uploadedFile); // Привязка к файлу, чтобы не было ошибки 500
                error.setRowNumber(rowNumber);
                error.setErrorMessage("Некорректное количество колонок: ожидается 6 полей через запятую");
                error.setRawData(el != null ? String.join(",", el) : "");
                parseResult.getFileErrors().add(error);
                continue;
            }

            // Порядок полей согласно ТЗ: 0-Имя, 1-Фамилия, 2-Отчество, 3-Email, 4-Телефон, 5-Дата рождения

            // 1. Проверка Имени (el[0])
            if (el[0] == null || !NAME.matcher(el[0].trim()).matches()) {
                FileProcessingErrors error = new FileProcessingErrors();
                error.setUploadedFile(uploadedFile);
                error.setRowNumber(rowNumber);
                error.setErrorMessage("Имя возможные ошибки: кириллица, 3-50 символов, первая заглавная");
                error.setRawData(String.join(",", el));
                parseResult.getFileErrors().add(error);
                stringValid = false;
            }

            // 2. Проверка Фамилии (el[1])
            if (el[1] == null || !NAME.matcher(el[1].trim()).matches()) {
                FileProcessingErrors error = new FileProcessingErrors();
                error.setUploadedFile(uploadedFile);
                error.setRowNumber(rowNumber);
                error.setErrorMessage("Фамилия возможные ошибки: кириллица, 3-50 символов, первая заглавная");
                error.setRawData(String.join(",", el));
                parseResult.getFileErrors().add(error);
                stringValid = false;
            }

            // 3. Проверка Отчества (el[2]) - Необязательное
            if (el[2] != null && !el[2].trim().isEmpty() && !NAME.matcher(el[2].trim()).matches()) {
                FileProcessingErrors error = new FileProcessingErrors();
                error.setUploadedFile(uploadedFile);
                error.setRowNumber(rowNumber);
                error.setErrorMessage("Отчество возможные ошибки: кириллица, 3-50 символов, первая заглавная");
                error.setRawData(String.join(",", el));
                parseResult.getFileErrors().add(error);
                stringValid = false;
            }

            // 4. Проверка Email (el[3])
            if (el[3] == null || !EMAIL.matcher(el[3].trim()).matches()) {
                FileProcessingErrors error = new FileProcessingErrors();
                error.setUploadedFile(uploadedFile);
                error.setRowNumber(rowNumber);
                error.setErrorMessage("Email возможные ошибки: только shift.com или shift.ru");
                error.setRawData(String.join(",", el));
                parseResult.getFileErrors().add(error);
                stringValid = false;
            }

            // 5. Проверка Телефона (el[4])
            if (el[4] == null || !PHONE.matcher(el[4].trim()).matches()) {
                FileProcessingErrors error = new FileProcessingErrors();
                error.setUploadedFile(uploadedFile);
                error.setRowNumber(rowNumber);
                error.setErrorMessage("Телефон возможные ошибки: должно начинаться с 7 и состоять из 11 цифр");
                error.setRawData(String.join(",", el));
                parseResult.getFileErrors().add(error);
                stringValid = false;
            }

            // 6. Проверка Даты рождения (el[5])
            try {
                LocalDate birth = LocalDate.parse(el[5].trim(), DATE);
                if (birth.isAfter(LocalDate.now().minusYears(18))) {
                    FileProcessingErrors error = new FileProcessingErrors();
                    error.setUploadedFile(uploadedFile);
                    error.setRowNumber(rowNumber);
                    error.setErrorMessage("Возраст должен быть больше 18 лет");
                    error.setRawData(String.join(",", el));
                    parseResult.getFileErrors().add(error);
                    stringValid = false;
                }
            } catch (Exception e) {
                FileProcessingErrors error = new FileProcessingErrors();
                error.setUploadedFile(uploadedFile);
                error.setRowNumber(rowNumber);
                error.setErrorMessage("Дата рождения должна быть в формате yyyy-MM-dd");
                error.setRawData(String.join(",", el));
                parseResult.getFileErrors().add(error);
                stringValid = false;
            }

            // Если строка валидна — создаем объект пользователя
            if (stringValid) {
                Users user = new Users();
                user.setFirstName(el[0].trim());
                user.setLastName(el[1].trim());
                user.setMiddleName(el[2].trim().isEmpty() ? null : el[2].trim());
                user.setEmail(el[3].trim());
                user.setPhone(el[4].trim());
                user.setBirthDate(LocalDate.parse(el[5].trim(), DATE));
                parseResult.getUserDone().add(user);
            }
        }

        return parseResult;
    }
}