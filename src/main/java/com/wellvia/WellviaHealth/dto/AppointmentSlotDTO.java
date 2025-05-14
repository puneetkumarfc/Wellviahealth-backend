package com.wellvia.WellviaHealth.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Data
public class AppointmentSlotDTO {
    private Integer slotId;
    private Long doctorId;
    private LocalDate slotDate;
    private LocalTime startTime;
    private Integer duration;
    private Boolean isAvailable;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 