package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByEmail(String email);

    //custom query to find users by name
    @Query("SELECT u FROM User u WHERE u.name = ?1")           
    List<User> findUsersByName(String name);

}