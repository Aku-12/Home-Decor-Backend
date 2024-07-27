package com.example.homedecor.Repo;


import com.example.homedecor.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface CustomerRepo extends JpaRepository<Customer,Integer> {
    Optional<Customer> findByUsername(String username);
    Boolean existsByUsername(String username);
}
