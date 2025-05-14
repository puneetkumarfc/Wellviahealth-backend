package com.wellvia.WellviaHealth.interfaces;

import com.wellvia.WellviaHealth.dto.ApiResponse;
import com.wellvia.WellviaHealth.dto.DoctorDTO;
import org.springframework.http.ResponseEntity;

public interface DoctorInterface {
    ResponseEntity<ApiResponse<DoctorDTO>> getDoctorById(Long doctorId);
} 