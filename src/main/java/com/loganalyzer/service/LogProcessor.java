package com.loganalyzer.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class LogProcessor {
    private static final String LOG_FILE_PATH = "C:/Users/Zehra Yagmur/IdeaProjects/LogAnalyzerApp/logs/test.log";

    public static void main(String[] args) {
        try {
            List<String> logs = Files.readAllLines(Paths.get(LOG_FILE_PATH), StandardCharsets.UTF_8);

            // Logları seviyelerine göre grupla
            Map<String, List<String>> groupedLogs = logs.stream()
                    .collect(Collectors.groupingBy(LogProcessor::getLogLevel));

            // Her seviyeden kaç tane olduğunu hesapla
            Map<String, Integer> logCounts = groupedLogs.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().size()));

            System.out.println("=== LOG ANALİZ RAPORU ===");
            logCounts.forEach((level, count) -> System.out.println(level + " LOGS: " + count + " adet"));

            // ERROR loglarını tarih sırasına göre sıralayalım
            List<String> sortedErrorLogs = groupedLogs.getOrDefault("ERROR", new ArrayList<>()).stream()
                    .sorted(Comparator.comparing(LogProcessor::extractTimestamp))
                    .collect(Collectors.toList());

            System.out.println("\n🔴 ERROR LOGS (Tarih Sırasına Göre):");
            sortedErrorLogs.forEach(System.out::println);

        } catch (IOException e) {
            System.err.println("Log dosyası okunurken hata oluştu: " + e.getMessage());
        }
    }

    // Log seviyesini belirleyen yardımcı metot
    private static String getLogLevel(String log) {
        if (log.contains("[INFO]")) return "INFO";
        if (log.contains("[DEBUG]")) return "DEBUG";
        if (log.contains("[ERROR]")) return "ERROR";
        return "OTHER";
    }

    // Loglardan timestamp bilgisini çıkaran metot
    private static String extractTimestamp(String log) {
        String[] parts = log.split(" - ");
        return parts.length > 0 ? parts[0].replace("[INFO]", "").replace("[DEBUG]", "").replace("[ERROR]", "").trim() : "";
    }
}
