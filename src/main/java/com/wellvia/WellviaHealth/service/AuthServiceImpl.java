package com.wellvia.WellviaHealth.service;

import com.wellvia.WellviaHealth.dto.OTPRequestDTO;
import com.wellvia.WellviaHealth.dto.RegisterRequestDTO;
import com.wellvia.WellviaHealth.interfaces.AuthInterface;
import com.wellvia.WellviaHealth.model.Users;
import com.wellvia.WellviaHealth.model.UserType;
import com.wellvia.WellviaHealth.model.Patient;
import com.wellvia.WellviaHealth.repository.UserRepository;
import com.wellvia.WellviaHealth.repository.UserTypeRepository;
import com.wellvia.WellviaHealth.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.wellvia.WellviaHealth.dto.ApiResponse;
import java.util.Collections;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import com.wellvia.WellviaHealth.dto.LoginRequestDTO;
import com.wellvia.WellviaHealth.dto.OtpVerifyDTO;
import com.wellvia.WellviaHealth.security.JwtUtil;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthInterface {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTypeRepository userTypeRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public ResponseEntity<?> requestOtp(OTPRequestDTO request) {
        Optional<Users> existingUser = userRepository.findByPhoneNumber(request.getPhoneNumber());

        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body(
                new ApiResponse<>(false, Collections.singletonList("Phone number already exists"), "OTP request failed", null)
            );
        }

        Users user = new Users();
        user.setPhoneNumber(request.getPhoneNumber());

        // Generate a random 6-digit OTP
        String otp = String.format("%06d", new Random().nextInt(999999));
        user.setOtp(otp);
        user.setOtpCreatedAt(LocalDateTime.now());
        user.setOtpVerified(false);

        // Set user_type_id to Patient (assumed ID 2)
        Optional<UserType> userType = userTypeRepository.findById(2L);
        userType.ifPresent(user::setUserType);

        userRepository.save(user);

        // In actual code, send OTP via SMS here

        return ResponseEntity.ok(
            new ApiResponse<>(true, null, "OTP sent successfully to phone number", null)
        );
    }

    @Override
    public ResponseEntity<?> register(RegisterRequestDTO request) {
        Optional<Users> existingUser = userRepository.findByPhoneNumber(request.getPhoneNumber());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body(
                new ApiResponse<>(false, Collections.singletonList("Phone number already exists"), "Registration failed", null)
            );
        }

        Users user = new Users();
        user.setPhoneNumber(request.getPhoneNumber());

        // Generate a random 6-digit OTP
        String otp = String.format("%06d", new Random().nextInt(999999));
        user.setOtp(otp);
        user.setOtpCreatedAt(LocalDateTime.now());
        user.setOtpVerified(false);

        // Set user_type_id to Patient (ID 2)
        Optional<UserType> userType = userTypeRepository.findById(2L);
        userType.ifPresent(user::setUserType);

        Users savedUser = userRepository.save(user);

        // Create Patient entry
        Patient patient = new Patient();
        patient.setUser(savedUser); // Assuming Patient has a User reference
        patient.setName(request.getName());
        patientRepository.save(patient);

        // In actual code, send OTP via SMS here

        return ResponseEntity.ok(
            new ApiResponse<>(true, null, "User registered and OTP sent successfully", null)
        );
    }

    public ResponseEntity<?> requestLoginOtp(LoginRequestDTO request) {
        Optional<Users> userOpt = userRepository.findByPhoneNumber(request.getPhoneNumber());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(
                new ApiResponse<>(false, Collections.singletonList("User not found"), "Login failed", null)
            );
        }
        Users user = userOpt.get();
        String otp = String.format("%06d", new Random().nextInt(999999));
        user.setOtp(otp);
        user.setOtpCreatedAt(LocalDateTime.now());
        user.setOtpVerified(false);
        userRepository.save(user);

        // For now, just save OTP in DB. No SMS sending.

        return ResponseEntity.ok(
            new ApiResponse<>(true, null, "OTP generated and saved in database", null)
        );
    }

    public ResponseEntity<?> verifyLoginOtp(OtpVerifyDTO request) {
        Optional<Users> userOpt = userRepository.findByPhoneNumber(request.getPhoneNumber());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(
                new ApiResponse<>(false, Collections.singletonList("User not found"), "OTP verification failed", null)
            );
        }
        Users user = userOpt.get();
        if (user.getOtp() == null || !user.getOtp().equals(request.getOtp())) {
            return ResponseEntity.badRequest().body(
                new ApiResponse<>(false, Collections.singletonList("Invalid OTP"), "OTP verification failed", null)
            );
        }
        if (user.getOtpCreatedAt() == null || user.getOtpCreatedAt().plusMinutes(2).isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body(
                new ApiResponse<>(false, Collections.singletonList("OTP expired"), "OTP verification failed", null)
            );
        }
        user.setOtpVerified(true);
        userRepository.save(user);

        // Get user role
        String role = user.getUserType() != null ? user.getUserType().getName().toUpperCase() : "PATIENT";

        // Generate JWT
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        String token = jwtUtil.generateToken(user.getPhoneNumber(), claims);

        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("role", role);

        return ResponseEntity.ok(
            new ApiResponse<>(true, null, "Login successful", data)
        );
    }
}
