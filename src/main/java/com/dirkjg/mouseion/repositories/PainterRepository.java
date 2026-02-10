package com.dirkjg.mouseion.repositories;

import com.dirkjg.mouseion.models.Painter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PainterRepository extends JpaRepository<Painter, Long> {
    // Net als in PaintingRepository zal hier later een zoekmethode worden toegevoegd,
    // (bijv. findAllPaintersByNameEqualsIgnoreCase) om painters op naam te kunnen vinden.
    // Dit ondersteunt de gerichte zoekfunctionaliteit binnen de applicatie.
    // Dit past bij de use case waarin gebruikers gericht kunstenaars willen opzoeken.
    List<Painter> findAllByNameIgnoreCase(String name);
}
