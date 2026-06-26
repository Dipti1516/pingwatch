package com.dipti.pingwatch.scheduler;

import com.dipti.pingwatch.service.MonitorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PingScheduler {

    private static final Logger logger = LoggerFactory.getLogger(PingScheduler.class);

    private final MonitorService monitorService;

    public PingScheduler(MonitorService monitorService) {
        this.monitorService = monitorService;
    }

    // Runs automatically every 60 seconds
    @Scheduled(fixedRate = 60000)
    public void scheduledPing() {
        logger.info("Scheduler triggered — pinging all URLs...");
        monitorService.pingAll();
        logger.info("Scheduler done.");
    }
}