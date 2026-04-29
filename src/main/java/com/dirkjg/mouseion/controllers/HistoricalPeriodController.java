package com.dirkjg.mouseion.controllers;

import com.dirkjg.mouseion.Dtos.HistoricalPeriodDto;
import com.dirkjg.mouseion.Dtos.HistoricalPeriodInputDto;
import org.springframework.web.bind.annotation.RestController;
import com.dirkjg.mouseion.services.HistoricalPeriodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class HistoricalPeriodController {

    private final HistoricalPeriodService historicalPeriodService;

    public HistoricalPeriodController(HistoricalPeriodService historicalPeriodService) {
        this.historicalPeriodService = historicalPeriodService;
    }

    // Get all historical periods, met optionele filter op naam
    @GetMapping("/historicalPeriods")
    public ResponseEntity<List<HistoricalPeriodDto>> getAllHistoricalPeriods(
            @RequestParam(value = "name", required = false) Optional<String> name) {

        List<HistoricalPeriodDto> dtos;

        if (name.isEmpty()) {
            dtos = historicalPeriodService.getAllHistoricalPeriods();
        } else {
            dtos = historicalPeriodService.getHistoricalPeriodsByName(name.get());
        }

        return ResponseEntity.ok().body(dtos);
    }

    // Get één historical period by id
    @GetMapping("/historicalPeriods/{id}")
    public ResponseEntity<HistoricalPeriodDto> getHistoricalPeriod(@PathVariable Long id) {
        HistoricalPeriodDto dto = historicalPeriodService.getHistoricalPeriodById(id);
        return ResponseEntity.ok().body(dto);
    }

    // Post een nieuwe historical period
    @PostMapping("/historicalPeriods")
    public ResponseEntity<HistoricalPeriodDto> addHistoricalPeriod(
            @Valid @RequestBody HistoricalPeriodInputDto inputDto) {

        HistoricalPeriodDto dto = historicalPeriodService.addHistoricalPeriod(inputDto);
        return ResponseEntity.created(null).body(dto);
    }

    // Verwijder een historical period by id
    @DeleteMapping("/historicalPeriods/{id}")
    public ResponseEntity<Object> deleteHistoricalPeriod(@PathVariable Long id) {
        historicalPeriodService.deleteHistoricalPeriod(id);
        return ResponseEntity.noContent().build();
    }

    // Volledige update van een historical period
    @PutMapping("/historicalPeriods/{id}")
    public ResponseEntity<HistoricalPeriodDto> updateHistoricalPeriod(
            @PathVariable Long id,
            @Valid @RequestBody HistoricalPeriodInputDto newPeriod) {

        HistoricalPeriodDto dto = historicalPeriodService.updateHistoricalPeriod(id, newPeriod);
        return ResponseEntity.ok().body(dto);
    }

    // Gedeeltelijke update (PATCH) van een historical period
    @PatchMapping("/historicalPeriods/{id}")
    public ResponseEntity<HistoricalPeriodDto> updatePartialHistoricalPeriod(
            @PathVariable Long id,
            @RequestBody HistoricalPeriodInputDto newPeriod) {

        HistoricalPeriodDto dto = historicalPeriodService.updatePartialHistoricalPeriod(id, newPeriod);
        return ResponseEntity.ok().body(dto);
    }

    // Voor een enkelvoudige assign van één aspect aan een periode
    @PutMapping("/historicalPeriods/{periodId}/characteristicAspects/{aspectId}")
    public void assignCharacteristicAspectToHistoricalPeriod(
            @PathVariable Long periodId,
            @PathVariable Long aspectId) {
        historicalPeriodService.assignCharacteristicAspectToHistoricalPeriod(periodId, aspectId);
    }

    // Voor een meervoudige assign van meerdere aspecten aan een periode
    @PutMapping("/historicalPeriods/{periodId}/characteristicAspects")
    public void assignMultipleAspectsToHistoricalPeriod(
            @PathVariable Long periodId,
            @RequestBody List<Long> aspectIds) {
        historicalPeriodService.assignMultipleAspectsToHistoricalPeriod(periodId, aspectIds);
    }
}
