package com.dirkjg.mouseion.repositories;

import com.dirkjg.mouseion.models.PaintingImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileUploadRepository extends JpaRepository<PaintingImage, Long> {
    Optional<PaintingImage> findByFileName(String fileName);
}
