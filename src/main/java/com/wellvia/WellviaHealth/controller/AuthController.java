package com.wellvia.WellviaHealth.controller;

import com.wellvia.WellviaHealth.dto.OTPRequestDTO;
import com.wellvia.WellviaHealth.dto.RegisterRequestDTO;
import com.wellvia.WellviaHealth.dto.LoginRequestDTO;
import com.wellvia.WellviaHealth.dto.OtpVerifyDTO;
import com.wellvia.WellviaHealth.dto.LogoutRequestDTO;
import com.wellvia.WellviaHealth.service.AuthServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthServiceImpl authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO request) {
        return authService.register(request);
    }

    @PostMapping("/login/request-otp")
    public ResponseEntity<?> requestLoginOtp(@Valid @RequestBody LoginRequestDTO request) {
        return authService.requestLoginOtp(request);
    }

    @PostMapping("/login/verify-otp")
    public ResponseEntity<?> verifyLoginOtp(@Valid @RequestBody OtpVerifyDTO request) {
        return authService.verifyLoginOtp(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader, @RequestBody LogoutRequestDTO request) {
        String token = authHeader.substring(7); // Remove "Bearer " prefix
        return authService.logout(token, request);
    }

    // ... existing endpoints ...
}
