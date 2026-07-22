package ru.shift.userimporter.core.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileSaver {
    private final String uploadDirectory
            = "Storage";

    public String fileSaver(MultipartFile file){

        if (file.isEmpty()) {
            System.out.print("файл пуст");
            return null;
        }

        try {
            Files.createDirectories(Paths.get(uploadDirectory));
            Path targetPath = Paths.get(uploadDirectory).resolve(UUID.randomUUID() + "_" + file.getOriginalFilename());
            file.transferTo(targetPath.toAbsolutePath());

            return targetPath.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
