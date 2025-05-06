package com.wellvia.WellviaHealth.repository;

import com.wellvia.WellviaHealth.model.Laboratory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaboratoryRepository extends JpaRepository<Laboratory, Long> {
    List<Laboratory> findByIsDeletedFalse();
    List<Laboratory> findByCityAndIsDeletedFalse(String city);
    List<Laboratory> findByStateAndIsDeletedFalse(String state);
    Page<Laboratory> findByCityAndStateAndIsDeletedFalse(String city, String state, Pageable pageable);
} 