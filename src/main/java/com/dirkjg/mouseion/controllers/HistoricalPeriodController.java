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

// Verantwoordingsdocument TECHNISCHE KEUZE 2: GEBRUIKERSROLLEN
// In de applicatie zijn er twee rollen: Educator en Student.
// Deze rollen bepalen wat een gebruiker mag doen. Voor Historische Periodes is ervoor gekozen
// om restricties in te bouwen: geen Educator of Student kan nieuwe Historische Periodes toevoegen,
// verwijderen, volledig updaten of gedeeltelijk updaten. De historische periodes zijn vastgelegd en
// veranderen alleen bij uitzonderlijke omstandigheden, zoals nieuwe wetenschappelijke inzichten of
// noodzakelijke technische aanpassingen. Alleen een beheerder (admin) krijgt de autoriteit om deze
// wijzigingen door te voeren. Deze keuze wordt gerealiseerd via de User- en Authority-entiteiten.
// Dit geldt ook voor CharacteristicAspectController. Er zijn namelijk 49 Kenmerkende Aspecten en
// die zijn van tevoren vastgelegd.

// Verantwoordingsdocument TECHNISCHE KEUZE 3: Enkelvoudige en meervoudige koppeling
// In de applicatie is ervoor gekozen om zowel een enkelvoudige als een meervoudige assign-functionaliteit
// te implementeren voor het koppelen van CharacteristicAspects aan een HistoricalPeriod.
// De enkelvoudige assign maakt het mogelijk om één aspect per request te koppelen, wat overzichtelijk en
// eenvoudig te testen is. De meervoudige assign maakt het mogelijk om meerdere aspecten in één keer te koppelen,
// wat efficiënter. Door beide methoden te gebruiken is de applicatie flexibel en geschikt voor verschillende gebruikssituaties.

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
