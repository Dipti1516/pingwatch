package com.dipti.pingwatch.repository;

import com.dipti.pingwatch.model.PingLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PingLogRepository
        extends JpaRepository<PingLog, Long> {

    // Find all ping history for a specific URL
    List<PingLog> findByUrl(String url);
}
