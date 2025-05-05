package com.wellvia.WellviaHealth.service;

import com.wellvia.WellviaHealth.dto.OTPRequestDTO;
import com.wellvia.WellviaHealth.interfaces.AuthInterface;
import com.wellvia.WellviaHealth.model.Users;
import com.wellvia.WellviaHealth.model.UserType;
import com.wellvia.WellviaHealth.repository.UserRepository;
import com.wellvia.WellviaHealth.repository.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthServiceImpl implements AuthInterface {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTypeRepository userTypeRepository;

    @Override
    public ResponseEntity<?> requestOtp(OTPRequestDTO request) {
        Optional<Users> existingUser = userRepository.findByPhoneNumber(request.getPhoneNumber());

        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("Phone number already exists");
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

        return ResponseEntity.ok("OTP sent successfully to phone number");
    }
}
