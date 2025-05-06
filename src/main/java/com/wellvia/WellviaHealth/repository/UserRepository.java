package com.wellvia.WellviaHealth.repository;

import com.wellvia.WellviaHealth.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByPhoneNumber(String phoneNumber);
    
    @Query("SELECT u FROM Users u LEFT JOIN FETCH u.userType WHERE u.phoneNumber = ?1")
    Optional<Users> findByPhoneNumberWithUserType(String phoneNumber);

    Optional<Users> findByProviderAndProviderUserId(Integer provider, String providerUserId);
}

