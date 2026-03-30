package com.matias.taskly.repository;


import com.matias.taskly.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Custom query to find a user by email — used in login and email uniqueness validation
    Optional<User> findByEmail(String email);

    // Custom query to check if an email is already registered — used in register
    boolean existsByEmail(String email);


}