package com.wellvia.WellviaHealth.service;

import com.wellvia.WellviaHealth.dto.ApiResponse;
import com.wellvia.WellviaHealth.dto.DoctorDTO;
import com.wellvia.WellviaHealth.interfaces.DoctorInterface;
import com.wellvia.WellviaHealth.mapper.DoctorMapper;
import com.wellvia.WellviaHealth.model.Doctor;
import com.wellvia.WellviaHealth.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public class DoctorService implements DoctorInterface {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DoctorMapper doctorMapper;

    @Override
    public ResponseEntity<ApiResponse<DoctorDTO>> getDoctorById(Long doctorId) {
        // Check if doctorId is null
        if (doctorId == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false,
                            Collections.singletonList("Doctor ID cannot be null"),
                            "Invalid request",
                            null));
        }

        // Check if doctorId is valid
        if (doctorId <= 0) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false,
                            Collections.singletonList("Invalid Doctor ID"),
                            "Invalid request",
                            null));
        }

        // Find the doctor
        Doctor doctor = doctorRepository.findByIdWithAvailableSlots(doctorId)
                .orElse(null);

        // Check if doctor exists
        if (doctor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false,
                            Collections.singletonList("Doctor not found with id: " + doctorId),
                            "Doctor not found",
                            null));
        }

        // Check if doctor is deleted
        if (doctor.getIsDeleted()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false,
                            Collections.singletonList("Doctor has been deleted"),
                            "Doctor not found",
                            null));
        }

        // Map to DTO
        DoctorDTO doctorDTO = doctorMapper.toDTO(doctor);
        if (doctorDTO == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false,
                            Collections.singletonList("Error mapping doctor data"),
                            "Internal server error",
                            null));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, null, "Doctor details retrieved successfully", doctorDTO));
    }
} 