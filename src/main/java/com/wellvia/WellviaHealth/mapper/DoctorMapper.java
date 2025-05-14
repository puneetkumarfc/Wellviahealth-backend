package com.wellvia.WellviaHealth.mapper;

import com.wellvia.WellviaHealth.model.Doctor;
import com.wellvia.WellviaHealth.dto.DoctorDTO;
import com.wellvia.WellviaHealth.dto.AppointmentSlotDTO;
import com.wellvia.WellviaHealth.dto.AppointmentDTO;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class DoctorMapper {

    public DoctorDTO toDTO(Doctor doctor) {
        if (doctor == null) {
            return null;
        }

        DoctorDTO dto = new DoctorDTO();
        dto.setDoctorId(doctor.getDoctorId());
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

        // Map appointment slots (already filtered at database level)
        if (doctor.getAppointmentSlots() != null) {
            dto.setAppointmentSlots(doctor.getAppointmentSlots().stream()
                    .map(this::toAppointmentSlotDTO)
                    .collect(Collectors.toList()));
        }

        // We don't need to return appointments to patients
        dto.setAppointments(null);

        return dto;
    }

    private AppointmentSlotDTO toAppointmentSlotDTO(com.wellvia.WellviaHealth.model.AppointmentSlot slot) {
        if (slot == null) {
            return null;
        }

        AppointmentSlotDTO dto = new AppointmentSlotDTO();
        dto.setSlotId(slot.getSlotId());
        dto.setDoctorId(slot.getDoctor().getDoctorId());
        dto.setSlotDate(slot.getSlotDate());
        dto.setStartTime(slot.getStartTime());
        dto.setDuration(slot.getDuration());
        dto.setIsAvailable(slot.getIsAvailable());
        dto.setCreatedAt(slot.getCreatedAt());
        dto.setUpdatedAt(slot.getUpdatedAt());
        return dto;
    }
}