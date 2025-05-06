package com.wellvia.WellviaHealth.repository;

import com.wellvia.WellviaHealth.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
} 