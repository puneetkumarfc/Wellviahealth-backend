package com.wellvia.WellviaHealth.repository;

import com.wellvia.WellviaHealth.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType, Long> {
}

