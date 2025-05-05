package com.wellvia.WellviaHealth.interfaces;

import com.wellvia.WellviaHealth.dto.OTPRequestDTO;
import org.springframework.http.ResponseEntity;

public interface AuthInterface {
    ResponseEntity<?> requestOtp(OTPRequestDTO request);
}
