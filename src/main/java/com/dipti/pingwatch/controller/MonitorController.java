package com.dipti.pingwatch.controller;

import com.dipti.pingwatch.model.MonitoredUrl;
import com.dipti.pingwatch.model.PingLog;
import com.dipti.pingwatch.service.MonitorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MonitorController {

    private final MonitorService monitorService;

    public MonitorController(MonitorService monitorService) {
        this.monitorService = monitorService;
    }

    // ENDPOINT 1: Add a URL to monitor
    // POST /api/monitor
    // Body: { "url": "https://google.com", "name": "Google" }
    @PostMapping("/monitor")
    public ResponseEntity<MonitoredUrl> addUrl(@RequestBody Map<String, String> body) {
        String url = body.get("url");
        String name = body.get("name");
        MonitoredUrl saved = monitorService.addUrl(url, name);
        return ResponseEntity.ok(saved);
    }

    // ENDPOINT 2: Get status of all URLs
    // GET /api/status
    @GetMapping("/status")
    public ResponseEntity<List<Map<String, Object>>> getStatus() {
        List<MonitoredUrl> urls = monitorService.getAllUrls();

        List<Map<String, Object>> result = urls.stream().map(u -> {
            Map<String, Object> map = new HashMap<>();
            map.put("name", u.getName());
            map.put("url", u.getUrl());
            map.put("uptimePercent", monitorService.getUptimePercent(u.getUrl()));
            return map;
        }).toList();

        return ResponseEntity.ok(result);
    }

    // ENDPOINT 3: Get ping history for a URL
    // GET /api/logs?url=https://google.com
    @GetMapping("/logs")
    public ResponseEntity<List<PingLog>> getLogs(@RequestParam String url) {
        return ResponseEntity.ok(monitorService.getLogs(url));
    }

    // BONUS: Manually trigger a ping
    // GET /api/ping?url=https://google.com
    @GetMapping("/ping")
    public ResponseEntity<PingLog> pingNow(@RequestParam String url) {
        return ResponseEntity.ok(monitorService.pingUrl(url));
    }
}