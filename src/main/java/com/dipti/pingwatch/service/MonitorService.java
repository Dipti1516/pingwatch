package com.dipti.pingwatch.service;

import com.dipti.pingwatch.model.MonitoredUrl;
import com.dipti.pingwatch.model.PingLog;
import com.dipti.pingwatch.repository.MonitoredUrlRepository;
import com.dipti.pingwatch.repository.PingLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MonitorService {

    private static final Logger logger = LoggerFactory.getLogger(MonitorService.class);

    private final MonitoredUrlRepository urlRepository;
    private final PingLogRepository logRepository;

    public MonitorService(MonitoredUrlRepository urlRepository,
                          PingLogRepository logRepository) {
        this.urlRepository = urlRepository;
        this.logRepository = logRepository;
    }

    // ADD a URL to monitor
    public MonitoredUrl addUrl(String url, String name) {
        MonitoredUrl monitored = new MonitoredUrl();
        monitored.setUrl(url);
        monitored.setName(name);
        logger.info("Adding URL to monitor: {}", url);
        MonitoredUrl saved = urlRepository.save(monitored);

        // Ping immediately when URL is added
        pingUrl(url);

        return saved;
    }

    // PING a single URL and save result
    public PingLog pingUrl(String url) {
        PingLog log = new PingLog();
        log.setUrl(url);
        log.setCheckedAt(LocalDateTime.now());

        try {
            long start = System.currentTimeMillis();

            HttpURLConnection connection = (HttpURLConnection)
                    new URL(url).openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestMethod("GET");

            // Pretend to be a real browser so sites like Flipkart don't block us
            connection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/120.0.0.0 Safari/537.36");
            connection.setRequestProperty("Accept",
                    "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");

            // Follow redirects (301, 302) automatically
            connection.setInstanceFollowRedirects(true);

            int responseCode = connection.getResponseCode();
            long responseTime = System.currentTimeMillis() - start;

            // 200-399 = UP (includes redirects)
            // 400+ = DOWN (client/server errors)
            boolean isUp = responseCode >= 200 && responseCode < 400;

            log.setUp(isUp);
            log.setResponseTimeMs(responseTime);

            logger.info("Pinged {} | Code: {} | Status: {} | Time: {}ms",
                    url, responseCode, isUp ? "UP" : "DOWN", responseTime);

        } catch (Exception e) {
            // Can't reach website at all = DOWN
            log.setUp(false);
            log.setResponseTimeMs(0);
            logger.warn("Failed to ping {} | Reason: {}", url, e.getMessage());
        }

        return logRepository.save(log);
    }

    // PING ALL monitored URLs
    public void pingAll() {
        List<MonitoredUrl> urls = urlRepository.findAll();
        logger.info("Pinging {} URLs...", urls.size());
        for (MonitoredUrl monitored : urls) {
            pingUrl(monitored.getUrl());
        }
    }

    // GET uptime % for a URL
    public double getUptimePercent(String url) {
        List<PingLog> logs = logRepository.findByUrl(url);
        if (logs.isEmpty()) return 0.0;

        long upCount = logs.stream()
                .filter(PingLog::isUp)
                .count();

        return (upCount * 100.0) / logs.size();
    }

    // GET all monitored URLs
    public List<MonitoredUrl> getAllUrls() {
        return urlRepository.findAll();
    }

    // GET ping history for a URL
    public List<PingLog> getLogs(String url) {
        return logRepository.findByUrl(url);
    }
}