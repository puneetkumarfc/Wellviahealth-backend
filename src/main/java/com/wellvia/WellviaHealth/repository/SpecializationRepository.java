package com.wellvia.WellviaHealth.repository;

import com.wellvia.WellviaHealth.model.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Long> {
    
    @Query("SELECT s FROM Specialization s WHERE s.isDeleted = false AND " +
           "(:search IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :search, '%'))) " +
           "ORDER BY s.name ASC")
    List<Specialization> findAllActiveWithSearch(@Param("search") String search);
} 