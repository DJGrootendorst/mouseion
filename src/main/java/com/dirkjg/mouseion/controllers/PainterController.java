package com.dirkjg.mouseion.controllers;

import com.dirkjg.mouseion.Dtos.PainterDto;
import com.dirkjg.mouseion.Dtos.PainterInputDto;
import com.dirkjg.mouseion.services.PainterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class PainterController {

    private final PainterService painterService;

    public PainterController(PainterService painterService) {
        this.painterService = painterService;
    }

    // Get all painters
    @GetMapping("/painters")
    public ResponseEntity<List<PainterDto>> getAllPainters(
            @RequestParam(value = "name", required = false) Optional<String> name) {

        List<PainterDto> dtos;

        if (name.isEmpty()){
            dtos = painterService.getAllPainters();
        } else {
            dtos = painterService.getAllPaintersByName(name.get());
        }

        return ResponseEntity.ok().body(dtos);

    }

    // Get een painter by id
    @GetMapping("/painters/{id}")
    public ResponseEntity<PainterDto> getPainter(@PathVariable("id")Long id) {

        PainterDto painter = painterService.getPainterById(id);

        return ResponseEntity.ok().body(painter);
    }

    // Post een nieuwe painter
    @PostMapping("/painters")
    public ResponseEntity<PainterDto> addPainter(@Valid @RequestBody PainterInputDto painterInputDto) {

        PainterDto dto = painterService.addPainter(painterInputDto);

        return ResponseEntity.created(null).body(dto);

    }

    // Verwijder een painter by id
    @DeleteMapping("/painters/{id}")
    public ResponseEntity<Object> deletePainter(@PathVariable Long id) {

        painterService.deletePainter(id);

        return ResponseEntity.noContent().build();

    }

    // Volledige update van een painter
    @PutMapping("/painter/{id}")
    public ResponseEntity<PainterDto> updatePainter(@PathVariable Long id, @Valid @RequestBody PainterInputDto newPainter) {

        PainterDto dto = painterService.updatePainter(id, newPainter);

        return ResponseEntity.ok().body(dto);

    }

    // VERANTWOORDINGSDOCUMENT, TECHNISCHE KEUZE
    // De PatchMapping voor het gedeeltelijk updaten van een Painter,
    // is eigenlijk overbodig, de naam, geboortejaar en overlijdensjaar van de
    // historische Painter staan namelijk vast en zullen niet snel gewijzigd hoeven te worden,
    // tenzij er een fout door de gebruiker gemaakt is.
    @PatchMapping("/painters/{id}")
    public ResponseEntity<PainterDto> updatePartialPainter(
            @PathVariable Long id,
            @RequestBody PainterInputDto newPainter) {

        PainterDto dto = painterService.updatePartialPainter(id, newPainter);

        return ResponseEntity.ok().body(dto);
    }
}
