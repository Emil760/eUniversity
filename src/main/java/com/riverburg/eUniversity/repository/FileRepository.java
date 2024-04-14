package com.riverburg.eUniversity.repository;

import com.riverburg.eUniversity.model.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Integer> {

    Optional<FileEntity> findByFileName(String filename);
}

