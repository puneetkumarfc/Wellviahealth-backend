package com.wellvia.WellviaHealth.interfaces;

import com.wellvia.WellviaHealth.dto.OTPRequestDTO;
import com.wellvia.WellviaHealth.dto.RegisterRequestDTO;
import org.springframework.http.ResponseEntity;

public interface AuthInterface {
    ResponseEntity<?> requestOtp(OTPRequestDTO request);
    ResponseEntity<?> register(RegisterRequestDTO request);
}
