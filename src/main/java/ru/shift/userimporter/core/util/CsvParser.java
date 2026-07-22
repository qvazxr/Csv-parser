package ru.shift.userimporter.core.util;

import org.springframework.stereotype.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class CsvParser {

    public List<String[]> parse(String filePath){
        List<String[]> rows = new ArrayList<>();
        File file = new File(filePath);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] columns = line.split(",", -1);
                rows.add(columns);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return rows;
    }
}
