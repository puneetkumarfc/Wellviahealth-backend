package com.wellvia.WellviaHealth.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AppointmentDTO {
    private Integer appointmentId;
    private Integer slotId;
    private Long patientId;
    private LocalDateTime bookedAt;
    private String status;
} 