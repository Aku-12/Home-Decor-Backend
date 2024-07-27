package com.example.homedecor.Repo;

import com.example.homedecor.Entity.Furniture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FurnitureRepo extends JpaRepository<Furniture,Long> {
}
