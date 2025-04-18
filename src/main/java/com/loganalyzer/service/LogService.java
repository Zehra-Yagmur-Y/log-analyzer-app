package com.loganalyzer.service;

import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LogService {

    private static final String LOG_DIRECTORY = "logs"; // Log dosyalarının olduğu klasör

    /**
     * Tüm log dosyalarını oku ve liste olarak döndür.
     */
    public List<String> readLogFiles() {
        List<String> logs = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(LOG_DIRECTORY), "*.log")) {
            for (Path path : stream) {
                List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
                logs.addAll(lines);
                // İşlenmiş dosyayı .processed olarak değiştirme (opsiyonel)
                // Files.move(path, path.resolveSibling(path.getFileName() + ".processed"), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return logs;
    }

    /**
     * Logları seviyeye ve tarihe göre filtreler.
     */
    public List<String> getFilteredLogs(String level, String date) {
        List<String> logs = readLogFiles();

        if (level != null) {
            logs = logs.stream()
                    .filter(log -> log.contains("[" + level.toUpperCase() + "]"))
                    .collect(Collectors.toList());
        }

        if (date != null) {
            logs = logs.stream()
                    .filter(log -> log.contains(date))
                    .collect(Collectors.toList());
        }

        return logs;
    }

    /**
     * Logları seviyelerine göre özetler (INFO, DEBUG, ERROR).
     */
    public Map<String, Long> getLogSummary() {
        List<String> logs = readLogFiles();
        return logs.stream()
                .collect(Collectors.groupingBy(this::getLogLevel, Collectors.counting()));
    }

    /**
     * Log seviyesini belirleme fonksiyonu.
     */
    private String getLogLevel(String log) {
        if (log.contains("[INFO]")) return "INFO";
        if (log.contains("[DEBUG]")) return "DEBUG";
        if (log.contains("[ERROR]")) return "ERROR";
        return "OTHER";
    }
}
