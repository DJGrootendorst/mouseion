package com.dirkjg.mouseion.controllers;

import org.springframework.web.bind.annotation.RestController;
import com.dirkjg.mouseion.services.CharacteristicAspectService;
import com.dirkjg.mouseion.Dtos.CharacteristicAspectDto;
import com.dirkjg.mouseion.Dtos.CharacteristicAspectInputDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

// Verantwoordingsdocument TECHNISCHE KEUZE 2: GEBRUIKERSROLLEN
// Zie de verwijzing in HistoricalPeriodController.

@RestController
public class CharacteristicAspectController {

    private final CharacteristicAspectService characteristicAspectService;

    public CharacteristicAspectController(CharacteristicAspectService characteristicAspectService) {
        this.characteristicAspectService = characteristicAspectService;
    }

    // Get all characteristic aspects
    @GetMapping("/characteristicAspects")
    public ResponseEntity<List<CharacteristicAspectDto>> getAllAspects(
            @RequestParam(value = "number", required = false) Optional<Integer> number) {

        List<CharacteristicAspectDto> dtos;

        if (number.isEmpty()) {
            dtos = characteristicAspectService.getAllAspects();
        } else {
            dtos = characteristicAspectService.getAspectsByNumber(number.get());
        }

        return ResponseEntity.ok().body(dtos);
    }

    // Get a single characteristic aspect by id
    @GetMapping("/characteristicAspects/{id}")
    public ResponseEntity<CharacteristicAspectDto> getAspect(@PathVariable Long id) {

        CharacteristicAspectDto dto = characteristicAspectService.getAspectById(id);

        return ResponseEntity.ok().body(dto);
    }

    // Post a new characteristic aspect
    @PostMapping("/characteristicAspects")
    public ResponseEntity<CharacteristicAspectDto> addAspect(
            @Valid @RequestBody CharacteristicAspectInputDto inputDto) {

        CharacteristicAspectDto dto = characteristicAspectService.addAspect(inputDto);

        return ResponseEntity.created(null).body(dto);
    }

    // Delete a characteristic aspect by id
    @DeleteMapping("/characteristicAspects/{id}")
    public ResponseEntity<Object> deleteAspect(@PathVariable Long id) {

        characteristicAspectService.deleteAspect(id);

        return ResponseEntity.noContent().build();
    }

    // Update a characteristic aspect completely
    @PutMapping("/characteristicAspects/{id}")
    public ResponseEntity<CharacteristicAspectDto> updateAspect(
            @PathVariable Long id, @Valid @RequestBody CharacteristicAspectInputDto inputDto) {

        CharacteristicAspectDto dto = characteristicAspectService.updateAspect(id, inputDto);

        return ResponseEntity.ok().body(dto);
    }

    // Partial update (PATCH) — meestal overbodig, maar aanwezig voor consistentie met andere controllers
    @PatchMapping("/characteristicAspects/{id}")
    public ResponseEntity<CharacteristicAspectDto> updatePartialAspect(
            @PathVariable Long id, @RequestBody CharacteristicAspectInputDto inputDto) {

        CharacteristicAspectDto dto = characteristicAspectService.updatePartialAspect(id, inputDto);

        return ResponseEntity.ok().body(dto);
    }
}
