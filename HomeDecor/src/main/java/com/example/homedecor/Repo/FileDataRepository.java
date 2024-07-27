package com.example.homedecor.Repo;

import com.example.homedecor.Entity.FileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileDataRepository extends JpaRepository<FileData,Long> {
    List<FileData> findByName(String fileName);
}
