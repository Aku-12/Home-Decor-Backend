package com.example.homedecor.Repo;

import com.example.homedecor.Entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepo extends JpaRepository<Contact, Long>{
}
