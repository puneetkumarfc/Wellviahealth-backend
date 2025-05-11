package com.wellvia.WellviaHealth.mapper;

import com.wellvia.WellviaHealth.model.Doctor;
import com.wellvia.WellviaHealth.dto.DoctorDTO;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {

    public DoctorDTO toDTO(Doctor doctor) {
        if (doctor == null) {
            return null;
        }

        DoctorDTO dto = new DoctorDTO();
        dto.setUserId(doctor.getDoctorId());
        dto.setName(doctor.getName());
        dto.setEmail(doctor.getEmail());
        dto.setMobile(doctor.getMobile());
        dto.setGender(doctor.getGender() != null ? doctor.getGender().name() : null);
        dto.setDob(doctor.getDob());
        dto.setAddress(doctor.getAddress());
        dto.setEducation(doctor.getEducation());
        dto.setBio(doctor.getBio());
        dto.setSpecialization(doctor.getSpecialization());
        dto.setRegistrationNumber(doctor.getRegistrationNumber());
        dto.setProfileImage(doctor.getProfileImage());
        dto.setExperience(doctor.getExperience());
        dto.setCertificate(doctor.getCertificate());
        dto.setSignature(doctor.getSignature());
        dto.setPracticeAddress(doctor.getPracticeAddress());
        dto.setAgreementFile(doctor.getAgreementFile());
        return dto;
    }
}