package com.wellvia.WellviaHealth.repository;

import com.wellvia.WellviaHealth.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
} 