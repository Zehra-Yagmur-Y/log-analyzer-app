package com.loganalyzer.controller;

import com.loganalyzer.service.LogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3001")
@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    /**
     * Logları seviyeye ve tarihe göre filtreleyerek getirir.
     */
    @GetMapping
    public List<String> getLogs(@RequestParam(required = false) String level,
                                @RequestParam(required = false) String date) {
        return logService.getFilteredLogs(level, date);
    }

    /**
     * Logları seviyelerine göre özetler.
     */
    @GetMapping("/summary")
    public Map<String, Long> getLogSummary() {
        return logService.getLogSummary();
    }

    /**
     * Tüm logları getirir.
     */
    @GetMapping("/read")
    public List<String> readLogs() {
        return logService.readLogFiles();
    }
}
