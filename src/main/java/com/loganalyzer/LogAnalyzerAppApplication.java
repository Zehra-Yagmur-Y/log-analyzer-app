package com.loganalyzer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootApplication
public class LogAnalyzerAppApplication extends SpringBootServletInitializer implements CommandLineRunner {

    private static final String LOG_FILE_PATH = "logs/test.log";

    public static void main(String[] args) {
        SpringApplication.run(LogAnalyzerAppApplication.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            List<String> logs = Files.readAllLines(Paths.get(LOG_FILE_PATH), StandardCharsets.UTF_8);

            // Dosyanın okunup okunmadığını kontrol et
            System.out.println("=== LOG DOSYASI İÇERİĞİ ===");
            logs.forEach(System.out::println);

            // Logları seviyelerine göre grupla
            Map<String, List<String>> groupedLogs = logs.stream()
                    .collect(Collectors.groupingBy(LogAnalyzerAppApplication::getLogLevel));

            System.out.println("=== LOG ANALİZ RAPORU ===");
            groupedLogs.forEach((level, logEntries) -> {
                System.out.println("\n" + level + " LOGS:");
                logEntries.forEach(System.out::println);
            });

        } catch (IOException e) {
            System.err.println("Log dosyası okunurken hata oluştu: " + e.getMessage());
        }
    }

    private static String getLogLevel(String log) {
        if (log.contains("[INFO]")) return "INFO";
        if (log.contains("[DEBUG]")) return "DEBUG";
        if (log.contains("[ERROR]")) return "ERROR";
        return "OTHER";
    }
}
