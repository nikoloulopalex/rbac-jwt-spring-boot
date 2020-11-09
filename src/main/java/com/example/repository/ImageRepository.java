package com.example.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByName(String name);

}
