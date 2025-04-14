package com.ecommerce.platform.auth.repository;

import com.ecommerce.platform.auth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email); // <-- Add this
    Optional<User> findByPassword(String password);


    boolean existsByUsername(String username);
}
