package com.wellvia.WellviaHealth.repository;

import com.wellvia.WellviaHealth.model.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    Optional<UserSession> findBySessionTokenAndIsActiveTrue(String sessionToken);
    Optional<UserSession> findByUserIdAndSessionTokenAndIsActiveTrue(Long userId, String sessionToken);
    
    @Modifying
    @Query("UPDATE UserSession s SET s.isActive = false WHERE s.user.id = ?1 AND s.isActive = true")
    void deactivateAllUserSessions(Long userId);
} 