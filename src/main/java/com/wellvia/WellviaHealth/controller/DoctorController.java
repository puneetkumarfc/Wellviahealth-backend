package com.wellvia.WellviaHealth.controller;

import com.wellvia.WellviaHealth.dto.RegisterRequestDTO;
import com.wellvia.WellviaHealth.dto.LoginRequestDTO;
import com.wellvia.WellviaHealth.dto.OtpVerifyDTO;
import com.wellvia.WellviaHealth.dto.LogoutRequestDTO;
import com.wellvia.WellviaHealth.service.AuthService;
import com.wellvia.WellviaHealth.dto.ApiResponse;
import com.wellvia.WellviaHealth.dto.DoctorDTO;
import com.wellvia.WellviaHealth.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctor")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true", allowedHeaders = "*", exposedHeaders = {"Authorization", "X-Device-Info"})
public class DoctorController {
    
    @Autowired
    private DoctorService doctorService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorDTO>> getDoctorById(@PathVariable("id") Long doctorId) {
        return doctorService.getDoctorById(doctorId);
    }
}
