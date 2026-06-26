package com.dipti.pingwatch.repository;

import com.dipti.pingwatch.model.MonitoredUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitoredUrlRepository
        extends JpaRepository<MonitoredUrl, Long> {
    // Spring automatically gives you:
    // save(), findAll(), findById(), deleteById()
    // No code needed!
}
