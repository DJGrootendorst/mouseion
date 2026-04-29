package com.dirkjg.mouseion.repositories;

import com.dirkjg.mouseion.models.Painter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PainterRepository extends JpaRepository<Painter, Long> {
    List<Painter> findAllByNameIgnoreCase(String name);
}
