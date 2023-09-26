package com.example.PowerUser.Repository;

import com.example.PowerUser.Model.PowerUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PowerUserRepository extends JpaRepository<PowerUser, Integer> {
    Optional<PowerUser> findByUsername(String username);
    Optional<PowerUser> findByFullName(String fullName);
}
