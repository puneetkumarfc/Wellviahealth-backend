package com.wellvia.WellviaHealth.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "laboratories")
public class Laboratory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lab_name", nullable = false)
    private String labName;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "ratings")
    private BigDecimal ratings;

    @Column(name = "accreditation")
    private String accreditation;

    @Column(name = "report_time")
    private String reportTime;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip")
    private String zip;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "last_modified_on")
    private LocalDateTime lastModifiedOn;

    @Column(name = "last_modified_by")
    private Long lastModifiedBy;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "laboratory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LaboratorySlot> slots;

    @PrePersist
    protected void onCreate() {
        createdOn = LocalDateTime.now();
        lastModifiedOn = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        lastModifiedOn = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Laboratory{" +
                "id=" + id +
                ", labName='" + labName + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }
} 