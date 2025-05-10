package com.wellvia.WellviaHealth.service;

import com.wellvia.WellviaHealth.dto.OTPRequestDTO;
import com.wellvia.WellviaHealth.dto.RegisterRequestDTO;
import com.wellvia.WellviaHealth.interfaces.AuthInterface;
import com.wellvia.WellviaHealth.model.Users;
import com.wellvia.WellviaHealth.model.UserType;
import com.wellvia.WellviaHealth.model.Patient;
import com.wellvia.WellviaHealth.model.UserSession;
import com.wellvia.WellviaHealth.model.LoginHistory;
import com.wellvia.WellviaHealth.repository.UserRepository;
import com.wellvia.WellviaHealth.repository.UserTypeRepository;
import com.wellvia.WellviaHealth.repository.PatientRepository;
import com.wellvia.WellviaHealth.repository.UserSessionRepository;
import com.wellvia.WellviaHealth.repository.LoginHistoryRepository;
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
import com.wellvia.WellviaHealth.model.Doctor;
import com.wellvia.WellviaHealth.repository.DoctorRepository;
import jakarta.servlet.http.HttpServletRequest;
import com.wellvia.WellviaHealth.dto.LogoutRequestDTO;

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

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserSessionRepository userSessionRepository;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private LoginHistoryRepository loginHistoryRepository;

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

        // Set static OTP
        String otp = "123456";
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
            Users user = existingUser.get();
            if (Boolean.TRUE.equals(user.getAccountVerified())) {
                return ResponseEntity.badRequest().body(
                        new ApiResponse<>(false, Collections.singletonList("Phone number already exists"), "Registration failed", null)
                );
            }
        }

        // Validate user type
        if (request.getUserType() == null || (request.getUserType() != 1 && request.getUserType() != 2)) {
            return ResponseEntity.badRequest().body(
                new ApiResponse<>(false, Collections.singletonList("Invalid user type. Must be 1 (Doctor) or 2 (Patient)"), "Registration failed", null)
            );
        }

        Users user = new Users();
        user.setPhoneNumber(request.getPhoneNumber());

        // Set static OTP for now since API not provided yet
        String otp = "123456";
        user.setOtp(otp);
        user.setOtpCreatedAt(LocalDateTime.now());
        user.setOtpVerified(false);
        user.setAccountVerified(false);

        // Set user type based on request
        // 1-> Doctor, 2-> Patient
        Optional<UserType> userType = userTypeRepository.findById(request.getUserType().longValue());
        if (userType.isEmpty()) {
            return ResponseEntity.badRequest().body(
                new ApiResponse<>(false, Collections.singletonList("Invalid user type"), "Registration failed", null)
            );
        }
        user.setUserType(userType.get());

        Users savedUser = userRepository.save(user);

        // Create user profile based on type
        if (request.getUserType() == 1) { // Doctor
            Doctor doctor = new Doctor();
            doctor.setUser(savedUser);
            doctor.setName(request.getName());
            doctor.setMobile(request.getPhoneNumber());
            doctor.setIsDeleted(false);
            doctorRepository.save(doctor);
        } else { // Patient
            Patient patient = new Patient();
            patient.setUser(savedUser);
            patient.setName(request.getName());
            patient.setMobile(request.getPhoneNumber());
            patient.setIsDeleted(false);
            patientRepository.save(patient);
        }

        // In actual code, send OTP via SMS here

        return ResponseEntity.ok(
            new ApiResponse<>(true, null, "User registered and OTP sent successfully", null)
        );
    }

    public ResponseEntity<?> requestLoginOtp(LoginRequestDTO request) {
        // Handle social login
        if (request.getProvider() != null && request.getProviderUserId() != null) {
            return handleSocialLogin(request);
        }

        // Handle mobile login
        if (request.getPhoneNumber() == null) {
            return ResponseEntity.badRequest().body(
                new ApiResponse<>(false, Collections.singletonList("Phone number is required for mobile login"), "Login failed", null)
            );
        }

        Optional<Users> userOpt = userRepository.findByPhoneNumber(request.getPhoneNumber());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(
                new ApiResponse<>(false, Collections.singletonList("User not found"), "Login failed", null)
            );
        }

        Users user = userOpt.get();

        if(!user.getAccountVerified()){
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(false, Collections.singletonList("User not found"), "Login failed", null)
            );
        }

        // Set static OTP
        String otp = "123456";
        user.setOtp(otp);
        user.setOtpCreatedAt(LocalDateTime.now());
        user.setOtpVerified(false);
        userRepository.save(user);

        // Log the OTP request
        LoginHistory loginHistory = new LoginHistory();
        loginHistory.setUser(user);
        loginHistory.setLoginMethod(LoginHistory.LoginMethod.MOBILE);
        loginHistory.setDeviceInfo(request.getDeviceInfo());
        loginHistory.setIpAddress(getClientIpAddress());
        loginHistory.setLoginTimestamp(LocalDateTime.now());
        loginHistory.setStatus("SUCCESS");
        loginHistoryRepository.save(loginHistory);

        return ResponseEntity.ok(
            new ApiResponse<>(true, null, "OTP generated and saved in database", null)
        );
    }

    public ResponseEntity<?> verifyRegisterOtp(OtpVerifyDTO request) {
        Optional<Users> userOpt = userRepository.findByPhoneNumberWithUserType(request.getPhoneNumber());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(false, Collections.singletonList("User not found"), "OTP verification failed", null)
            );
        }
        Users user = userOpt.get();

        // Check if OTP is already verified
        if (user.getOtpVerified()) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(false, Collections.singletonList("OTP already verified. Please request a new OTP"), "OTP verification failed", null)
            );
        }

        if (user.getOtp() == null || !user.getOtp().equals(request.getOtp())) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(false, Collections.singletonList("Invalid OTP"), "OTP verification failed", null)
            );
        }

        if (user.getOtpCreatedAt() == null || user.getOtpCreatedAt().plusMinutes(3).isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(false, Collections.singletonList("OTP expired"), "OTP verification failed", null)
            );
        }
        user.setOtpVerified(true);
        user.setAccountVerified(true);
        userRepository.save(user);

        // Get user role
        String role = user.getUserType() != null ? user.getUserType().getName().toUpperCase() : "PATIENT";

        // Generate JWT
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        String token = jwtUtil.generateToken(user.getPhoneNumber(), claims);

        // Create user session
        UserSession session = new UserSession();
        session.setUser(user);
        session.setSessionToken(token);
        session.setDeviceInfo(request.getDeviceInfo());
        session.setIpAddress(getClientIpAddress());
        session.setExpiresAt(java.sql.Timestamp.valueOf(LocalDateTime.now().plusDays(7))); // Token expires in 7 days
        session.setIsActive(true);
        userSessionRepository.save(session);

        // Create response data with user information
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("role", role);

        // Add user data
        Map<String, Object> userData = new HashMap<>();
        userData.put("id", user.getId());
        userData.put("phoneNumber", user.getPhoneNumber());
        userData.put("userType", user.getUserType() != null ? user.getUserType().getName() : null);

        // Add profile data based on user type
        if (user.getUserType() != null) {
            if (user.getUserType().getName().equalsIgnoreCase("DOCTOR")) {
                Optional<Doctor> doctorOpt = doctorRepository.findByUserId(user.getId());
                doctorOpt.ifPresent(doctor -> {
                    // Basic user info
                    userData.put("name", doctor.getName());
                    userData.put("email", doctor.getEmail());
                    userData.put("mobile", doctor.getMobile());
                    userData.put("gender", doctor.getGender());
                    userData.put("dob", doctor.getDob());
                    userData.put("address", doctor.getAddress());

                    // Doctor specific info
                    userData.put("profileImage", doctor.getProfileImage());
                    userData.put("specialization", doctor.getSpecialization());
                    userData.put("education", doctor.getEducation());
                    userData.put("bio", doctor.getBio());
                    userData.put("registrationNumber", doctor.getRegistrationNumber());
                    userData.put("experience", doctor.getExperience());
                    userData.put("certificate", doctor.getCertificate());
                    userData.put("signature", doctor.getSignature());
                    userData.put("practiceAddress", doctor.getPracticeAddress());
                    userData.put("agreementFile", doctor.getAgreementFile());
                });
            } else if (user.getUserType().getName().equalsIgnoreCase("PATIENT")) {
                Optional<Patient> patientOpt = patientRepository.findByUserId(user.getId());
                patientOpt.ifPresent(patient -> {
                    // Basic user info
                    userData.put("name", patient.getName());
                    userData.put("email", patient.getEmail());
                    userData.put("mobile", patient.getMobile());
                    userData.put("gender", patient.getGender());
                    userData.put("dob", patient.getDob());

                    // Patient specific info
                    userData.put("profileImage", patient.getProfileImage());
                    userData.put("address", patient.getAddress());
                    userData.put("city", patient.getCity());
                    userData.put("state", patient.getState());
                    userData.put("zip", patient.getZip());
                    userData.put("country", patient.getCountry());
                    userData.put("bloodGroup", patient.getBloodGroup());
                    userData.put("allergies", patient.getAllergies());
                    userData.put("medicalConditions", patient.getMedicalConditions());
                });
            }
        }

        data.put("user", userData);

        return ResponseEntity.ok(
                new ApiResponse<>(true, null, "Login successful", data)
        );
    }

    private ResponseEntity<?> handleSocialLogin(LoginRequestDTO request) {
        // Validate provider
        if (request.getProvider() != 1 && request.getProvider() != 2) {
            return ResponseEntity.badRequest().body(
                new ApiResponse<>(false, Collections.singletonList("Invalid provider"), "Login failed", null)
            );
        }

        // Find user by provider and provider_user_id
        Optional<Users> userOpt = userRepository.findByProviderAndProviderUserId(
            request.getProvider(), request.getProviderUserId());

        Users user;
        if (userOpt.isEmpty()) {
            // Create new user for social login
            user = new Users();
            user.setProvider(request.getProvider());
            user.setProviderUserId(request.getProviderUserId());
            user.setOtpVerified(true);  // Social login is pre-verified
            
            // Set default user type as Patient (2)
            Optional<UserType> userType = userTypeRepository.findById(2L);
            userType.ifPresent(user::setUserType);
            
            user = userRepository.save(user);
        } else {
            user = userOpt.get();
        }

        // Generate JWT
        String role = user.getUserType() != null ? user.getUserType().getName().toUpperCase() : "PATIENT";
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        String token = jwtUtil.generateToken(user.getPhoneNumber(), claims);

        // Create user session
        UserSession session = new UserSession();
        session.setUser(user);
        session.setSessionToken(token);
        session.setDeviceInfo(request.getDeviceInfo());
        session.setIpAddress(getClientIpAddress());
        session.setExpiresAt(java.sql.Timestamp.valueOf(LocalDateTime.now().plusDays(7)));
        session.setIsActive(true);
        userSessionRepository.save(session);

        // Log the social login
        LoginHistory loginHistory = new LoginHistory();
        loginHistory.setUser(user);
        loginHistory.setLoginMethod(request.getProvider() == 1 ? 
            LoginHistory.LoginMethod.FACEBOOK : LoginHistory.LoginMethod.GOOGLE);
        loginHistory.setProviderUserId(request.getProviderUserId());
        loginHistory.setDeviceInfo(request.getDeviceInfo());
        loginHistory.setIpAddress(getClientIpAddress());
        loginHistory.setLoginTimestamp(LocalDateTime.now());
        loginHistory.setStatus("SUCCESS");
        loginHistoryRepository.save(loginHistory);

        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("role", role);

        return ResponseEntity.ok(
            new ApiResponse<>(true, null, "Login successful", data)
        );
    }

    public ResponseEntity<?> verifyLoginOtp(OtpVerifyDTO request) {
        Optional<Users> userOpt = userRepository.findByPhoneNumberWithUserType(request.getPhoneNumber());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(
                new ApiResponse<>(false, Collections.singletonList("User not found"), "OTP verification failed", null)
            );
        }
        Users user = userOpt.get();
        
        // Check if OTP is already verified
        if (user.getOtpVerified()) {
            return ResponseEntity.badRequest().body(
                new ApiResponse<>(false, Collections.singletonList("OTP already verified. Please request a new OTP"), "OTP verification failed", null)
            );
        }
        
        if (user.getOtp() == null || !user.getOtp().equals(request.getOtp())) {
            return ResponseEntity.badRequest().body(
                new ApiResponse<>(false, Collections.singletonList("Invalid OTP"), "OTP verification failed", null)
            );
        }

        if (!user.getAccountVerified()) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(false, Collections.singletonList("Invalid User"), "User does not exist", null)
            );
        }

        if (user.getOtpCreatedAt() == null || user.getOtpCreatedAt().plusMinutes(20).isBefore(LocalDateTime.now())) {
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

        // Create user session
        UserSession session = new UserSession();
        session.setUser(user);
        session.setSessionToken(token);
        session.setDeviceInfo(request.getDeviceInfo());
        session.setIpAddress(getClientIpAddress());
        session.setExpiresAt(java.sql.Timestamp.valueOf(LocalDateTime.now().plusDays(7))); // Token expires in 7 days
        session.setIsActive(true);
        userSessionRepository.save(session);

        // Create response data with user information
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("role", role);
        
        // Add user data
        Map<String, Object> userData = new HashMap<>();
        userData.put("id", user.getId());
        userData.put("phoneNumber", user.getPhoneNumber());
        userData.put("userType", user.getUserType() != null ? user.getUserType().getName() : null);
        
        // Add profile data based on user type
        if (user.getUserType() != null) {
            if (user.getUserType().getName().equalsIgnoreCase("DOCTOR")) {
                Optional<Doctor> doctorOpt = doctorRepository.findByUserId(user.getId());
                doctorOpt.ifPresent(doctor -> {
                    // Basic user info
                    userData.put("name", doctor.getName());
                    userData.put("email", doctor.getEmail());
                    userData.put("mobile", doctor.getMobile());
                    userData.put("gender", doctor.getGender());
                    userData.put("dob", doctor.getDob());
                    userData.put("address", doctor.getAddress());
                    
                    // Doctor specific info
                    userData.put("profileImage", doctor.getProfileImage());
                    userData.put("specialization", doctor.getSpecialization());
                    userData.put("education", doctor.getEducation());
                    userData.put("bio", doctor.getBio());
                    userData.put("registrationNumber", doctor.getRegistrationNumber());
                    userData.put("experience", doctor.getExperience());
                    userData.put("certificate", doctor.getCertificate());
                    userData.put("signature", doctor.getSignature());
                    userData.put("practiceAddress", doctor.getPracticeAddress());
                    userData.put("agreementFile", doctor.getAgreementFile());
                });
            } else if (user.getUserType().getName().equalsIgnoreCase("PATIENT")) {
                Optional<Patient> patientOpt = patientRepository.findByUserId(user.getId());
                patientOpt.ifPresent(patient -> {
                    // Basic user info
                    userData.put("name", patient.getName());
                    userData.put("email", patient.getEmail());
                    userData.put("mobile", patient.getMobile());
                    userData.put("gender", patient.getGender());
                    userData.put("dob", patient.getDob());
                    
                    // Patient specific info
                    userData.put("profileImage", patient.getProfileImage());
                    userData.put("address", patient.getAddress());
                    userData.put("city", patient.getCity());
                    userData.put("state", patient.getState());
                    userData.put("zip", patient.getZip());
                    userData.put("country", patient.getCountry());
                    userData.put("bloodGroup", patient.getBloodGroup());
                    userData.put("allergies", patient.getAllergies());
                    userData.put("medicalConditions", patient.getMedicalConditions());
                });
            }
        }
        
        data.put("user", userData);

        return ResponseEntity.ok(
            new ApiResponse<>(true, null, "Login successful", data)
        );
    }

    public ResponseEntity<?> logout(String token, LogoutRequestDTO request) {
        try {
            // Extract user ID from token
            String username = jwtUtil.getUsername(token);
            Users user = userRepository.findByPhoneNumber(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

            // Validate that the user ID in request matches the token
            if (!user.getId().equals(request.getUserId())) {
                return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, Collections.singletonList("Invalid user ID"), "Logout failed", null));
            }

            // Find active session for this user and token
            UserSession session = userSessionRepository.findByUserIdAndSessionTokenAndIsActiveTrue(user.getId(), token)
                .orElse(null);

            if (session == null) {
                return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, Collections.singletonList("No active session found"), "Logout failed", null));
            }

            // Deactivate the session
            session.setIsActive(false);
            userSessionRepository.save(session);

            // Optional: Deactivate all sessions for this user
            // userSessionRepository.deactivateAllUserSessions(user.getId());

            return ResponseEntity.ok(
                new ApiResponse<>(true, null, "Logged out successfully", null)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse<>(false, Collections.singletonList(e.getMessage()), "Logout failed", null));
        }
    }

    private String getClientIpAddress() {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0];
        }
        return request.getRemoteAddr();
    }
}
